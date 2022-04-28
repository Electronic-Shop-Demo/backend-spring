package com.mairwunnx.application.controller;

import com.mairwunnx.application.annotations.ApiStage;
import com.mairwunnx.application.annotations.type.ApiStageType;
import com.mairwunnx.application.dto.request.LoginRequestDto;
import com.mairwunnx.application.dto.request.LogoutRequestDto;
import com.mairwunnx.application.dto.request.RefreshRequestDto;
import com.mairwunnx.application.dto.request.RegisterRequestDto;
import com.mairwunnx.application.dto.response.LoginResponseDto;
import com.mairwunnx.application.dto.response.RefreshResponseDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.mairwunnx.application.Constants.Api.ENDPOINT_BASE;

@ApiStage(ApiStageType.PROTOTYPE)
@RestController
public final class AuthController {

    private static final String END_POINT = ENDPOINT_BASE + "/auth";

    @PostMapping(END_POINT + "login")
    @ResponseBody
    @ParametersAreNonnullByDefault
    public @NotNull LoginResponseDto login(@RequestBody final LoginRequestDto body) {
        throw new IllegalStateException("Not implemented yet");
    }

    @PostMapping(END_POINT + "register")
    @ResponseBody
    @ParametersAreNonnullByDefault
    public void register(@RequestBody final RegisterRequestDto body) {
        throw new IllegalStateException("Not implemented yet");
    }

    @PostMapping(END_POINT + "refresh")
    @ResponseBody
    @ParametersAreNonnullByDefault
    public @NotNull RefreshResponseDto refresh(@RequestBody final RefreshRequestDto body) {
        throw new IllegalStateException("Not implemented yet");
    }

    @PostMapping(END_POINT + "logout")
    @ResponseBody
    @ParametersAreNonnullByDefault
    public void logout(
        @CurrentSecurityContext final SecurityContext context,
        @RequestBody final LogoutRequestDto body
    ) {
        throw new IllegalStateException("Not implemented yet");
    }

}
