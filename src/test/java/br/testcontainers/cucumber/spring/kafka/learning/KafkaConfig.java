package br.testcontainers.cucumber.spring.kafka.learning;


import jakarta.annotation.PostConstruct;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.KafkaContainer;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@TestConfiguration
@Testcontainers
public abstract class KafkaConfig {

    @Container
    public static final KafkaContainer kafka = new KafkaContainer("apache/kafka-native:3.8.0")
            .withEnv("KAFKA_AUTO_CREATE_TOPICS_ENABLE", "true");
}
