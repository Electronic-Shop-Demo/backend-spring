package com.mairwunnx.application.cache;

import lombok.NonNull;
import org.springframework.stereotype.Component;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public final class UUIDCache extends CacheBase<String, UUID> {

    public UUIDCache() {
        super(300, 2, TimeUnit.MINUTES);
    }

    @Override
    @ParametersAreNonnullByDefault
    @NonNull UUID getNewInstance(final String in) {
        return UUID.fromString(in);
    }

}
