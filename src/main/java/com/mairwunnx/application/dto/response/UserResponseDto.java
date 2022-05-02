package com.mairwunnx.application.dto.response;

import lombok.NonNull;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.util.UUID;

public record UserResponseDto(
    @NonNull UUID id,
    @Nullable UUID avatar,
    @NonNull String email,
    @Nullable String phone,
    @NonNull String fullname,
    @NonNull Instant creationDate,
    @NonNull Instant lastVisitDate,
    @NonNull UUID favorite,
    @NonNull UUID cart,
    @NonNull UUID order,
    @NonNull UUID userSettings,
    boolean active
) {}
