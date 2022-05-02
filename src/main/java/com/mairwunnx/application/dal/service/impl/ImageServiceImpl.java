package com.mairwunnx.application.dal.service.impl;

import com.mairwunnx.application.dal.repository.ImageRepository;
import com.mairwunnx.application.dal.service.ImageService;
import com.mairwunnx.application.dto.response.ImageDto;
import com.mairwunnx.application.entity.mongo.ImageDocument;
import com.mairwunnx.application.exception.CodeAwareException;
import com.mairwunnx.application.mapper.ImageMapper;
import com.mairwunnx.application.utils.ImageUtils;
import com.mairwunnx.application.utils.StringUtils;
import com.mairwunnx.application.valid.ImageFormatValidator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.IOException;
import java.util.UUID;

import static com.mairwunnx.application.Constants.Errors.*;

@Log4j2
@Component
@RequiredArgsConstructor
public final class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final ImageFormatValidator imageFormatValidator;

    @Override
    @ParametersAreNonnullByDefault
    public byte[] getImageById(final UUID id) {
        final var image = imageRepository.findOneById(id);
        if (image == null) throw new CodeAwareException(IMAGE_NOT_FOUND, HttpStatus.NOT_FOUND);
        return image.getRaw();
    }

    @Override
    @ParametersAreNonnullByDefault
    public @NotNull ImageDto insert(final MultipartFile file) {
        final var extension = checkFileExtensionAndReturnExtension(file);
        final var image = prepareImageDocument(file, UUID.randomUUID(), extension);
        return ImageMapper.INSTANCE.entityToDto(imageRepository.insert(image));
    }

    @Override
    @ParametersAreNonnullByDefault
    public @NotNull ImageDto replaceById(final MultipartFile file, final UUID id) {
        final var extension = checkFileExtensionAndReturnExtension(file);
        final var image = prepareImageDocument(file, id, extension);
        return ImageMapper.INSTANCE.entityToDto(imageRepository.replaceById(image, id));
    }

    @ParametersAreNonnullByDefault
    private @NonNull String checkFileExtensionAndReturnExtension(final MultipartFile file) {
        final var originalFileName = file.getOriginalFilename();
        if (originalFileName == null) {
            log.error("An error occurred while getting original file name.");
            throw new CodeAwareException(NO_EXTENSION, HttpStatus.BAD_REQUEST);
        } else {
            final var extension = StringUtils.getBeforeLast(file.getOriginalFilename(), '.');
            imageFormatValidator.validate(extension);
            return extension;
        }
    }

    @ParametersAreNonnullByDefault
    private @NonNull ImageDocument prepareImageDocument(final MultipartFile file, final UUID id, final String extension) {
        final byte[] raw;
        var size = file.getSize();

        try {
            raw = ImageUtils.compressedImageOf(file.getBytes(), size, extension);
        } catch (final IOException e) {
            log.error("An error occurred while getting bytes of passed image", e);
            throw new CodeAwareException(INTERNAL_FILE_SAVING_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        size = raw.length;

        return new ImageDocument(id, size, extension, raw);
    }

}
