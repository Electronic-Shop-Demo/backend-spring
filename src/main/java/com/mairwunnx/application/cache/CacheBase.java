package com.mairwunnx.application.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Log4j2
@SuppressWarnings("unused")
abstract class CacheBase<IN, OUT> {

    private final Cache<IN, OUT> cache;

    CacheBase(final long maxSize) {
        cache = CacheBuilder.newBuilder()
            .maximumSize(maxSize)
            .build();
    }

    @ParametersAreNonnullByDefault
    CacheBase(final long maxSize, final long expireAfter, final TimeUnit unit) {
        cache = CacheBuilder.newBuilder()
            .maximumSize(maxSize)
            .expireAfterWrite(expireAfter, unit)
            .build();
    }

    @ParametersAreNonnullByDefault
    public @NotNull OUT getOrCache(final IN in) {
        try {
            return cache.get(in, () -> getNewInstance(in));
        } catch (final ExecutionException e) {
            log.error("Impossible to receive cache by key {}, returned new instance!", in);
            return getNewInstance(in);
        }
    }

    @ParametersAreNonnullByDefault
    abstract @NonNull OUT getNewInstance(final IN in);

}
