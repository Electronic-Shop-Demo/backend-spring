package com.mairwunnx.application.dto.response;

import lombok.NonNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;

public record ErrorResponseDto(
    @NonNull LocalDateTime incidentTime,
    @NonNull String status,
    @NonNull String path,
    @Nullable String stacktrace,
    int code
) {
}
