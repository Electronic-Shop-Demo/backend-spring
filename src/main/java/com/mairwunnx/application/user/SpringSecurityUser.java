package com.mairwunnx.application.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public final class SpringSecurityUser implements UserDetails {

    @NonNull private UUID id;
    @NonNull private String email;
    @NonNull private String password;
    @NonNull private Collection<? extends GrantedAuthority> authorities;
    @NonNull private List<String> refreshTokens;
    private boolean active;

    @Override
    public @NotNull String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
