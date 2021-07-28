package com.bd.test;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Iterator;   

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

import com.bd.train.TrainReducer;

public class TestReducer extends TrainReducer {

	public void reduce(Text t_key, Iterator<Text> values, OutputCollector<Text,Text> output, Reporter reporter) throws IOException {
		Text key = t_key;
		BigDecimal max = new BigDecimal(Integer.MIN_VALUE);
		BigDecimal b = null;
		String OUTCOME = "";
		String[] VALUE_SPLIT;
		while (values.hasNext()) {
			Text content = values.next();
			VALUE_SPLIT = content.toString().split("\\s+");
			b = new BigDecimal(VALUE_SPLIT[0]);
			if (1 == b.compareTo(max)){
				max = b;
				OUTCOME = VALUE_SPLIT[1];
			}
		}
		output.collect(key, new Text(OUTCOME));
	}

}
