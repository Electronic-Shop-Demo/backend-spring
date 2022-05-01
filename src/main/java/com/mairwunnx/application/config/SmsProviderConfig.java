package com.mairwunnx.application.config;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter(onMethod_ = {@NotNull})
@SuppressWarnings("SpringElInspection")
public class SmsProviderConfig {

    @Value("#{environment.shop_smsprovider_login}") private String login;
    @Value("#{environment.shop_smsprovider_passw}") private String password;

}
