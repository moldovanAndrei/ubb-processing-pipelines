package com.andrei.kafka.producer;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.andrei.kafka.producer.core.SpeedMessage;
import com.andrei.kafka.producer.core.SpeedMessageProducer;

/**
 * Spring Boot App for the kafka consumer.
 *
 * @author Andrei Moldovan
 * @since 06.06.2019
 */
@SpringBootApplication
public class ProducerApplication {

	public static void main(String[] args) throws InterruptedException {
		ConfigurableApplicationContext context = SpringApplication.run(ProducerApplication.class);
		SpeedMessageProducer customMessageProducer = context.getBean(SpeedMessageProducer.class);

		Random rand = new Random(System.currentTimeMillis());
		
		while (true) {
			Double speedValue = rand.nextDouble()  * 100 + 30;
			customMessageProducer.sendMessage(new SpeedMessage(speedValue, LocalDateTime.now()));
			Thread.sleep(1000);
		}
	}
}
