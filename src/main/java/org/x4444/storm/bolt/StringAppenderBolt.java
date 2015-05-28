package org.x4444.storm.bolt;

import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

@SuppressWarnings("serial")
public class StringAppenderBolt extends BaseRichBolt {

  OutputCollector collector;

  final String fieldName;
  final String suffix;
  
  public StringAppenderBolt() {
    this("", "col_0");
  }

  public StringAppenderBolt(String suffix, String fieldName) {
    this.suffix = suffix;
    this.fieldName = fieldName;
  }

  @Override
  public void declareOutputFields(OutputFieldsDeclarer declarer) {
    declarer.declare(new Fields(fieldName));
  }

  @Override
  public void execute(Tuple input) {
    String in0 = input.getString(0);
    String out0 = in0.toLowerCase() + suffix;
    Values vals = new Values(out0);
    collector.emit(input, vals);
  }

  @SuppressWarnings("rawtypes")
  @Override
  public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
    this.collector = collector;
  }
}
