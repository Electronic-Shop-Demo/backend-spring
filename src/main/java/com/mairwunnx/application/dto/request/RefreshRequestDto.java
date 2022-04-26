package com.mairwunnx.application.dto.request;

import org.jetbrains.annotations.NotNull;

public record RefreshRequestDto(@NotNull String email, @NotNull String refreshToken) {
}
