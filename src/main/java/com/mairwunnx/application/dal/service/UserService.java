package com.mairwunnx.application.dal.service;

import com.mairwunnx.application.dto.response.UserResponseDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.UUID;

public interface UserService extends UserDetailsService {

    @ParametersAreNonnullByDefault
    @NotNull UserResponseDto loadByEmail(final String email);

    @ParametersAreNonnullByDefault
    @NotNull UserResponseDto loadById(final UUID id);

}
