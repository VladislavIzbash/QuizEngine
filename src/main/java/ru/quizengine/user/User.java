package ru.quizengine.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity @Table(name = "user")
@Data @AllArgsConstructor @NoArgsConstructor
public class User {
    @Id @GeneratedValue
    long id;

    @Column(nullable = false, unique = true)
    @Pattern(regexp = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
            message = "Email must be valid",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    String email;

    @Column(nullable = false)
    @Size(min = 5, message = "Password mush have length greater than 5")
    String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    List<CompletedQuiz> completedQuizzes;
}
