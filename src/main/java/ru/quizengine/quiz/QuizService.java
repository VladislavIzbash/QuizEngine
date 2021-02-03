package ru.quizengine.quiz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.quizengine.user.UserService;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
public class QuizService {
    private final QuizRepository repo;
    private final UserService userService;

    @Autowired
    public QuizService(QuizRepository repo, UserService userService) {
        this.repo = repo;
        this.userService = userService;
    }

    @Transactional
    public Quiz addQuiz(Quiz quiz) {
        quiz.setAuthor(UserService.getCurrentUser());
        return repo.save(quiz);
    }

    public Quiz getQuiz(long id) {
        return repo.findById(id).orElseThrow(QuizNotFoundException::new);
    }

    public Page<Quiz> getQuizzes(int pageNum, int count) {
        return repo.findAll(PageRequest.of(pageNum, count));
    }

    public boolean trySolve(long id, int[] answer) {
        Quiz quiz = repo.findById(id).orElseThrow(QuizNotFoundException::new);

        if (quiz.isAnswer(answer)) {
            userService.markAsSolved(quiz);
            return true;
        }
        return false;
    }

    @Transactional
    public void deleteQuiz(long id) {
        Quiz targetQuiz = repo.findById(id).orElseThrow(QuizNotFoundException::new);
        if (targetQuiz.getAuthor().getId() != UserService.getCurrentUser().getId()) {
            throw new QuizAccessDeniedException();
        }
        repo.deleteById(id);
    }

    public static class QuizNotFoundException extends EntityNotFoundException {
        public QuizNotFoundException() {
            super("Quiz not found");
        }
    }

    public static class QuizAccessDeniedException extends RuntimeException {
        public QuizAccessDeniedException() {
            super("Access denied");
        }
    }
}
