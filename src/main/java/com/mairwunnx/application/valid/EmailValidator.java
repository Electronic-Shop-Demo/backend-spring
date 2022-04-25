package com.mairwunnx.application.valid;

import com.mairwunnx.application.Constants.Errors;
import com.mairwunnx.application.cache.MatcherCache;
import com.mairwunnx.application.exception.CodeAwareException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.regex.Pattern;

import static com.mairwunnx.application.Constants.Validations.USER_EMAIL_MIN_LENGTH_THRESHOLD;

@Component
@RequiredArgsConstructor
public class EmailValidator implements ValidatorBase<String> {

    private static final Pattern PATTERN = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])");

    @NonNull private final MatcherCache matcherCache;

    @Override
    @ParametersAreNonnullByDefault
    public void validate(final String candidate) {
        if (StringUtils.isBlank(candidate)) {
            throw new CodeAwareException(Errors.USER_INCORRECT_EMAIL, HttpStatus.BAD_REQUEST);
        }

        if (candidate.length() < USER_EMAIL_MIN_LENGTH_THRESHOLD) {
            throw new CodeAwareException(Errors.USER_INCORRECT_EMAIL, HttpStatus.BAD_REQUEST);
        }

        if (!matcherCache.getOrCache(Pair.of(PATTERN, candidate)).matches()) {
            throw new CodeAwareException(Errors.USER_INCORRECT_EMAIL, HttpStatus.BAD_REQUEST);
        }
    }

}
