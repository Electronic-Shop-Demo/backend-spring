package com.mairwunnx.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

@Configuration
public class MongoTrustManagerConfig {

    @Bean
    TrustManager[] mongoTrustManager() {
        return new TrustManager[]{new X509TrustManager() { // trust all certs implementation (thanks to Bogdan Mart).
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(final X509Certificate[] certs, final String authType) {
            }

            public void checkServerTrusted(final X509Certificate[] certs, final String authType) {
            }
        }};
    }

}
