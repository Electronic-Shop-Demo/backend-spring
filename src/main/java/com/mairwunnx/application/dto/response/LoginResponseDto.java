package com.mairwunnx.application.dto.response;

import org.jetbrains.annotations.NotNull;

public record LoginResponseDto(@NotNull String token, @NotNull String refreshToken) {

}
