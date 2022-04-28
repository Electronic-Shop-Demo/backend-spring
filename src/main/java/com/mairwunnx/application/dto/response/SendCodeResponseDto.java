package com.mairwunnx.application.dto.response;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record SendCodeResponseDto(@NotNull UUID session) {

}
