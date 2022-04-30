package com.mairwunnx.application.entity.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public final class UserDocument {

    private UUID id;
    private UUID avatar;
    private String email;
    private String phone;
    private String password;
    private String fullname;
    private LocalDateTime creationDate;
    private LocalDateTime lastVisitDate;
    private UUID favorite;
    private UUID cart;
    private UUID order;
    private UUID userSettings;
    private List<String> authorities;
    private List<String> refreshTokens;
    private boolean active;

}
