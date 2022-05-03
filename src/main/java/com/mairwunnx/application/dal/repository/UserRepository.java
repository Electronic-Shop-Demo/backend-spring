package com.mairwunnx.application.dal.repository;

import com.mairwunnx.application.entity.mongo.UserDocument;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.UUID;

public interface UserRepository {

    @ParametersAreNonnullByDefault
    @Nullable UserDocument findByEmail(final String email);

    @ParametersAreNonnullByDefault
    @Nullable UserDocument findById(final UUID id);

    @ParametersAreNonnullByDefault
    boolean isExistByEmail(final String email);

    @ParametersAreNonnullByDefault
    @NotNull UserDocument insert(final UserDocument entity);

    @ParametersAreNonnullByDefault
    void removeById(final UUID id);

    @ParametersAreNonnullByDefault
    void updateRefreshTokenByEmail(final String email, final String refreshToken);

    @ParametersAreNonnullByDefault
    void removeRefreshTokenAndAddNewByEmail(final String email, final String refreshToken, final String newToken);

    @ParametersAreNonnullByDefault
    void removeCurrentRefreshTokenById(final UUID id, final String refreshToken);

}
