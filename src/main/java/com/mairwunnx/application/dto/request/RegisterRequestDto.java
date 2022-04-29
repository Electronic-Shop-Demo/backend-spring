package com.mairwunnx.application.dto.request;

import lombok.NonNull;

public record RegisterRequestDto(
    @NonNull String username,
    @NonNull String password,
    @NonNull String phone,
    @NonNull String email
) {

}
