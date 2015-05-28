package org.x4444.storm;

import org.x4444.storm.bolt.StringAppenderBolt;
import org.x4444.storm.spout.RandomStringSpout;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.AuthorizationException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.BoltDeclarer;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;

public class SimpleTopology {

  public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException, AuthorizationException {
    TopologyBuilder builder = new TopologyBuilder();

    String[] words = new String[] { "Alex", "Misha", "Oleg", "Sergey", "Olga" };

    RandomStringSpout spout1 = new RandomStringSpout(words, "name", 20, 180);

    builder.setSpout("in1", spout1, 1);

    StringAppenderBolt bolt1 = new StringAppenderBolt("_123", "user_id");
    BoltDeclarer boltDec1 = builder.setBolt("simple_bolt1", bolt1, 1);
    boltDec1.shuffleGrouping("in1");

    Config conf = new Config();
    conf.setDebug(true);

    if (args != null && args.length > 0) {
      conf.setNumWorkers(2);
      StormSubmitter.submitTopologyWithProgressBar(args[0], conf, builder.createTopology());
    } else {
      LocalCluster cluster = new LocalCluster();
      cluster.submitTopology("simple1", conf, builder.createTopology());
      Utils.sleep(10000);
      cluster.killTopology("simple1");
      cluster.shutdown();
    }
  }
}
