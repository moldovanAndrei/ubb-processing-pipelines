package com.andrei.storm.spout;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.storm.kafka.spout.ByTopicRecordTranslator;
import org.apache.storm.kafka.spout.KafkaSpoutConfig;
import org.apache.storm.kafka.spout.KafkaSpoutConfig.FirstPollOffsetStrategy;
import org.apache.storm.kafka.spout.KafkaSpoutRetryExponentialBackoff;
import org.apache.storm.kafka.spout.KafkaSpoutRetryExponentialBackoff.TimeInterval;
import org.apache.storm.kafka.spout.KafkaSpoutRetryService;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import com.andrei.storm.data.SpeedMessage;

/**
 * Kafka spout configuration.
 * 
 * @author Andrei Moldovan
 * @since 06.05.2019
 */
public class KafkaSpeedMessageSpoutConfig {

	public static KafkaSpoutConfig<Long, SpeedMessage> getKafkaSpoutConfig(String bootstrapServers, String topic,
			FirstPollOffsetStrategy firstPollOffsetStrategy) {
		ByTopicRecordTranslator<String, String> trans = new ByTopicRecordTranslator<>(
				(r) -> new Values(r.topic(), r.partition(), r.offset(), r.key(), r.value()),
				new Fields("topic", "partition", "offset", "key", "speed-message"), "speed-stream");

		return KafkaSpoutConfig.builder(bootstrapServers, topic).setRecordTranslator(trans)
				.setProp(ConsumerConfig.GROUP_ID_CONFIG, "storm-consumer").setKey(LongDeserializer.class)
				.setValue(SpeedMessageDeserializer.class).setOffsetCommitPeriodMs(200)
				.setProp(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true")
				.setProp(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1000)
				.setFirstPollOffsetStrategy(firstPollOffsetStrategy).setMaxUncommittedOffsets(100)
				.setRetry(getRetryService()).build();
	}

	private static KafkaSpoutRetryService getRetryService() {
		return new KafkaSpoutRetryExponentialBackoff(TimeInterval.microSeconds(500), TimeInterval.milliSeconds(2),
				Integer.MAX_VALUE, TimeInterval.seconds(5));
	}
}
