package com.mairwunnx.application.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.ParametersAreNonnullByDefault;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Component
@RequiredArgsConstructor
public final class JwtCreator {

    @Qualifier("jwtSigningKey") @NonNull private final RSAPrivateKey privateKey;
    @Qualifier("jwtValidationKey") @NonNull private final RSAPublicKey publicKey;
    @Qualifier("refreshJwtSigningKey") @NonNull private final RSAPrivateKey refreshPrivateKey;
    @Qualifier("refreshJwtValidationKey") @NonNull private final RSAPublicKey refreshPublicKey;

    @ParametersAreNonnullByDefault
    public @NotNull String createAuthorizationJwt(final String subject, final Map<String, String> claims) {
        final var calendar = lifetimeCalendarOf(Calendar.MINUTE, 10);
        final var jwtBuilder = JWT.create().withSubject(subject);
        claims.forEach(jwtBuilder::withClaim);
        return sing(jwtBuilder, calendar, publicKey, privateKey);
    }

    @ParametersAreNonnullByDefault
    public @NotNull String createRefreshJwt(final String subject) {
        final var calendar = lifetimeCalendarOf(Calendar.MONTH, 1);
        final var jwtBuilder = JWT.create().withSubject(subject);
        return sing(jwtBuilder, calendar, refreshPublicKey, refreshPrivateKey);
    }

    private @NotNull Calendar lifetimeCalendarOf(final int field, final int amount) {
        final var calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Instant.now().toEpochMilli());
        calendar.add(field, amount);
        return calendar;
    }

    @ParametersAreNonnullByDefault
    private @NotNull String sing(
        final JWTCreator.Builder builder,
        final Calendar expiresAt,
        final RSAPublicKey publicKey,
        final RSAPrivateKey privateKey
    ) {
        return builder
            .withNotBefore(new Date())
            .withExpiresAt(expiresAt.getTime())
            .sign(Algorithm.RSA256(publicKey, privateKey));
    }
}
