package br.testcontainers.cucumber.spring.kafka.learning;

import br.testcontainers.cucumber.spring.kafka.learning.config.KafkaContainerSetup;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;

@CucumberContextConfiguration
@SpringBootTest
public class RumCucumberTest {

    static {
        // Garante que o container está rodando antes dos testes
        KafkaContainerSetup.KAFKA_CONTAINER.start();
    }

    @DynamicPropertySource
    static void kafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", KafkaContainerSetup.KAFKA_CONTAINER::getBootstrapServers);
        registry.add("test.topic", () -> "embedded-test-topic");
    }
}
