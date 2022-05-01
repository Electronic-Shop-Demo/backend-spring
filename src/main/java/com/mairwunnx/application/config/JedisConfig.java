package com.mairwunnx.application.config;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter(onMethod_ = {@NotNull})
@SuppressWarnings("SpringElInspection")
public class JedisConfig {

    @Value("#{environment.shop_jedis_host}") private String host;
    @Value("#{environment.shop_jedis_port}") private String port;
    @Value("#{environment.shop_jedis_pass}") private String pass;
    @Value("#{environment.shop_jedis_timo}") private String timo;

}
