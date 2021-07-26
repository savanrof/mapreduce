package com.bd.test;
import java.io.IOException;
import java.util.Iterator;   

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

import com.bd.train.TrainReducer;

public class TestReducer extends TrainReducer {

	public void reduce(Text t_key, Iterator<Text> values, OutputCollector<Text,Text> output, Reporter reporter) throws IOException {
		Text key = t_key;
		double MAX_VALUE = Integer.MIN_VALUE;
		double a = 0;
		String OUTCOME = "";
		String[] VALUE_SPLIT;
		while (values.hasNext()) {
			Text content = values.next();
			VALUE_SPLIT = content.toString().split("\\s+");
			a = Double.parseDouble(VALUE_SPLIT[0]);
			if (a > MAX_VALUE){
				MAX_VALUE = a;
				OUTCOME = VALUE_SPLIT[1];
			}
		}
		output.collect(key, new Text(OUTCOME));
	}

}
