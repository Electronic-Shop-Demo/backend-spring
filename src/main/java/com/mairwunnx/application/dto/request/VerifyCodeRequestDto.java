package com.mairwunnx.application.dto.request;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record VerifyCodeRequestDto(@NotNull String phone, @NotNull UUID session, @NotNull String code) {

}
