package br.testcontainers.cucumber.spring.kafka.learning;

import br.testcontainers.cucumber.spring.kafka.learning.producer.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LearningApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(LearningApplication.class, args);
	}

	@Autowired
	MessageProducer messageProducer;

	@Override
	public void run(String... args) throws Exception {

		messageProducer.sendMessage("Novos dados");
	}
}
