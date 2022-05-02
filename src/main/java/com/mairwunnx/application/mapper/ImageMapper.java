package com.mairwunnx.application.mapper;

import com.mairwunnx.application.dto.response.ImageDto;
import com.mairwunnx.application.entity.mongo.ImageDocument;
import org.jetbrains.annotations.NotNull;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import javax.annotation.ParametersAreNonnullByDefault;

@Mapper
public interface ImageMapper {

    ImageMapper INSTANCE = Mappers.getMapper(ImageMapper.class);

    @ParametersAreNonnullByDefault
    @NotNull ImageDto entityToDto(final ImageDocument entity);

    @ParametersAreNonnullByDefault
    @NotNull ImageDocument dtoToEntity(final ImageDto entity);

}
