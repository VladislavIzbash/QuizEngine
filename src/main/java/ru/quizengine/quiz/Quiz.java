package ru.quizengine.quiz;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.quizengine.user.CompletedQuiz;
import ru.quizengine.user.User;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@Entity @Table(name = "quiz")
@Data @AllArgsConstructor @NoArgsConstructor
public class Quiz {
    @Id @GeneratedValue
    long id;

    @OneToOne
    @ToString.Exclude
    User author;

    @Column(nullable = false)
    String title;

    @Column(nullable = false)
    String text;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "quiz_option")
    List<QuizOption> options;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    List<CompletedQuiz> completions;

    public boolean isAnswer(int[] answer) {
        int[] rightAnswer = options.stream().filter(QuizOption::isCorrect).mapToInt(options::indexOf).toArray();
        return Arrays.equals(rightAnswer, answer);
    }
}
