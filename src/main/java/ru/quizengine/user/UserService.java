package ru.quizengine.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.quizengine.auth.QuizUserDetails;
import ru.quizengine.quiz.Quiz;

import javax.transaction.Transactional;

@Service
public class UserService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UserRepository repo;
    private final CompletedQuizRepository completedQuizRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository repo,
                       CompletedQuizRepository completedQuizRepo,
                       PasswordEncoder passwordEncoder) {

        this.repo = repo;
        this.completedQuizRepo = completedQuizRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return new QuizUserDetails(user);
    }

    @Transactional
    public void addUser(User user) {
        if (repo.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExists();
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repo.save(user);
    }

    @Transactional
    public void markAsSolved(Quiz quiz) {
        User user = getCurrentUser();
        user.getCompletedQuizzes().add(new CompletedQuiz(user, quiz));
        repo.save(user);
    }

    public Page<CompletedQuiz> getCompletedQuizzes(int pageNum, int count) {
        return completedQuizRepo.findAllByUser(
                PageRequest.of(pageNum, count, Sort.by("completedAt").descending()),
                getCurrentUser());
    }

    public static User getCurrentUser() {
        return ((QuizUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
    }

    public static class UserAlreadyExists extends RuntimeException {
        public UserAlreadyExists() {
            super("User already exists");
        }
    }
}
