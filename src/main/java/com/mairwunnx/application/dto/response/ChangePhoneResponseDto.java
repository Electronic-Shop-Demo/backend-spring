package com.mairwunnx.application.dto.response;

import lombok.NonNull;

import java.util.UUID;

public record ChangePhoneResponseDto(@NonNull UUID session, @NonNull String phone) {}
