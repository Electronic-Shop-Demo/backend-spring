package com.mairwunnx.application.dto.response;

import lombok.NonNull;

public record RefreshResponseDto(@NonNull String token, @NonNull String refreshToken) {

}
