package ru.quizengine.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompletedQuizRepository extends PagingAndSortingRepository<CompletedQuiz, Long> {
    Page<CompletedQuiz> findAllByUser(Pageable pageable, User user);
}
