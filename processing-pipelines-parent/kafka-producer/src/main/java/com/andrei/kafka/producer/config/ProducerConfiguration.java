package com.andrei.kafka.producer.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.andrei.kafka.producer.core.SpeedMessage;
import com.andrei.kafka.producer.core.SpeedMessageProducer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Kafka producer configuration class.
 * 
 * @author Andrei Moldovan
 * @since 06.05.2019
 */
@Configuration
public class ProducerConfiguration {

	@Value(value = "${kafka.bootstrapAddress}")
	private String bootstrapAddress;

	@Bean
	public ProducerFactory<String, SpeedMessage> greetingProducerFactory() {
		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapAddress);
		configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

		DefaultKafkaProducerFactory<String, SpeedMessage> factory = new DefaultKafkaProducerFactory<>(configProps);
		// Override {@link ObjectMapper} to alow serialization of Java 8 types.
		JsonSerializer serializer = new JsonSerializer<>(objectMapper());
		// Remove type info, so the client can deserialize the object.
		serializer.setAddTypeInfo(false);
		factory.setValueSerializer(serializer);
		return factory;
	}

	@Bean
	public KafkaTemplate<String, SpeedMessage> customKafkaTemplate() {
		return new KafkaTemplate<>(greetingProducerFactory());
	}

	@Bean
	public SpeedMessageProducer customMessageProducer() {
		return new SpeedMessageProducer();
	}

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		return mapper;
	}
}
