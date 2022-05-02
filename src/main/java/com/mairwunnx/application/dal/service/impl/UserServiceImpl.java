package com.mairwunnx.application.dal.service.impl;

import com.mairwunnx.application.Constants;
import com.mairwunnx.application.dal.repository.UserRepository;
import com.mairwunnx.application.dal.service.UserService;
import com.mairwunnx.application.dto.response.UserResponseDto;
import com.mairwunnx.application.exception.CodeAwareException;
import com.mairwunnx.application.mapper.SpringUserMapper;
import com.mairwunnx.application.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.UUID;

@Component
@Service
@RequiredArgsConstructor
public final class UserServiceImpl implements UserService {

    private final UserRepository usersRepository;

    @Override
    @ParametersAreNonnullByDefault
    public @NotNull UserDetails loadUserByUsername(final String email) {
        final var user = usersRepository.findByEmail(email);

        if (user != null) return SpringUserMapper.INSTANCE.entityToDto(user);

        throw new CodeAwareException(Constants.Errors.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    @Override
    @ParametersAreNonnullByDefault
    public @NotNull UserResponseDto loadByEmail(final String email) {
        final var user = usersRepository.findByEmail(email);

        if (user != null) return UserMapper.INSTANCE.entityToDto(user);

        throw new CodeAwareException(Constants.Errors.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    @Override
    @ParametersAreNonnullByDefault
    public @NotNull UserResponseDto loadById(final UUID id) {
        final var user = usersRepository.findById(id);

        if (user != null) return UserMapper.INSTANCE.entityToDto(user);

        throw new CodeAwareException(Constants.Errors.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

}
