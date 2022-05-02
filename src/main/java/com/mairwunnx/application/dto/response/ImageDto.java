package com.mairwunnx.application.dto.response;

import lombok.NonNull;

import java.util.UUID;

public record ImageDto(@NonNull UUID id, long size, @NonNull String type, byte[] raw) {}
