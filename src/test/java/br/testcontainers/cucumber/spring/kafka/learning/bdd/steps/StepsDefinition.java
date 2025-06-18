package br.testcontainers.cucumber.spring.kafka.learning.bdd.steps;

import br.testcontainers.cucumber.spring.kafka.learning.consumer.MessageConsumer;
import br.testcontainers.cucumber.spring.kafka.learning.producer.MessageProducer;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Value;

import static org.testcontainers.shaded.org.hamcrest.MatcherAssert.assertThat;
import static org.testcontainers.shaded.org.hamcrest.Matchers.containsString;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class StepsDefinition {


    private MessageConsumer consumer;


    private MessageProducer producer;


    public StepsDefinition(MessageConsumer consumer, MessageProducer producer){
        this.consumer = consumer;
        this.producer = producer;
    }

    @Value("${test.topic}")
    private String topic;

    private boolean messageReceived = false;
    private final String data = "The Integration is Working Fine!";

    @Given("there is a message produced by the Kafka Producer")
    public void thereIsAMessageProducedByTheKafkaProducer() {
        producer.sendMessage(topic, data);
    }

    @When("we fetch for new message in the Kafka Consumer")
    public void weFetchForNewMessageInTheKafkaConsumer() throws InterruptedException {
        messageReceived = consumer.getLatch().await(10, TimeUnit.SECONDS);
    }

    @Then("the message should be retrieved.")
    public void theMessageShouldBeRetrieved() {
        assertTrue(messageReceived);
        assertThat(consumer.getPayload(), containsString(data));
    }
}
