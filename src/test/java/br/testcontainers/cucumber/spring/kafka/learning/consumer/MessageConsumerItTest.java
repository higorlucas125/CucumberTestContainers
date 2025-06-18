package br.testcontainers.cucumber.spring.kafka.learning.consumer;

import br.testcontainers.cucumber.spring.kafka.learning.IntegrationTest;
import br.testcontainers.cucumber.spring.kafka.learning.KafkaConfig;
import br.testcontainers.cucumber.spring.kafka.learning.producer.MessageProducer;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.mockito.Mockito.atLeastOnce;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;



@IntegrationTest
public class MessageConsumerItTest {


    @Autowired
    private MessageProducer messageProducer;

    @SpyBean
    private MessageConsumer messageConsumer;

    @DynamicPropertySource
    static void initKafkaProperties(DynamicPropertyRegistry registry){
        registry.add("spring.kafka.bootstrap-servers",KafkaConfig.kafka::getBootstrapServers);
    }

    @BeforeAll
    static void setUp() {
        KafkaConfig.kafka.start();
        try (AdminClient adminClient = AdminClient.create(Map.of(
                AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfig.kafka.getBootstrapServers()
        ))) {
            adminClient.createTopics(List.of(new NewTopic("any-topic-name", 1, (short) 1)));
        }
    }

    @Test
    void shouldHandleProductPriceChangedEvent() {
        String testMessage = "Ola mundo";
        messageProducer.sendMessage(testMessage);

        await().pollInterval(Duration.ofSeconds(1))
                .atMost(Duration.ofSeconds(15)) // Aumentado para 15s
                .untilAsserted(() -> {
                    Mockito.verify(messageConsumer, atLeastOnce()).consumerMessage(testMessage);
                });
    }
}