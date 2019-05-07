package com.andrei.storm.data;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Kafka consumer speed message object.
 * 
 * @author Andrei Moldovan
 * @since 06.05.2019
 */
public class SpeedMessage {

	private Double speedValue;
	private LocalDateTime timestamp;

	@JsonCreator
	public SpeedMessage(@JsonProperty("speedValue") Double speedValue,
			@JsonProperty("timestamp") LocalDateTime timestamp) {
		super();
		this.speedValue = speedValue;
		this.timestamp = timestamp;
	}

	public Double getSpeedValue() {
		return this.speedValue;
	}

	public LocalDateTime getTimestamp() {
		return this.timestamp;
	}

	@Override
	public String toString() {
		return "SpeedMessage [speedValue=" + speedValue + ", timestamp=" + timestamp + "]";
	}
}
