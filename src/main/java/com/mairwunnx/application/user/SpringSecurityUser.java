package com.mairwunnx.application.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public final class SpringSecurityUser implements UserDetails {

    @NonNull private UUID id;
    @Nullable private UUID avatar;
    @NonNull private String email;
    @NonNull private String phone;
    @NonNull private String password;
    @NonNull private String fullname;
    @NonNull private Instant creationDate;
    @NonNull private Instant lastVisitDate;
    @NonNull private UUID favorite;
    @NonNull private UUID cart;
    @NonNull private UUID order;
    @NonNull private UUID userSettings;
    @NonNull private List<String> authorities;
    @NonNull private List<String> refreshTokens;
    private boolean active;

    @Override
    public @NotNull Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities.stream().map(SimpleGrantedAuthority::new).toList();
    }

    @Override
    public @NotNull String getUsername() {
        return getFullname();
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
