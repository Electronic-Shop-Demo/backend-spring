package com.mairwunnx.application.dto.request;

import org.jetbrains.annotations.NotNull;

public record LoginRequestDto(@NotNull String email, @NotNull String password) {

}
