package com.mairwunnx.application.dto.response;

import lombok.NonNull;
import org.jetbrains.annotations.Nullable;

import java.time.ZonedDateTime;

public record ErrorResponseDto(
    @NonNull ZonedDateTime incidentTime,
    @NonNull String status,
    @NonNull String path,
    @Nullable String stacktrace,
    int code
) {}
