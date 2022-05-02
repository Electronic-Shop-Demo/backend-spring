package com.mairwunnx.application.migrations.mongock;

import com.mairwunnx.application.Constants;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.*;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.annotation.ParametersAreNonnullByDefault;

import java.util.Locale;

import static com.mairwunnx.application.Constants.MongoDb.Authors.PAVEL_EROKHIN;

@RequiredArgsConstructor
@ChangeUnit(id = "addcaseinsensetiveindexes", order = "2", author = PAVEL_EROKHIN)
public final class V2AddCaseInsensetiveIndexes {

    @NonNull private final MongoDatabase mongoDatabase;

    @Execution
    @ParametersAreNonnullByDefault
    public void execution() {
        final var users = mongoDatabase.getCollection(Constants.MongoDb.Collections.USERS);
        final var indexOptions = new IndexOptions();
        indexOptions.collation(
            Collation.builder()
                .locale(Locale.US.toString())
                .collationStrength(CollationStrength.SECONDARY)
                .caseLevel(false)
                .build()
        );

        users.createIndex(Indexes.ascending("email"), indexOptions);
    }

    @RollbackExecution
    public void rollback() {
        final var users = mongoDatabase.getCollection(Constants.MongoDb.Collections.USERS);
        users.dropIndex(Indexes.ascending("email"));
    }

}
