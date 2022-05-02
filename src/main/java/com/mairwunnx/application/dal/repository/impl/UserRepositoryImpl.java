package com.mairwunnx.application.dal.repository.impl;

import com.mairwunnx.application.Constants;
import com.mairwunnx.application.dal.repository.UserRepository;
import com.mairwunnx.application.entity.mongo.UserDocument;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Collation;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

@Component
@RequiredArgsConstructor
public final class UserRepositoryImpl implements UserRepository {

    private final MongoCollection<UserDocument> usersCollection;
    @Qualifier("insensitiveCollation") private final Collation insensitiveCollation;

    @Override
    @ParametersAreNonnullByDefault
    public @Nullable UserDocument findByEmail(final String email) {
        return usersCollection.find(eq("email", email)).collation(insensitiveCollation).first();
    }

    @Override
    @ParametersAreNonnullByDefault
    public @Nullable UserDocument findById(final UUID id) {
        return usersCollection.find(eq(Constants.MongoDb.ID_NAME, id)).first();
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean isExistByEmail(final String email) {
        return usersCollection.countDocuments(eq("email", email)) != 0;
    }

    @Override
    @ParametersAreNonnullByDefault
    public @NotNull UserDocument insert(final UserDocument entity) {
        usersCollection.insertOne(entity);
        return entity;
    }

    @Override
    @ParametersAreNonnullByDefault
    public void updateRefreshTokenByEmail(final String email, final String refreshToken) {
        usersCollection.findOneAndUpdate(
            eq("email", email),
            new BasicDBObject("$push", new BasicDBObject("refreshTokens", refreshToken)),
            new FindOneAndUpdateOptions().collation(insensitiveCollation)
        );
    }

    @Override
    @ParametersAreNonnullByDefault
    public void removeRefreshTokenAndAddNewByEmail(final String email, final String refreshToken, final String newToken) {
        usersCollection.findOneAndUpdate(
            eq("email", email),
            new BasicDBObject("$pull", new BasicDBObject("refreshTokens", refreshToken)),
            new FindOneAndUpdateOptions().collation(insensitiveCollation)
        );

        usersCollection.findOneAndUpdate(
            eq("email", email),
            new BasicDBObject("$push", new BasicDBObject("refreshTokens", newToken)),
            new FindOneAndUpdateOptions().collation(insensitiveCollation)
        );
    }

    @Override
    @ParametersAreNonnullByDefault
    public void removeCurrentRefreshTokenById(final UUID id, final String refreshToken) {
        usersCollection.findOneAndUpdate(
            eq(Constants.MongoDb.ID_NAME, id),
            new BasicDBObject("$pull", new BasicDBObject("refreshTokens", refreshToken))
        );
    }

}