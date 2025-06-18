package br.testcontainers.cucumber.spring.kafka.learning.bdd.config;

import br.testcontainers.cucumber.spring.kafka.learning.RumCucumberTest;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resource/features",
        glue = "br.testcontainers.cucumber.spring.kafka.learning",
        plugin = {"pretty", "html:target/cucumber-report.html"},
        monochrome = true
)
public class CucumberConfig extends RumCucumberTest {
}
