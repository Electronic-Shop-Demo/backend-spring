package com.mairwunnx.application.dal.service.impl;

import com.mairwunnx.application.Constants;
import com.mairwunnx.application.cache.UUIDCache;
import com.mairwunnx.application.dal.repository.UserRepository;
import com.mairwunnx.application.dal.service.AuthService;
import com.mairwunnx.application.dto.response.LoginResponseDto;
import com.mairwunnx.application.dto.response.RefreshResponseDto;
import com.mairwunnx.application.dto.response.RegisterResponseDto;
import com.mairwunnx.application.entity.mongo.UserDocument;
import com.mairwunnx.application.exception.CodeAwareException;
import com.mairwunnx.application.jwt.JwtCreator;
import com.mairwunnx.application.mapper.RegisteredUserMapper;
import com.mairwunnx.application.mapper.UserMapper;
import com.mairwunnx.application.user.SpringSecurityUser;
import com.mairwunnx.application.utils.PhoneUtils;
import com.mairwunnx.application.valid.EmailValidator;
import com.mairwunnx.application.valid.PasswordValidator;
import com.mairwunnx.application.valid.PhoneValidator;
import com.mairwunnx.application.valid.UsernameValidator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;

import javax.annotation.ParametersAreNonnullByDefault;
import java.text.Normalizer;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.mairwunnx.application.structs.WithStruct.with;
import static java.text.Normalizer.Form.NFD;

@Log4j2
@Component
@RequiredArgsConstructor
public final class AuthServiceImpl implements AuthService {

    @Qualifier("refreshJwtDecoder")
    private final @NonNull JwtDecoder jwtDecoder;
    private final @NonNull JwtCreator jwtCreator;
    private final @NonNull UserDetailsService userDetailsService;
    private final @NonNull PasswordEncoder passwordEncoder;
    private final @NonNull UserRepository userRepository;
    private final @NonNull UsernameValidator usernameValidator;
    private final @NonNull PasswordValidator passwordValidator;
    private final @NonNull PhoneValidator phoneValidator;
    private final @NonNull EmailValidator emailValidator;
    private final @NonNull UUIDCache uuidCache;

    @Override
    @ParametersAreNonnullByDefault
    public @NotNull LoginResponseDto login(final String email, final String password) {
        final var trimmed = with(email, password).apply(String::trim);

        final var trimmedEmail = trimmed.get(0);
        final var trimmedPassword = trimmed.get(1);

        emailValidator.validate(trimmedEmail);
        passwordValidator.validate(trimmedPassword);

        final var userDetails = (SpringSecurityUser) userDetailsService.loadUserByUsername(trimmedEmail);

        if (passwordEncoder.matches(trimmedPassword, userDetails.getPassword())) {
            final var claims = getClaimsOf(userDetails);
            final var refreshToken = jwtCreator.createRefreshJwt(trimmedEmail);
            userRepository.updateRefreshTokenByEmail(trimmedEmail, refreshToken);

            return new LoginResponseDto(jwtCreator.createAuthorizationJwt(trimmedEmail, claims), refreshToken);
        }

        throw new CodeAwareException(Constants.Errors.INCORRECT_PASSWORD, HttpStatus.UNAUTHORIZED);
    }

    @Override
    @ParametersAreNonnullByDefault
    public @NotNull RegisterResponseDto register(
        final String username,
        final String password,
        final String phone,
        final String email
    ) {
        final var trimmed = with(username, password, phone, email).apply(String::trim);
        final var trimmedUserName = trimmed.get(0);
        final var trimmedPassword = trimmed.get(1);
        final var trimmedPhone = trimmed.get(2);
        final var trimmedEmail = trimmed.get(3);

        final var normalizedUserName = Normalizer.normalize(trimmedUserName, NFD);

        usernameValidator.validate(normalizedUserName);
        passwordValidator.validate(trimmedPassword);
        phoneValidator.validate(trimmedPhone);
        emailValidator.validate(trimmedEmail);

        final var normalizedPhone = PhoneUtils.trimPhone(trimmedPhone);

        if (userRepository.findByEmail(trimmedEmail) != null) {
            throw new CodeAwareException(Constants.Errors.USER_REGISTERED, HttpStatus.CONFLICT);
        }

        final var user = new UserDocument(
            UUID.randomUUID(),
            null,
            trimmedEmail,
            normalizedPhone,
            passwordEncoder.encode(trimmedPassword),
            normalizedUserName,
            Instant.now(),
            Instant.now(),
            UUID.randomUUID(),
            UUID.randomUUID(),
            UUID.randomUUID(),
            UUID.randomUUID(),
            List.of(Constants.Jwt.Authorities.AUTHENTICATED_USER),
            List.of(),
            true
        );

        // todo: create
        //  favorite
        //  cart
        //  order
        //  userSettings
        //          table columns for user id

        return RegisteredUserMapper.INSTANCE.entityToDto(userRepository.insert(user));
    }

    @Override
    @ParametersAreNonnullByDefault
    public @NotNull RefreshResponseDto refresh(final String email, final String refreshToken) {
        return null;
    }

    @Override
    @ParametersAreNonnullByDefault
    public void logout(final SecurityContext context, final String refreshToken) {
    }

    @NotNull
    private Map<String, String> getClaimsOf(@NotNull final SpringSecurityUser user) {
        final Map<String, String> claims = new HashMap<>();
        final var authorities = user
            .getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(" "));

        claims.put(Constants.Jwt.CLAIM_USER_SCOPE, authorities);
        claims.put(Constants.Jwt.CLAIM_USER_ID, String.valueOf(user.getId()));
        return claims;
    }

}
