package com.mairwunnx.application.utils;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.mairwunnx.application.Constants.Validations.USER_NUMBER_ALLOWED_CHARS;

public final class PhoneUtils {

    @ParametersAreNonnullByDefault
    public static @NotNull String clear(final String phone) {
        return StringUtils.replaceChars(phone, USER_NUMBER_ALLOWED_CHARS, "");
    }

}
