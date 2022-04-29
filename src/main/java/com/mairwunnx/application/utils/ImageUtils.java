package com.mairwunnx.application.utils;

import com.mairwunnx.application.exception.CodeAwareException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.mairwunnx.application.Constants.Errors.INTERNAL_COMPRESSING_IMAGE_ERROR;
import static com.mairwunnx.application.Constants.Errors.UNSUPPORTED_IMAGE_EXTENSION;
import static com.mairwunnx.application.Constants.Files.COMPRESSION_QUALITY;
import static com.mairwunnx.application.Constants.Files.COMPRESSION_SIZE_THRESHOLD;
import static javax.imageio.ImageWriteParam.MODE_EXPLICIT;

@Log4j2
public final class ImageUtils {

    @ParametersAreNonnullByDefault
    public static byte[] compressedImageOf(final byte[] input, final long size, final String extension) {
        if (!isNeedCompression(size) || !testSupportCompression(extension)) {
            return input;
        }

        final byte[] bytes;

        try (final var inputStream = new ByteArrayInputStream(input)) {
            final var bufferedImage = ImageIO.read(inputStream);
            final var writer = ImageIO.getImageWritersByFormatName(extension).next();
            final var params = writer.getDefaultWriteParam();
            final var byteOutputStream = new ByteArrayOutputStream((int) size);

            params.setCompressionMode(MODE_EXPLICIT);
            params.setCompressionQuality(COMPRESSION_QUALITY);

            try (final var imageOutputStream = ImageIO.createImageOutputStream(byteOutputStream)) {
                writer.setOutput(imageOutputStream);
                writer.write(null, new IIOImage(bufferedImage, null, null), params);
            }

            bytes = byteOutputStream.toByteArray();
        } catch (final IllegalArgumentException e) {
            log.error("Unsupported image extension tried to compress: " + extension, e);
            throw new CodeAwareException(UNSUPPORTED_IMAGE_EXTENSION, HttpStatus.BAD_REQUEST);
        } catch (final IOException e) {
            log.error("An error occurred while compressing image with size: " + size + " , extension: " + extension, e);
            throw new CodeAwareException(INTERNAL_COMPRESSING_IMAGE_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return bytes;
    }

    private static boolean isNeedCompression(final long size) {
        return size > COMPRESSION_SIZE_THRESHOLD;
    }

    @ParametersAreNonnullByDefault
    private static boolean testSupportCompression(final String extension) {
        return switch (extension) {
            case "svg", "webp" -> false;
            default -> true;
        };
    }

}
