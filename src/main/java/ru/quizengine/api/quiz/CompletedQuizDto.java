package ru.quizengine.api.quiz;

import lombok.Value;
import ru.quizengine.user.CompletedQuiz;

import java.time.LocalDateTime;

@Value
public class CompletedQuizDto {
    long id;
    LocalDateTime completedAt;

    public CompletedQuizDto(CompletedQuiz completedQuiz) {
        id = completedQuiz.getQuiz().getId();
        completedAt = completedQuiz.getCompletedAt();
    }
}
