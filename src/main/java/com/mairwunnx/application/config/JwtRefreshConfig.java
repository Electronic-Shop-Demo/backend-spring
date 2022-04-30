package com.mairwunnx.application.config;

import com.mairwunnx.application.exception.CodeAwareException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
public class JwtRefreshConfig {

    @Value("${app.security.jwt.refresh.keystore-location}") private String refreshKeyStorePath;
    @Value("#{environment.shop_jwt_refresh_als}") private String refreshKeyAlias;
    @Value("#{environment.shop_jwt_refresh_pwd}") private String refreshKeyStorePassword;
    @Value("#{environment.shop_jwt_refresh_phr}") private String refreshPrivateKeyPassphrase;

    @Bean("refreshKeyStore")
    public KeyStore refreshKeyStore() {
        try {
            final var keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            final var resourceAsStream =
                Thread.currentThread().getContextClassLoader().getResourceAsStream(refreshKeyStorePath);
            keyStore.load(resourceAsStream, refreshKeyStorePassword.toCharArray());
            return keyStore;
        } catch (final IOException | CertificateException | NoSuchAlgorithmException | KeyStoreException e) {
            log.error("Unable to load keystore: {}", refreshKeyStorePath, e);
        }

        throw new CodeAwareException(INTERNAL_JWT_KEYSTORE_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Bean("privateRefreshSignKey")
    public RSAPrivateKey privateRefreshSignKey(@Qualifier("refreshKeyStore") final KeyStore keyStore) {
        try {
            final var key = keyStore.getKey(refreshKeyAlias, refreshPrivateKeyPassphrase.toCharArray());
            if (key instanceof RSAPrivateKey privateKey) return privateKey;
        } catch (final UnrecoverableKeyException | NoSuchAlgorithmException | KeyStoreException e) {
            log.error("Unable to load private key from keystore: {}", refreshKeyStorePath, e);
        }

        throw new CodeAwareException(INTERNAL_JWT_SIGN_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Bean("publicRefreshValidationKey")
    public RSAPublicKey publicRefreshValidationKey(@Qualifier("refreshKeyStore") final KeyStore keyStore) {
        try {
            final var certificate = keyStore.getCertificate(refreshKeyAlias);
            final var publicKey = certificate.getPublicKey();

            if (publicKey instanceof RSAPublicKey rsaPublicKey) return rsaPublicKey;
        } catch (final KeyStoreException e) {
            log.error("Unable to load private key from keystore: {}", refreshKeyStorePath, e);
        }

        throw new CodeAwareException(INTERNAL_JWT_VALIDATION_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Bean("refreshJwtDecoder")
    public JwtDecoder refreshJwtDecoder(@Qualifier("publicRefreshValidationKey") final RSAPublicKey rsaPublicKey) {
        return NimbusJwtDecoder.withPublicKey(rsaPublicKey).build();
    }

}
