package com.mairwunnx.application.dto.response;

import lombok.NonNull;

import java.util.UUID;

public record RestorePasswordResponseDto(@NonNull UUID session) {}
