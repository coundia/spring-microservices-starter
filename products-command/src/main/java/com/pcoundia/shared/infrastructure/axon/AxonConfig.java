package com.pcoundia.shared.infrastructure.axon;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.axonframework.common.jdbc.ConnectionProvider;
import org.axonframework.common.jdbc.DataSourceConnectionProvider;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.jdbc.JdbcEventStorageEngine;
import org.axonframework.messaging.StreamableMessageSource;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class AxonConfig {

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
            .type(HikariDataSource.class)
            .url("jdbc:postgresql://localhost:5432/pcoundia")
            .username("pcoundia")
            .password("pcoundia")
            .build();
    }

    @Bean
    public PlatformTransactionManager platformTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public ConnectionProvider connectionProvider(DataSource dataSource) {
        return new DataSourceConnectionProvider(dataSource);
    }

    // Use JacksonSerializer for JSON event payload
    @Bean
    public Serializer eventSerializer() {

        return JacksonSerializer.defaultSerializer();
    }

    @Bean
    public EventStorageEngine eventStorageEngine(
        ConnectionProvider connectionProvider,
        TransactionManager axonTransactionManager,
        Serializer eventSerializer
    ) {
        return JdbcEventStorageEngine.builder()
            .connectionProvider(connectionProvider)
            .transactionManager(axonTransactionManager)
            .eventSerializer(eventSerializer)
            .build();
    }

    @Bean
    public EmbeddedEventStore eventStore(
        EventStorageEngine storageEngine,
        org.axonframework.config.Configuration configuration
    ) {
        return EmbeddedEventStore.builder()
            .storageEngine(storageEngine)
            .messageMonitor(configuration.messageMonitor(StreamableMessageSource.class, "eventStore"))
            .build();
    }
}
