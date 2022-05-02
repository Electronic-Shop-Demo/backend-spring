package com.mairwunnx.application.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.mairwunnx.application.Constants.Api.ENDPOINT_BASE;
import static com.mairwunnx.application.Constants.Jwt.Authorities.AUTHENTICATED_USER;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    @Bean
    @ParametersAreNonnullByDefault
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        http
            .csrf().disable().cors().disable()
            .authorizeRequests().antMatchers(ENDPOINT_BASE + "/auth/register").not().hasAuthority(AUTHENTICATED_USER).and()
            .authorizeRequests().antMatchers(ENDPOINT_BASE + "/auth/logout").hasAuthority(AUTHENTICATED_USER).and()
            .authorizeRequests().antMatchers(ENDPOINT_BASE + "/auth/refresh").hasAuthority(AUTHENTICATED_USER).and()
            .authorizeRequests().antMatchers(ENDPOINT_BASE + "/auth/**").permitAll().and()
            .authorizeRequests().antMatchers(ENDPOINT_BASE + "/confirm/**").hasAuthority(AUTHENTICATED_USER).and()
            .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
        return http.build();
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder();
    }

    @Autowired
    public void configAuthBuilder(final AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsService);
    }

}
