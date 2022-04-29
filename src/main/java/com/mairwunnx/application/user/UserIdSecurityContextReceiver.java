package com.mairwunnx.application.user;

import com.mairwunnx.application.Constants;
import com.mairwunnx.application.cache.UUIDCache;
import com.mairwunnx.application.wrappers.UserId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import javax.annotation.ParametersAreNonnullByDefault;

@Component
@RequiredArgsConstructor
public final class UserIdSecurityContextReceiver {

    @NonNull private final UUIDCache uuidCache;

    @ParametersAreNonnullByDefault
    public @NotNull UserId receiveUserIdFrom(final SecurityContext context) {
        final var principal = (Jwt) context.getAuthentication().getPrincipal();
        final var id = String.valueOf(principal.getClaims().get(Constants.Jwt.CLAIM_USER_ID));
        return new UserId(uuidCache.getOrCache(id));
    }

}
