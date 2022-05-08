package com.mairwunnx.application.dal.service;

import com.mairwunnx.application.dto.response.LoginResponseDto;
import com.mairwunnx.application.dto.response.RefreshResponseDto;
import com.mairwunnx.application.dto.response.RegisterResponseDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.context.SecurityContext;

import javax.annotation.ParametersAreNonnullByDefault;

public interface AuthService {

    @ParametersAreNonnullByDefault
    @NotNull LoginResponseDto login(final String email, final String password);

    @ParametersAreNonnullByDefault
    @NotNull RegisterResponseDto register(
        final String username,
        final String password,
        final String phone,
        final String email
    );

    @ParametersAreNonnullByDefault
    @NotNull RefreshResponseDto refresh(final String email, final String refreshToken);

    @ParametersAreNonnullByDefault
    void logout(final SecurityContext context, final String refreshToken);

}
