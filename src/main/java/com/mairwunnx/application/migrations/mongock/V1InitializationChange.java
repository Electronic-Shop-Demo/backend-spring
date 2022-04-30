package com.mairwunnx.application.migrations.mongock;

import com.mairwunnx.application.Constants;
import com.mongodb.client.MongoDatabase;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.annotation.ParametersAreNonnullByDefault;

@RequiredArgsConstructor
@ChangeUnit(id = "initialization", order = "1", author = "Pavel Erokhin")
public final class V1InitializationChange {

    @NonNull private final MongoDatabase mongoDatabase;

    @Execution
    @ParametersAreNonnullByDefault
    public void execution() {
        mongoDatabase.createCollection(Constants.MongoDb.Collections.USERS);
        mongoDatabase.createCollection(Constants.MongoDb.Collections.PRODUCT_IMAGES);
    }

    @RollbackExecution
    public void rollback() {
        mongoDatabase.getCollection(Constants.MongoDb.Collections.USERS).drop();
        mongoDatabase.getCollection(Constants.MongoDb.Collections.PRODUCT_IMAGES).drop();
    }

}
