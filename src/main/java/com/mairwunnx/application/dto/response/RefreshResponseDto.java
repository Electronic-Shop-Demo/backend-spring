package com.mairwunnx.application.dto.response;

import org.jetbrains.annotations.NotNull;

public record RefreshResponseDto(@NotNull String token, @NotNull String refreshToken) {

}
