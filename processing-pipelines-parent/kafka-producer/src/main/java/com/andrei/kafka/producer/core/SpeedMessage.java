package com.andrei.kafka.producer.core;

import java.time.LocalDateTime;

/**
 * Kafka producer speed message object.
 * 
 * @author Andrei Moldovan
 * @since 06.05.2019
 */
public class SpeedMessage {

	private Double speedValue;
	private LocalDateTime timestamp;

	public SpeedMessage(Double speedValue, LocalDateTime timestamp) {
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
