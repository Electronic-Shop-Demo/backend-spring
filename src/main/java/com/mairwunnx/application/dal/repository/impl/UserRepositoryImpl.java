package com.mairwunnx.application.dal.repository.impl;

import com.mairwunnx.application.Constants;
import com.mairwunnx.application.dal.repository.UserRepository;
import com.mairwunnx.application.entity.mongo.UserDocument;
import com.mairwunnx.application.types.UsersSortVariant;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Collation;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import lombok.RequiredArgsConstructor;
import org.bson.conversions.Bson;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

@Component
@RequiredArgsConstructor
public final class UserRepositoryImpl implements UserRepository {

    private final MongoCollection<UserDocument> usersCollection;
    @Qualifier("insensitiveCollation") private final Collation insensitiveCollation;

    @Override
    public @NotNull List<UserDocument> getAll(final int page, @Nullable final UsersSortVariant sort) {
        return usersCollection.find()
            .sort(sort(sort))
            .skip(Constants.Pagination.USERS_PER_PAGE * (page - 1))
            .limit(Constants.Pagination.USERS_PER_PAGE)
            .into(new ArrayList<>());
    }

    @Override
    public @NotNull List<UserDocument> getInactiveUsers(final int page, @Nullable final UsersSortVariant sort) {
        return usersCollection.find()
            .sort(sort(sort))
            .filter(eq("active", false))
            .skip(Constants.Pagination.USERS_PER_PAGE * (page - 1))
            .limit(Constants.Pagination.USERS_PER_PAGE)
            .into(new ArrayList<>());
    }

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
    public @Nullable UserDocument changeAvatar(final UUID id, final UUID imageId) {
        return usersCollection.findOneAndUpdate(
            eq(Constants.MongoDb.ID_NAME, id),
            Updates.set("avatar", imageId)
        );
    }

    @Override
    @ParametersAreNonnullByDefault
    public @Nullable UserDocument changeEmail(final UUID id, final String email) {
        return usersCollection.findOneAndUpdate(
            eq(Constants.MongoDb.ID_NAME, id),
            Updates.set("email", email)
        );
    }

    @Override
    @ParametersAreNonnullByDefault
    public @NotNull UserDocument insert(final UserDocument entity) {
        usersCollection.insertOne(entity);
        return entity;
    }

    @Override
    @ParametersAreNonnullByDefault
    public void removeById(final UUID id) {
        usersCollection.deleteOne(eq(Constants.MongoDb.ID_NAME, id));
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

    @Contract("null -> null")
    @ParametersAreNonnullByDefault
    private @Nullable Bson sort(@Nullable final UsersSortVariant sort) {
        return switch (sort) {
            case EMAIL_ASC -> Sorts.ascending("email");
            case EMAIL_DESC -> Sorts.descending("email");
            case FULLNAME_ASC -> Sorts.ascending("fullname");
            case FULLNAME_DESC -> Sorts.descending("fullname");
            case CREATION_DATE_ASC -> Sorts.ascending("creationDate");
            case CREATION_DATE_DESC -> Sorts.descending("creationDate");
            case null -> null;
        };
    }

}
