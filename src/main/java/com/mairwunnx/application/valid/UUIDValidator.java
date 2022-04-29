package com.mairwunnx.application.valid;

import com.mairwunnx.application.exception.CodeAwareException;
import org.jetbrains.annotations.Contract;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.mairwunnx.application.Constants.Errors.UUID_IS_INCORRECT;

@Component
public final class UUIDValidator implements ValidatorBase<String> {

    @Override
    @Contract(pure = true)
    @ParametersAreNonnullByDefault
    public void validate(final String candidate) {
        final var length = candidate.length();

        if (length != 36 && length != 32) invalid();

        final var array = candidate.toCharArray();
        for (final int c : array) {
            if (c == 0x2D) continue;
            if (!((c >= 0x30 && c <= 0x39) || (c >= 0x61 && c <= 0x66) || (c >= 0x41 && c <= 0x46))) invalid();
        }
    }

    private void invalid() {
        throw new CodeAwareException(UUID_IS_INCORRECT, HttpStatus.BAD_REQUEST);
    }

}
