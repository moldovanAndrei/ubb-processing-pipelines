package com.andrei.storm.bolt;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.storm.Config;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseTickTupleAwareRichBolt;
import org.apache.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.andrei.storm.data.SpeedMessage;

/**
 * Tick tuple based window bolt for computing the average speed of the incoming
 * {@link SpeedMessage}.
 * 
 * @author Andrei Moldovan
 * @since 06.05.2019
 */
public class AverageSpeedBolt extends BaseTickTupleAwareRichBolt {

	private static final long serialVersionUID = 3903111087334925039L;
	private static final Logger LOG = LoggerFactory.getLogger(AverageSpeedBolt.class);

	private Set<Double> speedValues;

	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.speedValues = new HashSet<>();
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		Config conf = new Config();
		conf.put(Config.TOPOLOGY_TICK_TUPLE_FREQ_SECS, 10);
		return conf;
	}

	@Override
	protected void process(Tuple tuple) {
		final SpeedMessage speedMessage = (SpeedMessage) tuple.getValueByField("speed-message");
		LOG.info("Got speed message: {}", speedMessage);
		speedValues.add(speedMessage.getSpeedValue());
	}

	@Override
	protected void onTickTuple(final Tuple tuple) {
		// compute average speed
		double averageSpeed = speedValues.stream().collect(Collectors.averagingDouble(v -> v));
		LOG.info("Average speed for window: " + averageSpeed);
		speedValues.clear();
	}

	/**
	 * @see org.apache.storm.topology.base.BaseWindowedBolt#declareOutputFields(OutputFieldsDeclarer)
	 */
	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// no-op
	}
}
