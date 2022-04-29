package com.mairwunnx.application.dto.request;

import lombok.NonNull;

public record LogoutRequestDto(@NonNull String refreshToken) {

}
