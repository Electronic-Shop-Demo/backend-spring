package com.mairwunnx.application.mapper;

import com.mairwunnx.application.dto.response.UserResponseDto;
import com.mairwunnx.application.entity.mongo.UserDocument;
import org.jetbrains.annotations.NotNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import javax.annotation.ParametersAreNonnullByDefault;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "avatarId", source = "avatar")
    @Mapping(target = "favoriteId", source = "favorite")
    @Mapping(target = "cartId", source = "cart")
    @Mapping(target = "orderId", source = "order")
    @Mapping(target = "userSettingsId", source = "userSettings")
    @Mapping(target = "isUserActive", source = "active")
    @ParametersAreNonnullByDefault
    @NotNull UserResponseDto entityToDto(final UserDocument entity);

}
