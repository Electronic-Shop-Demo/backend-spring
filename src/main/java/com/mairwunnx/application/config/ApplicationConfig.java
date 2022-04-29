package com.mairwunnx.application.config;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("unused")
public class ApplicationConfig {

    public static final String PRODUCTION_STAGE = "production";
    public static final String DEVELOPMENT_STAGE = "development";

    @NotNull
    @Getter
    @Value("${app.configuration.stage}")
    private String stage;

}
