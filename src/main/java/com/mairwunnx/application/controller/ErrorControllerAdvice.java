package com.mairwunnx.application.controller;

import com.mairwunnx.application.config.ApplicationConfig;
import com.mairwunnx.application.dto.response.ErrorResponseDto;
import com.mairwunnx.application.exception.CodeAwareException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.ZonedDateTime;

@Log4j2
@ControllerAdvice
@RequiredArgsConstructor
public final class ErrorControllerAdvice {

    @NonNull private final ApplicationConfig applicationConfig;

    @ExceptionHandler(CodeAwareException.class)
    public @NotNull ResponseEntity<ErrorResponseDto> handleCodeAwareException(
        @NotNull final RuntimeException exception,
        @NotNull final WebRequest webRequest
    ) {
        if (exception instanceof CodeAwareException codeAwareException) {
            return new ResponseEntity<>(
                new ErrorResponseDto(
                    ZonedDateTime.now(),
                    codeAwareException.getStatus().toString(),
                    webRequest.getContextPath(),
                    getStacktraceOrNullIfProductionMode(codeAwareException),
                    codeAwareException.getCode()
                ),
                codeAwareException.getStatus()
            );
        }

        log.error(exception + " does not matches " + CodeAwareException.class.getCanonicalName());
        throw new IllegalStateException(exception + " does not matches " + CodeAwareException.class.getCanonicalName());
    }

    private String getStacktraceOrNullIfProductionMode(@NotNull final Exception ex) {
        return applicationConfig.getStage().equals(ApplicationConfig.PRODUCTION_STAGE)
            ? null
            : ExceptionUtils.getStackTrace(ex);
    }

}
