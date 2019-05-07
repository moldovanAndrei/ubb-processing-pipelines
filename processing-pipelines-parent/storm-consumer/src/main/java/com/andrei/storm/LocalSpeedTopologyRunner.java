package com.andrei.storm;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;

import com.andrei.storm.topology.AverageSpeedTopology;

/**
 * Main class for the average speed topology, local simulation cluster mode.
 * 
 * @author Andrei Moldovan
 * @since 06.05.2019
 */
public class LocalSpeedTopologyRunner {
	
	/**
	 * Main method.
	 * 
	 * @param args main arguments.
	 * @throws AuthorizationException
	 */
	public static void main(String[] args) {
		new LocalSpeedTopologyRunner().runMain();
	}

	private void runMain() {
		Config tpConf = getLocalConfig();

		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("speed-topology", tpConf,
				AverageSpeedTopology.createTopology("localhost:9092", 2));
	}

	private Config getLocalConfig() {
		Config config = new Config();
		config.setDebug(false);
		config.setMessageTimeoutSecs(30);

		return config;
	}
}
