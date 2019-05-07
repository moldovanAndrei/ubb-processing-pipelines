package com.andrei.storm.spout;

import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.andrei.storm.data.SpeedMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Custom Kafka {@link SpeedMessage} deserializer which allows Java 8 types.
 * 
 * @author Andrei Moldovan
 * @since 06.05.2019
 */
public class SpeedMessageDeserializer extends JsonDeserializer<SpeedMessage> {

	public SpeedMessageDeserializer() {
		super(objectMapper());
		addTrustedPackages("*");
	}

	private static ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		return mapper;
	}
}
