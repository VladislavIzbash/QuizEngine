package ru.quizengine.api;

import lombok.Value;

@Value
public class ApiError {
    String code;
    String message;
}
