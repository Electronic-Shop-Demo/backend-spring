package com.mairwunnx.application.controller;

import com.mairwunnx.application.annotations.ApiStage;
import com.mairwunnx.application.annotations.type.ApiStageType;
import com.mairwunnx.application.dto.request.SendCodeRequestDto;
import com.mairwunnx.application.dto.request.VerifyCodeRequestDto;
import com.mairwunnx.application.dto.response.SendCodeResponseDto;
import com.mairwunnx.application.dto.response.VerifyCodeResponseDto;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.mairwunnx.application.Constants.Api.ENDPOINT_BASE;

@ApiStage(ApiStageType.PROTOTYPE)
@RequiredArgsConstructor
@RestController
public final class ConfirmController {

    private static final String END_POINT = ENDPOINT_BASE + "/confirm/";

    @PostMapping(END_POINT + "send")
    @ParametersAreNonnullByDefault
    public @NotNull SendCodeResponseDto send(@RequestBody final SendCodeRequestDto body) {
        throw new IllegalStateException("Not implemented yet");
    }

    @GetMapping(END_POINT + "verify")
    public @NotNull VerifyCodeResponseDto verify(@RequestBody final VerifyCodeRequestDto body) {
        throw new IllegalStateException("Not implemented yet");
    }

}
