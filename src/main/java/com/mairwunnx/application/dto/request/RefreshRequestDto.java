package com.mairwunnx.application.dto.request;

import lombok.NonNull;

public record RefreshRequestDto(@NonNull String email, @NonNull String refreshToken) {

}
