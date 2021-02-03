package ru.quizengine.api.quiz;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.quizengine.quiz.QuizService;
import ru.quizengine.user.UserService;

import javax.validation.Valid;
import java.util.Map;

@RestController @RequestMapping(value = "/api/quizzes")
public class QuizController {
    private final QuizService quizService;
    private final UserService userService;

    @Autowired
    public QuizController(QuizService quizService, UserService userService) {
        this.quizService = quizService;
        this.userService = userService;
    }

    @PostMapping
    public QuizDto addQuiz(@Valid @RequestBody QuizDto quiz) {
        return new QuizDto(quizService.addQuiz(quiz.toEntity()));
    }

    @GetMapping("/{id}")
    public QuizDto getQuiz(@PathVariable long id) {
        return new QuizDto(quizService.getQuiz(id));
    }

    @GetMapping
    public Page<QuizDto> getQuizzes(@RequestParam int page) {
        return quizService.getQuizzes(page, 10).map(QuizDto::new);
    }

    @GetMapping("/completed")
    public Page<CompletedQuizDto> getCompletedQuizzes(@RequestParam int page) {
        return userService.getCompletedQuizzes(page, 10).map(CompletedQuizDto::new);
    }

    @PostMapping("/{id}/solve")
    public SolveRespDto solveQuiz(@PathVariable long id, @RequestBody Map<String, int[]> answerObj) {
        boolean solved = quizService.trySolve(id, answerObj.get("answer"));
        return solved ? SolveRespDto.SUCCESSFUL : SolveRespDto.FAILED;
    }

    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQuiz(@PathVariable long id) {
        quizService.deleteQuiz(id);
    }
}
