package com.mairwunnx.application.valid;

import com.mairwunnx.application.exception.CodeAwareException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.mairwunnx.application.Constants.Errors.NO_IMAGE_EXTENSION;

@Component
public final class ImageFormatValidator implements ValidatorBase<String> {

    @Override
    @ParametersAreNonnullByDefault
    public void validate(final String candidate) {
        switch (candidate) {
            case "jpg", "jpeg", "png", "webp" -> noop();
            default -> throw new CodeAwareException(NO_IMAGE_EXTENSION, HttpStatus.BAD_REQUEST);
        }
    }

    private static void noop() {
    }

}
