package ru.quizengine.quiz;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @Table(name = "option")
@Data @AllArgsConstructor @NoArgsConstructor
public class QuizOption {
    @Id @GeneratedValue
    long id;

    @Column(nullable = false)
    String text;

    @Column(nullable = false)
    boolean correct;

    public QuizOption(String text) {
        this.text = text;
    }
}
