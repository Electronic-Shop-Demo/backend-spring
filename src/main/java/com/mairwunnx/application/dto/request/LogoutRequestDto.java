package com.mairwunnx.application.dto.request;

import org.jetbrains.annotations.NotNull;

public record LogoutRequestDto(@NotNull String refreshToken) {

}
