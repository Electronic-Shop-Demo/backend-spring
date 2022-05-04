package com.mairwunnx.application.mapper;

import com.mairwunnx.application.dto.response.UserResponseDto;
import com.mairwunnx.application.dto.response.UsersResponseDto;
import com.mairwunnx.application.entity.mongo.UserDocument;
import com.mairwunnx.application.types.UsersSortVariant;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@Mapper
public interface UsersMapper {

    UsersMapper INSTANCE = Mappers.getMapper(UsersMapper.class);

    @NotNull UsersResponseDto entityToDto(
        final int page,
        final int lastPage,
        @NotNull final List<UserDocument> users,
        @Nullable final UsersSortVariant sort
    );

    @ParametersAreNonnullByDefault
    default @NotNull List<UserResponseDto> map(final List<UserDocument> value) {
        return value.stream().map(UserMapper.INSTANCE::entityToDto).toList();
    }

}
