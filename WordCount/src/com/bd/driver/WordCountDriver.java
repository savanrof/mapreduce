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
		// Create a configuration object for the job
		JobConf job_conf = new JobConf(WordCountDriver.class);

		// Set a name of the Job
		job_conf.setJobName("Training");

		// Specify data type of output key and value
		job_conf.setOutputKeyClass(Text.class);
		job_conf.setOutputValueClass(Text.class);

		// Specify names of Mapper and Reducer Class
		job_conf.setMapperClass(TrainMapper.class);
		job_conf.setReducerClass(TrainReducer.class);

		// Specify formats of the data type of Input and output
		job_conf.setInputFormat(TextInputFormat.class);
		job_conf.setOutputFormat(TextOutputFormat.class);

		// Set input and output directories using command line arguments, 
		//arg[0] = name of input directory on HDFS, and arg[1] =  name of output directory to be created to store the output file.
		
		//FileInputFormat.setInputPaths(job_conf, new Path(args[0]));
		//FileOutputFormat.setOutputPath(job_conf, new Path(args[1]));
		FileInputFormat.setInputPaths(job_conf, new Path("D:\\Zoom\\PTDLL\\Input\\diabetes.txt"));
		FileOutputFormat.setOutputPath(job_conf, new Path("D:\\Zoom\\PTDLL\\Output\\" + OUT));
		
		my_client.setConf(job_conf);
		try {
			// Run the job 
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
