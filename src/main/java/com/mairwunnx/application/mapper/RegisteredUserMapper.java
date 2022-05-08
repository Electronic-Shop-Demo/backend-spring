package com.mairwunnx.application.mapper;

import com.mairwunnx.application.dto.response.RegisterResponseDto;
import com.mairwunnx.application.entity.mongo.UserDocument;
import org.jetbrains.annotations.NotNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import javax.annotation.ParametersAreNonnullByDefault;

@Mapper
public interface RegisteredUserMapper {

    RegisteredUserMapper INSTANCE = Mappers.getMapper(RegisteredUserMapper.class);

    @Mapping(target = "username", source = "fullname")
    @ParametersAreNonnullByDefault
    @NotNull RegisterResponseDto entityToDto(final UserDocument entity);

}
