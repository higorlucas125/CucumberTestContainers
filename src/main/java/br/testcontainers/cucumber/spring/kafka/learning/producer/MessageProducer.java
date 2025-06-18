package br.testcontainers.cucumber.spring.kafka.learning.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {

    private final KafkaTemplate<String,String> kafkaTemplate;
    private final String TOPIC_NAME = "any-topic-name";

    public MessageProducer(KafkaTemplate<String,String> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message ){
        kafkaTemplate.send(TOPIC_NAME,message);
        System.out.println("Message " +  message + " has been sucessfully sent to the topic: " + TOPIC_NAME);
    }

    public void sendMessage(String topicName,String message ){
        kafkaTemplate.send(topicName,message);
        System.out.println("Message " +  message + " has been sucessfully sent to the topic: " + topicName);
    }
}
