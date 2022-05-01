package com.mairwunnx.application.config;

import com.mairwunnx.application.Constants;
import com.mairwunnx.application.entity.mongo.ImageDocument;
import com.mairwunnx.application.entity.mongo.UserDocument;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.security.SecureRandom;

import static com.mairwunnx.application.Constants.MongoDb.Collections.PRODUCT_IMAGES;
import static com.mairwunnx.application.Constants.MongoDb.Collections.USERS;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Log4j2
@RequiredArgsConstructor
@Configuration
@SuppressWarnings("resource")
public class MongoDbConfig {

    private final TrustManager[] mongoTrustManager;

    CodecRegistry pojoCodecRegistry() {
        return fromRegistries(
            MongoClientSettings.getDefaultCodecRegistry(),
            fromProviders(
                PojoCodecProvider.builder()
                    .automatic(true)
                    .register(new String[]{ImageDocument.class.getPackageName(), UserDocument.class.getPackageName()})
                    .register(new Class[]{ImageDocument.class, UserDocument.class})
                    .build()
            )
        );
    }

    @Bean
    MongoClient mongoClient() {
        final var clientSettings = MongoClientSettings.builder()
            .applicationName(Constants.MongoDb.APP)
            .applyConnectionString(mongoConnectionString())
            .uuidRepresentation(UuidRepresentation.STANDARD)
            .codecRegistry(pojoCodecRegistry())
            .applyToSslSettings(builder -> {
                try {
                    final var sc = SSLContext.getInstance("SSL");
                    sc.init(null, mongoTrustManager, SecureRandom.getInstanceStrong());
                    builder.invalidHostNameAllowed(true).context(sc);
                } catch (final Exception ex) {
                    log.fatal("Can't apply ssl settings while constructing mongodb client.", ex);
                    throw new RuntimeException(ex);
                }
            })
            .build();

        final var mongoClient = MongoClients.create(clientSettings);
        PojoCodecProvider.builder().automatic(true).build();
        return mongoClient;
    }

    @Bean
    MongoDatabase mongoDatabase() {
        return mongoClient().getDatabase(Constants.MongoDb.DB);
    }

    @Bean
    MongoCollection<ImageDocument> imagesCollection() {
        return mongoDatabase().getCollection(PRODUCT_IMAGES, ImageDocument.class);
    }

    @Bean
    MongoCollection<UserDocument> usersCollection() {
        return mongoDatabase().getCollection(USERS, UserDocument.class);
    }

    @NotNull ConnectionString mongoConnectionString() {
        return new ConnectionString(System.getenv(Constants.MongoDb.CONNECTION_STRING_REF));
    }

}
