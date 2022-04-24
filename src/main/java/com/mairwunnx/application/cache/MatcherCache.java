package com.mairwunnx.application.cache;

import lombok.NonNull;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public final class MatcherCache extends CacheBase<Pair<Pattern, String>, Matcher> {

    public MatcherCache() {
        super(200, 5, TimeUnit.MINUTES);
    }

    @Override
    @ParametersAreNonnullByDefault
    @NonNull Matcher getNewInstance(final Pair<@NotNull Pattern, @NotNull String> pair) {
        return pair.getKey().matcher(pair.getValue());
    }

}
