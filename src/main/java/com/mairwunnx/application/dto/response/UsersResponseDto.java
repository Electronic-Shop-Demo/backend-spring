package com.mairwunnx.application.dto.response;

import lombok.NonNull;

import java.util.List;

public record UsersResponseDto(int page, int lastPage, @NonNull List<UserResponseDto> users) {
}
