package com.bd.driver;
import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

import com.bd.test.TestMapper;
import com.bd.test.TestReducer;
import com.bd.train.TrainMapper;
import com.bd.train.TrainReducer;

public class WordCountDriver {
	public final static String OUT = System.nanoTime() + ""; 
	public static void main(String[] args) throws IOException {
		JobClient my_client = new JobClient();
		JobConf job_conf = new JobConf(WordCountDriver.class);

		job_conf.setJobName("Training");

		job_conf.setOutputKeyClass(Text.class);
		job_conf.setOutputValueClass(Text.class);

		job_conf.setMapperClass(TrainMapper.class);
		job_conf.setReducerClass(TrainReducer.class);

		job_conf.setInputFormat(TextInputFormat.class);
		job_conf.setOutputFormat(TextOutputFormat.class);

		FileInputFormat.setInputPaths(job_conf, new Path("D:\\Zoom\\PTDLL\\Input\\huanluyen.txt"));
		FileOutputFormat.setOutputPath(job_conf, new Path("D:\\Zoom\\PTDLL\\Output\\" + OUT));
		
		my_client.setConf(job_conf);
		try {
			JobClient.runJob(job_conf);
	    	System.out.println("Training done!");
	    	
		} catch (Exception e) {
			e.printStackTrace();
		}
		job_conf.setJobName("Test");
		job_conf.setOutputKeyClass(Text.class);
		job_conf.setOutputValueClass(Text.class);
		job_conf.setMapperClass(TestMapper.class);
		job_conf.setReducerClass(TestReducer.class);
		job_conf.setInputFormat(TextInputFormat.class);
		job_conf.setOutputFormat(TextOutputFormat.class);
		FileInputFormat.setInputPaths(job_conf, new Path("D:\\Zoom\\PTDLL\\Input\\predict.txt"));
		FileOutputFormat.setOutputPath(job_conf, new Path("D:\\Zoom\\PTDLL\\Output\\Predict-"+OUT));
		my_client.setConf(job_conf);
		try {
			JobClient.runJob(job_conf);
			System.out.println("Test done! Check result in D:\\Zoom\\PTDLL\\Output\\Predict-"+OUT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
