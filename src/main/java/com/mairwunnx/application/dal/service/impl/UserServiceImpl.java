package com.mairwunnx.application.dal.service.impl;

import com.mairwunnx.application.Constants;
import com.mairwunnx.application.dal.repository.UserRepository;
import com.mairwunnx.application.dal.service.ImageService;
import com.mairwunnx.application.dal.service.UserService;
import com.mairwunnx.application.dto.response.RestorePasswordResponseDto;
import com.mairwunnx.application.dto.response.UserResponseDto;
import com.mairwunnx.application.dto.response.UsersResponseDto;
import com.mairwunnx.application.exception.CodeAwareException;
import com.mairwunnx.application.mapper.SpringUserMapper;
import com.mairwunnx.application.mapper.UserMapper;
import com.mairwunnx.application.mapper.UsersMapper;
import com.mairwunnx.application.types.UsersSortVariant;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.UUID;

@Component
@Service
@RequiredArgsConstructor
public final class UserServiceImpl implements UserService {

    private final UserRepository usersRepository;
    private final ImageService imageService;

    @Override
    @ParametersAreNonnullByDefault
    public @NotNull UserDetails loadUserByUsername(final String email) {
        final var user = usersRepository.findByEmail(email);

        if (user != null) return SpringUserMapper.INSTANCE.entityToDto(user);

        throw new CodeAwareException(Constants.Errors.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    @Override
    public @NotNull UsersResponseDto getUsers(final int page, @Nullable final UsersSortVariant sort) {
        final var users = usersRepository.getAll(page, sort);
        final var lastPage = users.size() / Constants.Pagination.USERS_PER_PAGE;
        return UsersMapper.INSTANCE.entityToDto(page, lastPage, users, sort);
    }

    @Override
    public @NotNull UsersResponseDto getBlockedUsers(final int page, @Nullable final UsersSortVariant sort) {
        final var users = usersRepository.getInactiveUsers(page, sort);
        final var lastPage = users.size() / Constants.Pagination.USERS_PER_PAGE;
        return UsersMapper.INSTANCE.entityToDto(page, lastPage, users, sort);
    }

    @Override
    @ParametersAreNonnullByDefault
    public @NotNull UserResponseDto getUser(final String email) {
        final var user = usersRepository.findByEmail(email);

        if (user != null) return UserMapper.INSTANCE.entityToDto(user);

        throw new CodeAwareException(Constants.Errors.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    @Override
    @ParametersAreNonnullByDefault
    public @NotNull UserResponseDto getUser(final UUID id) {
        final var user = usersRepository.findById(id);

        if (user != null) return UserMapper.INSTANCE.entityToDto(user);

        throw new CodeAwareException(Constants.Errors.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    @Override
    @ParametersAreNonnullByDefault
    public @NotNull UserResponseDto changeAvatar(final UUID id, final MultipartFile file) {
        final var user = usersRepository.findById(id);

        if (user != null) {
            if (user.getAvatar() != null) {
                imageService.replaceById(file, user.getAvatar());
            } else {
                final var image = imageService.insert(file);
                final var entity = usersRepository.changeAvatar(user.getId(), image.id());

                if (entity == null) {
                    throw new CodeAwareException(Constants.Errors.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
                } else {
                    return UserMapper.INSTANCE.entityToDto(entity);
                }
            }
            return UserMapper.INSTANCE.entityToDto(user);
        }

        throw new CodeAwareException(Constants.Errors.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    @Override
    @ParametersAreNonnullByDefault
    public @NotNull UserResponseDto changeEmail(final UUID id, final String email) {
        final var user = usersRepository.findById(id);

        if (user != null) {
            final var entity = usersRepository.changeEmail(user.getId(), email);

            if (entity == null) {
                throw new CodeAwareException(Constants.Errors.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
            } else {
                return UserMapper.INSTANCE.entityToDto(entity);
            }
        }

        throw new CodeAwareException(Constants.Errors.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    @Override
    @ParametersAreNonnullByDefault
    public @NotNull UserResponseDto changePhone(final UUID id, final String phone) {
        return null;
    }

    @Override
    @ParametersAreNonnullByDefault
    public @NotNull UserResponseDto changePassword(final UUID id, final String oldPwd, final String newPwd) {
        return null;
    }

    @Override
    @ParametersAreNonnullByDefault
    public @NotNull UserResponseDto changeFullName(final UUID id, final String fullName) {
        return null;
    }

    @Override
    @ParametersAreNonnullByDefault
    public @NonNull UserResponseDto addAuthority(final UUID id, final String newAuthority) {
        return null;
    }

    @Override
    @ParametersAreNonnullByDefault
    public @NonNull UserResponseDto removeAuthority(final UUID id, final String removeAuthority) {
        return null;
    }

    @Override
    @ParametersAreNonnullByDefault
    public @NotNull UserResponseDto deactivateUser(final UUID id) {
        return null;
    }

    @Override
    @ParametersAreNonnullByDefault
    public @NotNull UserResponseDto activateUser(final UUID id) {
        return null;
    }

    @Override
    @ParametersAreNonnullByDefault
    public @NonNull RestorePasswordResponseDto requestRestorePassword(final String email) {
        return null;
    }

    @Override
    @ParametersAreNonnullByDefault
    public @NonNull UserResponseDto confirmRestorePassword(final String email, final UUID session, final String newPassword) {
        return null;
    }

    @Override
    @ParametersAreNonnullByDefault
    public @NotNull UserResponseDto removeUser(final UUID id) {
        usersRepository.removeById(id);
    }

}
