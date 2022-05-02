package com.mairwunnx.application.dal.service;

import com.mairwunnx.application.entity.mongo.ImageDocument;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.UUID;

public interface ImageService {

    @ParametersAreNonnullByDefault
    byte[] getImageById(final UUID id);

    @ParametersAreNonnullByDefault
    @NotNull ImageDocument insert(final MultipartFile file);

    @ParametersAreNonnullByDefault
    @NotNull ImageDocument replaceById(final MultipartFile file, final UUID id);

}
