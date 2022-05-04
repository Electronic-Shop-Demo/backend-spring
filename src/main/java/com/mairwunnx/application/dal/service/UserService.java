package com.mairwunnx.application.dal.service;

import com.mairwunnx.application.dto.response.RestorePasswordResponseDto;
import com.mairwunnx.application.dto.response.UserResponseDto;
import com.mairwunnx.application.dto.response.UsersResponseDto;
import com.mairwunnx.application.types.UsersSortVariant;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.UUID;

public interface UserService extends UserDetailsService {

    @NotNull UsersResponseDto getUsers(final int page, @Nullable UsersSortVariant sort);

    @NotNull UsersResponseDto getBlockedUsers(final int page, @Nullable UsersSortVariant sort);

    @ParametersAreNonnullByDefault
    @NotNull UserResponseDto getUser(final String email);

    @ParametersAreNonnullByDefault
    @NotNull UserResponseDto getUser(final UUID id);

    @ParametersAreNonnullByDefault
    @NotNull UserResponseDto changeAvatar(final UUID id, final MultipartFile file);

    @ParametersAreNonnullByDefault
    @NotNull UserResponseDto changeEmail(final UUID id, final String email);

    @ParametersAreNonnullByDefault
    @NotNull UserResponseDto changePhone(final UUID id, final String phone);

    @ParametersAreNonnullByDefault
    @NotNull UserResponseDto changePassword(final UUID id, final String oldPwd, final String newPwd);

    @ParametersAreNonnullByDefault
    @NotNull UserResponseDto changeFullName(final UUID id, final String fullName);

    @ParametersAreNonnullByDefault
    @NonNull UserResponseDto addAuthority(final UUID id, final String newAuthority);

    @ParametersAreNonnullByDefault
    @NonNull UserResponseDto removeAuthority(final UUID id, final String removeAuthority);

    @ParametersAreNonnullByDefault
    @NotNull UserResponseDto deactivateUser(final UUID id);

    @ParametersAreNonnullByDefault
    @NotNull UserResponseDto activateUser(final UUID id);

    @ParametersAreNonnullByDefault
    @NonNull RestorePasswordResponseDto requestRestorePassword(final String email);

    @ParametersAreNonnullByDefault
    @NonNull UserResponseDto confirmRestorePassword(final String email, final UUID session, final String newPassword);

    @ParametersAreNonnullByDefault
    @NotNull UserResponseDto removeUser(final UUID id);

}
