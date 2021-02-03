package ru.quizengine.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.quizengine.quiz.QuizService;
import ru.quizengine.user.UserService;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationError(MethodArgumentNotValidException ex) {
        FieldError error = ex.getBindingResult().getFieldError();

        return new ResponseEntity<>(new ApiError(
                "400",
                error == null ? "None" : error.getDefaultMessage()
        ), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFoundError(EntityNotFoundException ex) {
        return new ResponseEntity<>(new ApiError("404", ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserService.UserAlreadyExists.class)
    public ResponseEntity<ApiError> handleNotFoundError(UserService.UserAlreadyExists ex) {
        return new ResponseEntity<>(new ApiError("400", ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(QuizService.QuizAccessDeniedException.class)
    public ResponseEntity<ApiError> handleNotFoundError(QuizService.QuizAccessDeniedException ex) {
        return new ResponseEntity<>(new ApiError("403", ex.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleUnknown(Exception ex) {
        return new ResponseEntity<>(
                new ApiError("500", "Unknown server error"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
