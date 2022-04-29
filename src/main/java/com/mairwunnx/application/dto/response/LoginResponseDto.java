package com.mairwunnx.application.dto.response;

import lombok.NonNull;

public record LoginResponseDto(@NonNull String token, @NonNull String refreshToken) {

}
