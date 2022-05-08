package com.mairwunnx.application.controller;

import com.mairwunnx.application.annotations.ApiStage;
import com.mairwunnx.application.annotations.type.ApiStageType;
import com.mairwunnx.application.dal.service.AuthService;
import com.mairwunnx.application.dto.request.LoginRequestDto;
import com.mairwunnx.application.dto.request.LogoutRequestDto;
import com.mairwunnx.application.dto.request.RefreshRequestDto;
import com.mairwunnx.application.dto.request.RegisterRequestDto;
import com.mairwunnx.application.dto.response.LoginResponseDto;
import com.mairwunnx.application.dto.response.RefreshResponseDto;
import com.mairwunnx.application.dto.response.RegisterResponseDto;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@RestController
public final class AuthController {

    private static final String END_POINT = ENDPOINT_BASE + "/auth/";

    private final AuthService authService;

    @PostMapping(END_POINT + "login")
    @ResponseBody
    @ParametersAreNonnullByDefault
    public @NotNull LoginResponseDto login(@RequestBody final LoginRequestDto body) {
        return authService.login(body.email(), body.password());
    }

    @PostMapping(END_POINT + "register")
    @ResponseBody
    @ParametersAreNonnullByDefault
    public @NotNull RegisterResponseDto register(@RequestBody final RegisterRequestDto body) {
        return authService.register(body.username(), body.password(), body.phone(), body.email());
    }

    @PostMapping(END_POINT + "refresh")
    @ResponseBody
    @ParametersAreNonnullByDefault
    public @NotNull RefreshResponseDto refresh(@RequestBody final RefreshRequestDto body) {
        return authService.refresh(body.email(), body.refreshToken());
    }

    @PostMapping(END_POINT + "logout")
    @ResponseBody
    @ParametersAreNonnullByDefault
    public void logout(
        @CurrentSecurityContext final SecurityContext context,
        @RequestBody final LogoutRequestDto body
    ) {
        authService.logout(context, body.refreshToken());
    }

}
