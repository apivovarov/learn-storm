package org.x4444.storm.spout;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.Config;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.testing.TestWordSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;

@SuppressWarnings("serial")
public class RandomStringSpout extends BaseRichSpout {
  public static Logger LOG = LoggerFactory.getLogger(TestWordSpout.class);
  boolean isDistributed;
  SpoutOutputCollector collector;
  final Random rand = new Random();
  final String[] words;
  String fieldName;
  final int minFreq;
  final int maxFreq;

  public RandomStringSpout() {
    this(new String[] { "" }, "col_1", 100, 100);
  }

  public RandomStringSpout(String[] words, String fieldName, int minFreq, int maxFreq) {
    this(words, fieldName, minFreq, maxFreq, true);
  }

  public RandomStringSpout(String[] words, String fieldName, int minFreq, int maxFreq, boolean isDistributed) {
    this.words = words;
    this.fieldName = fieldName;
    this.minFreq = minFreq;
    this.maxFreq = maxFreq;
    this.isDistributed = isDistributed;
  }

  @SuppressWarnings("rawtypes")
  public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
    this.collector = collector;
  }

  public void close() {

  }

  public void nextTuple() {
    Utils.sleep(minFreq + rand.nextInt(maxFreq - minFreq));
    String word = words[rand.nextInt(words.length)];
    collector.emit(new Values(word));
  }

  public void ack(Object msgId) {

  }

  public void fail(Object msgId) {

  }

  public void declareOutputFields(OutputFieldsDeclarer declarer) {
    declarer.declare(new Fields(fieldName));
  }

  @Override
  public Map<String, Object> getComponentConfiguration() {
    if (isDistributed) {
      return null;
    }
    Map<String, Object> ret = new HashMap<String, Object>();
    ret.put(Config.TOPOLOGY_MAX_TASK_PARALLELISM, 1);
    return ret;
  }
}