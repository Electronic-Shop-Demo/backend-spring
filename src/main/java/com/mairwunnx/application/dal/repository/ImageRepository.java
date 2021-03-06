package com.mairwunnx.application.dal.repository;

import com.mairwunnx.application.entity.mongo.ImageDocument;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.UUID;

public interface ImageRepository {

    @ParametersAreNonnullByDefault
    @Nullable ImageDocument findOneById(final UUID id);

    @ParametersAreNonnullByDefault
    @NotNull ImageDocument insert(final ImageDocument entity);

    @ParametersAreNonnullByDefault
    @NotNull ImageDocument replaceById(final ImageDocument entity, final UUID id);

}
