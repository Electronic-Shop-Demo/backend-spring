package com.mairwunnx.application.valid;

import com.mairwunnx.application.Constants;
import com.mairwunnx.application.exception.CodeAwareException;
import com.mairwunnx.application.utils.PhoneUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.mairwunnx.application.Constants.Validations.USER_NUMBER_MAX_LENGTH_THRESHOLD;
import static com.mairwunnx.application.Constants.Validations.USER_NUMBER_MIN_LENGTH_THRESHOLD;

@Component
public final class PhoneValidator implements ValidatorBase<String> {

    @Override
    @ParametersAreNonnullByDefault
    public void validate(final String candidate) {
        if (StringUtils.isBlank(candidate)) {
            throw new CodeAwareException(Constants.Errors.USER_INCORRECT_PHONE, HttpStatus.BAD_REQUEST);
        }

        final var trimmedCandidate = PhoneUtils.trimPhone(candidate);
        if (isValidNumberLength(trimmedCandidate)) {
            throw new CodeAwareException(Constants.Errors.USER_INCORRECT_PHONE, HttpStatus.BAD_REQUEST);
        }

        if (isArabicNumeric(trimmedCandidate)) {
            throw new CodeAwareException(Constants.Errors.USER_INCORRECT_PHONE, HttpStatus.BAD_REQUEST);
        }
    }

    @ParametersAreNonnullByDefault
    private boolean isValidNumberLength(final String candidate) {
        return candidate.length() >= USER_NUMBER_MIN_LENGTH_THRESHOLD
            && candidate.length() <= USER_NUMBER_MAX_LENGTH_THRESHOLD;
    }

    @ParametersAreNonnullByDefault
    private boolean isArabicNumeric(final String candidate) {
        final char[] chars = candidate.toCharArray();
        for (final char c : chars) {
            if (!(c >= 0x30 && c <= 0x39)) {
                return false;
            }
        }
        return true;
    }

}
