package com.mairwunnx.application.dto.request;

import lombok.NonNull;

import java.util.UUID;

public record VerifyCodeRequestDto(@NonNull String phone, @NonNull UUID session, @NonNull String code) {}
