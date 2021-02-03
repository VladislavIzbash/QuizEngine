package ru.quizengine.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.quizengine.quiz.Quiz;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Table(name = "completed_quiz")
@Data @AllArgsConstructor @NoArgsConstructor
public class CompletedQuiz {
    @Id @GeneratedValue
    long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    Quiz quiz;

    LocalDateTime completedAt;

    public CompletedQuiz(User user, Quiz quiz) {
        this.user = user;
        this.quiz = quiz;
    }

    @PrePersist
    void updateDate() {
        completedAt = LocalDateTime.now();
    }
}
