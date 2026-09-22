package com.aitenant.web_service.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

import java.util.concurrent.TimeUnit;

@Configuration
public class MongoConfigs {

    @Value("${spring.data.mongodb.uri}")
    private String mongoUrl;

    @Primary
    @Bean(name = "mongoClientConfig")
    public MongoClient mongoConfig(){
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(mongoUrl))
                .applyToConnectionPoolSettings( connection ->
                        connection.maxSize(50)
                                .minSize(2)
                                .maxWaitTime(2000, TimeUnit.MILLISECONDS)
                                .maxConnectionIdleTime(6000,TimeUnit.MILLISECONDS)
                )
                .build();
        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    public MongoDatabaseFactory mongoDatabaseFactory(){
        return new SimpleMongoClientDatabaseFactory(mongoConfig(),"ai_tenent_history");
    }

    @Bean
    public MongoTemplate mongoTemplate(){
        return new MongoTemplate(mongoDatabaseFactory());
    }
}
