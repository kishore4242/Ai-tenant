package com.aitenant.web_service.config;


import com.aitenant.web_service.model.DataSourceType;
import com.aitenant.web_service.model.PrimaryReplica;
import com.aitenant.web_service.model.SecondaryReplica;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {
    private final PrimaryReplica primaryReplica;
    private final SecondaryReplica secondaryReplica;

    public DataSourceConfig(PrimaryReplica primaryReplica, SecondaryReplica secondaryReplica){
        this.primaryReplica = primaryReplica;
        this.secondaryReplica = secondaryReplica;
    }

    @Bean(name = "writeReplica")
    public HikariDataSource primaryReplicaSource(){
        HikariDataSource db = DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .url(primaryReplica.getUrl())
                .driverClassName(primaryReplica.getDriverClassName())
                .username(primaryReplica.getUser())
                .password(primaryReplica.getPassword())
                .build();
        db.setPoolName("write-replica");
        db.setMaximumPoolSize(primaryReplica.getPoolConnection());
        db.setReadOnly(false);
        db.setAutoCommit(false);
        return db;
    }

    @Bean(name = "readReplica")
    public DataSource secondaryReplicaSource(){
        HikariDataSource db = DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .url(secondaryReplica.getUrl())
                .driverClassName(secondaryReplica.getDriverClassName())
                .username(secondaryReplica.getUser())
                .password(secondaryReplica.getPassword())
                .build();
        db.setPoolName("read-replica");
        db.setMaximumPoolSize(secondaryReplica.getPoolConnection());
        db.setReadOnly(true);
        db.setAutoCommit(false);
        return db;
    }
    @Bean
    @Primary
    public DataSource routingDataSource(
            @Qualifier("writeReplica") DataSource writeReplica,
            @Qualifier("readReplica") DataSource readReplica) {

        ReplicationRoutingDataSourceConfig routingDataSource = new ReplicationRoutingDataSourceConfig();

        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceType.PRIMARY, writeReplica);
        targetDataSources.put(DataSourceType.SECONDARY, readReplica);

        routingDataSource.setTargetDataSources(targetDataSources);
        routingDataSource.setDefaultTargetDataSource(writeReplica);
        routingDataSource.afterPropertiesSet();

        return routingDataSource;
    }
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("routingDataSource") DataSource routingDataSource) {

        return builder
                .dataSource(routingDataSource)
                .packages("com.aitenant.web_service.model") // adjust to your entity package
                .persistenceUnit("default")
                .build();
    }

    @Bean
    public PlatformTransactionManager transactionManager(
            @Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}

