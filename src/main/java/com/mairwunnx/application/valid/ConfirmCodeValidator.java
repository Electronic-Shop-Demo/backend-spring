package com.mairwunnx.application.valid;

import com.mairwunnx.application.Constants;
import com.mairwunnx.application.exception.CodeAwareException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.annotation.ParametersAreNonnullByDefault;

@Component
public class ConfirmCodeValidator implements ValidatorBase<String> {

    @Override
    @ParametersAreNonnullByDefault
    public void validate(final String candidate) {
        if (candidate.length() != Constants.Validations.USER_CONFIRMATION_CODE_LENGTH) {
            throw new CodeAwareException(Constants.Errors.INCORRECT_CONFIRMATION_CODE, HttpStatus.BAD_REQUEST);
        }
    }

}
