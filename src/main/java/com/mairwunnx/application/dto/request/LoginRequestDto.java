package com.mairwunnx.application.dto.request;

import lombok.NonNull;

public record LoginRequestDto(@NonNull String email, @NonNull String password) {

}
