package com.mairwunnx.application.dto.request;

import org.jetbrains.annotations.NotNull;

public record RegisterRequestDto(
    @NotNull String username,
    @NotNull String password,
    @NotNull String phone,
    @NotNull String email
) {

}
