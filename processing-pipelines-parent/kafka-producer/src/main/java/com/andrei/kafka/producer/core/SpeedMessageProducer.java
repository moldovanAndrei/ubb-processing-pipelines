package com.andrei.kafka.producer.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * Speed message producer implementation for sending {@link SpeedMessage}
 * messages.
 * 
 * @author Andrei Moldovan
 * @since 06.05.2019
 */
public class SpeedMessageProducer {

	private static final Logger LOG = LoggerFactory.getLogger(SpeedMessageProducer.class);

	@Autowired
	private KafkaTemplate<String, SpeedMessage> kafkaTemplate;

	@Value(value = "${speed.topic.name}")
	private String topicName;

	/**
	 * Sends the speed message to the kafka topic.
	 * 
	 * @param message the speed message.
	 */
	public void sendMessage(SpeedMessage message) {
		LOG.info("Sending message \"{}\"", message);
		this.kafkaTemplate.send(this.topicName, message);
	}
}
