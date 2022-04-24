package com.mairwunnx.application.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper = true)
public final class CodeAwareException extends RuntimeException {
    private final int code;

    @NotNull
    private final HttpStatus status;
}
