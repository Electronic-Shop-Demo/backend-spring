package com.mairwunnx.application.dto.response;

import lombok.NonNull;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record UserResponseDto(
    @NonNull UUID id,
    @Nullable UUID avatarId,
    @NonNull String email,
    @Nullable String phone,
    @NonNull String fullname,
    @NonNull Instant creationDate,
    @NonNull Instant lastVisitDate,
    @NonNull UUID favoriteId,
    @NonNull UUID cartId,
    @NonNull UUID orderId,
    @NonNull UUID userSettingsId,
    @NonNull List<String> authorities,
    boolean isUserActive
) {}
