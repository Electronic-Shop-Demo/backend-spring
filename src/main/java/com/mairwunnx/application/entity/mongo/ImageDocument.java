package com.mairwunnx.application.entity.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.bson.codecs.pojo.annotations.BsonId;

import java.util.UUID;

@Data
@AllArgsConstructor
public final class ImageDocument {

    @BsonId @NonNull private UUID id;
    private long size;
    @NonNull private String type;
    private byte[] raw;

}
