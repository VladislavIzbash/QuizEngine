package ru.quizengine.api.quiz;

import lombok.Value;

@Value
public class SolveRespDto {
    boolean success;
    String feedback;

    public static final SolveRespDto SUCCESSFUL = new SolveRespDto(
            true,
            "You're right!"
    );
    public static final SolveRespDto FAILED = new SolveRespDto(
            false,
            "Wrong answer"
    );
}
