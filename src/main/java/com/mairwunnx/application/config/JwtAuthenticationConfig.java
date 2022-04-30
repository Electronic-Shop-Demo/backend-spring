package com.mairwunnx.application.config;

import com.mairwunnx.application.exception.CodeAwareException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import static com.mairwunnx.application.Constants.Errors.*;

@Log4j2
@Configuration
@SuppressWarnings("SpringElInspection")
public class JwtAuthenticationConfig {

    @Value("${app.security.jwt.keystore-location}") private String keyStorePath;
    @Value("#{environment.shop_jwt_token_als}") private String keyAlias;
    @Value("#{environment.shop_jwt_token_pwd}") private String keyStorePassword;
    @Value("#{environment.shop_jwt_token_phr}") private String privateKeyPassphrase;

    @Bean
    @Primary
    public KeyStore keyStore() {
        try {
            final var keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            final var resourceAsStream =
                Thread.currentThread().getContextClassLoader().getResourceAsStream(keyStorePath);
            keyStore.load(resourceAsStream, keyStorePassword.toCharArray());
            return keyStore;
        } catch (final IOException | CertificateException | NoSuchAlgorithmException | KeyStoreException e) {
            log.error("Unable to load keystore: {}", keyStorePath, e);
        }

        throw new CodeAwareException(INTERNAL_JWT_KEYSTORE_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Bean
    @Primary
    public RSAPrivateKey privateAuthenticationSignKey(final KeyStore keyStore) {
        try {
            final var key = keyStore.getKey(keyAlias, privateKeyPassphrase.toCharArray());
            if (key instanceof RSAPrivateKey privateKey) return privateKey;
        } catch (final UnrecoverableKeyException | NoSuchAlgorithmException | KeyStoreException e) {
            log.error("Unable to load private key from keystore: {}", keyStorePath, e);
        }

        throw new CodeAwareException(INTERNAL_JWT_SIGN_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Bean
    @Primary
    public RSAPublicKey publicAuthenticationValidationKey(final KeyStore keyStore) {
        try {
            final var certificate = keyStore.getCertificate(keyAlias);
            final var publicKey = certificate.getPublicKey();

            if (publicKey instanceof RSAPublicKey rsaPublicKey) return rsaPublicKey;
        } catch (final KeyStoreException e) {
            log.error("Unable to load private key from keystore: {}", keyStorePath, e);
        }

        throw new CodeAwareException(INTERNAL_JWT_VALIDATION_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Bean
    @Primary
    public JwtDecoder jwtDecoder(@Qualifier("publicAuthenticationValidationKey") final RSAPublicKey rsaPublicKey) {
        return NimbusJwtDecoder.withPublicKey(rsaPublicKey).build();
    }

}
