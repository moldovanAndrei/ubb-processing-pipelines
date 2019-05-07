package com.andrei.storm.topology;

import org.apache.storm.generated.StormTopology;
import org.apache.storm.kafka.spout.KafkaSpout;
import org.apache.storm.kafka.spout.KafkaSpoutConfig.FirstPollOffsetStrategy;
import org.apache.storm.topology.TopologyBuilder;

import com.andrei.storm.bolt.AverageSpeedBolt;
import com.andrei.storm.spout.KafkaSpeedMessageSpoutConfig;

/**
 * Creates the average speed topology.
 * 
 * @author Andrei Moldovan
 * @since 06.05.2019
 */
public class AverageSpeedTopology {

	public static StormTopology createTopology(String string, int numPartitions) {
		TopologyBuilder builder = new TopologyBuilder();

		builder.setSpout("speed-spout", new KafkaSpout<>(KafkaSpeedMessageSpoutConfig
				.getKafkaSpoutConfig("localhost:9092", "ubb-speed-topic", FirstPollOffsetStrategy.UNCOMMITTED_LATEST)),
				numPartitions);

		builder.setBolt("average-speed-bolt", new AverageSpeedBolt(), 1).shuffleGrouping("speed-spout", "speed-stream");

		return builder.createTopology();
	}

}
