package com.bd.train;
import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.*;


public class TrainMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {
	private Text KEY = new Text();
    private Text CONTENT = new Text();

	public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
		String valueString = value.toString();
		String[] arr = valueString.split("\\s++");
		KEY.set(arr[8]);
		CONTENT.set(arr[0]+" "+arr[1]+" "+arr[2]+" "+arr[3]+" "+arr[4]+" "+arr[5]+" "+arr[6]+" "+arr[7]);
		output.collect(KEY,CONTENT);
	}
}