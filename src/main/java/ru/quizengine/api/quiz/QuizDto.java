package ru.quizengine.api.quiz;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.quizengine.quiz.Quiz;
import ru.quizengine.quiz.QuizOption;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data @NoArgsConstructor
public class QuizDto {
    long id;

    @NotBlank(message = "Title must not be blank")
    String title;

    @NotBlank(message = "Text must not be blank")
    String text;

    @NotNull(message = "Options array is not provided")
    @Size(min = 2, message = "Must have at least two options")
    List<String> options;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    List<Integer> answer = new ArrayList<>();

    public QuizDto(Quiz entity) {
        id = entity.getId();
        title = entity.getTitle();
        text = entity.getText();
        options = entity.getOptions().stream().map(QuizOption::getText).collect(Collectors.toList());
    }

    public Quiz toEntity() {
        Quiz entity = new Quiz();
        entity.setTitle(title);
        entity.setText(text);

        List<QuizOption> opts = new ArrayList<>();
        for (int i = 0; i < options.size(); i++) {
            QuizOption opt = new QuizOption(options.get(i));
            opt.setCorrect(answer.contains(i));
            opts.add(opt);
        }
        entity.setOptions(opts);

        return entity;
    }
}
