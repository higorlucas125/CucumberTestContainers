package br.testcontainers.cucumber.spring.kafka.learning;

import br.testcontainers.cucumber.spring.kafka.learning.producer.MessageProducer;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.KafkaContainer;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Testcontainers
public class LearningApplicationTest {

    @Container
    static final KafkaContainer kafka = new KafkaContainer("apache/kafka-native:3.8.0")
            .withEnv("KAFKA_AUTO_CREATE_TOPICS_ENABLE", "true");

    @DynamicPropertySource
    static void initKafkaProperties(DynamicPropertyRegistry registry){
        registry.add("spring.kafka.bootstrap-servers",kafka::getBootstrapServers);
    }

    @BeforeAll
    static void setUp() {
        kafka.start();
        try (AdminClient adminClient = AdminClient.create(Map.of(
                AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers()
        ))) {
            adminClient.createTopics(List.of(new NewTopic("any-topic-name", 1, (short) 1)));
        }
    }

    @Autowired
    private MessageProducer messageProducer;

    @Test
    public void initTestContainersWithKafka(){

        messageProducer.sendMessage("Ola mundo");

        await().pollInterval(Duration.ofSeconds(3)).atMost(10, TimeUnit.SECONDS).untilAsserted(() ->{
            
        });
    }
}
