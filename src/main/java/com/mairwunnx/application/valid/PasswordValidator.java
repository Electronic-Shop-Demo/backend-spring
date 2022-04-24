package com.mairwunnx.application.valid;

import com.mairwunnx.application.Constants;
import com.mairwunnx.application.Constants.Errors;
import com.mairwunnx.application.exception.CodeAwareException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.annotation.ParametersAreNonnullByDefault;

@Component
public class PasswordValidator implements ValidatorBase<String> {

    @Override
    @ParametersAreNonnullByDefault
    public void validate(final String candidate) {
        if (StringUtils.isBlank(candidate)) {
            throw new CodeAwareException(Errors.USER_INCORRECT_PASSWORD, HttpStatus.BAD_REQUEST);
        }

        if (StringUtils.length(candidate) < Constants.Validations.USER_PASSWORD_MIN_LENGTH_THRESHOLD) {
            throw new CodeAwareException(Errors.USER_INCORRECT_PASSWORD, HttpStatus.BAD_REQUEST);
        }

        if (StringUtils.length(candidate) > Constants.Validations.USER_PASSWORD_MAX_LENGTH_THRESHOLD) {
            throw new CodeAwareException(Errors.USER_INCORRECT_PASSWORD, HttpStatus.BAD_REQUEST);
        }
    }

}
