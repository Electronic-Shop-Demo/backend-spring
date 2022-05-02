package com.mairwunnx.application.config;

import com.mongodb.client.model.Collation;
import com.mongodb.client.model.CollationStrength;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
public class CollationCaseInsensitiveConfig {

    @Bean("insensitiveCollation")
    @Qualifier("insensitiveCollation")
    Collation insensitiveCollation() {
        return Collation.builder()
            .locale(Locale.US.toString())
            .collationStrength(CollationStrength.SECONDARY)
            .caseLevel(false)
            .build();
    }

}
