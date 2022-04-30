package com.mairwunnx.application.entity.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.bson.codecs.pojo.annotations.BsonId;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public final class UserDocument {

    @BsonId @NonNull private UUID id;
    @Nullable private UUID avatar;
    @NonNull private String email;
    @NonNull private String phone;
    @NonNull private String password;
    @NonNull private String fullname;
    @NonNull private LocalDateTime creationDate;
    @NonNull private LocalDateTime lastVisitDate;
    @NonNull private UUID favorite;
    @NonNull private UUID cart;
    @NonNull private UUID order;
    @NonNull private UUID userSettings;
    @NonNull private List<String> authorities;
    @NonNull private List<String> refreshTokens;
    private boolean active;

}
