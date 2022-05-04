package com.mairwunnx.application.dto.response;

import com.mairwunnx.application.types.UsersSortVariant;
import lombok.NonNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public record UsersResponseDto(
    int page,
    int lastPage,
    @Nullable UsersSortVariant sort,
    @NonNull List<UserResponseDto> users
) {}
