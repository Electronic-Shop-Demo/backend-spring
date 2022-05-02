package com.mairwunnx.application.dal.repository.impl;

import com.mairwunnx.application.Constants;
import com.mairwunnx.application.dal.repository.ImageRepository;
import com.mairwunnx.application.entity.mongo.ImageDocument;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

@Component
@RequiredArgsConstructor
public class ImageRepositoryImpl implements ImageRepository {

    private final MongoCollection<ImageDocument> productImageCollection;

    @Override
    @ParametersAreNonnullByDefault
    public @Nullable ImageDocument findOneById(final UUID id) {
        return productImageCollection.find(eq(Constants.MongoDb.ID_NAME, id)).first();
    }

    @Override
    @ParametersAreNonnullByDefault
    public @NotNull ImageDocument insert(final ImageDocument entity) {
        productImageCollection.insertOne(entity);
        return entity;
    }

    @Override
    @ParametersAreNonnullByDefault
    public @NotNull ImageDocument replaceById(final ImageDocument entity, final UUID id) {
        productImageCollection.updateOne(
            eq(Constants.MongoDb.ID_NAME, id),
            Updates.combine(
                Updates.set("size", entity.getSize()),
                Updates.set("type", entity.getType()),
                Updates.set("raw", entity.getRaw())
            )
        );
        return entity;
    }

}
