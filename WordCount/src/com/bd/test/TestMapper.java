package com.bd.test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

import com.bd.driver.WordCountDriver;
import com.bd.fileReader.DiabetesReaderService;
import com.bd.model.Diabetes;
import com.bd.service.CalculateService;
import com.bd.train.TrainMapper;

public class TestMapper extends TrainMapper{
	
	private String OUT = WordCountDriver.OUT;
	private int index = 1;

	@Override
	public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
		List<Diabetes> list1 = new ArrayList<Diabetes>();
		List<Diabetes> list0 = new ArrayList<Diabetes>();
		CalculateService calculate = new CalculateService();
		DiabetesReaderService reader = new DiabetesReaderService();
		Map<Integer, List<Diabetes>> map = new HashMap<>();
		Map<String, String> npMap = new HashMap<>();
		Diabetes diabetesInput = new Diabetes();
		Diabetes mean1 = new Diabetes();
		Diabetes mean0 = new Diabetes();
		Diabetes variance1 = new Diabetes();
		Diabetes variance0 = new Diabetes();

		String inputLine = value.toString();
		String[] inputArr = inputLine.split("\\s+");
		diabetesInput.setPregnancies(Double.parseDouble(inputArr[0]));
		diabetesInput.setGlucose(Double.parseDouble(inputArr[1]));
		diabetesInput.setBloodPressure(Double.parseDouble(inputArr[2]));
		diabetesInput.setSkinThickness(Double.parseDouble(inputArr[3]));
		diabetesInput.setInsulin(Double.parseDouble(inputArr[4]));
		diabetesInput.setBmi(Double.parseDouble(inputArr[5]));
		diabetesInput.setDiabetesPredigreeFunction(Double.parseDouble(inputArr[6]));
		diabetesInput.setAge(Double.parseDouble(inputArr[7]));
		
		
		map = reader.getMeanAndVarianceFromFileReader(OUT);

		list1 = map.get(1);
		mean1 = list1.get(0);
		variance1 = list1.get(1);
		list0 = map.get(0);
		mean0 = list0.get(0);
		variance0 = list0.get(1);
		
		
		npMap = reader.getFromProperties();
		double positive = Double.parseDouble(npMap.get("positive"));
		double negative = Double.parseDouble(npMap.get("negative"));
		double p1 = positive/(positive+negative);
		double p0 = negative/(positive+negative);
		
		// P(d|1)
		double pd1 = calculate.probabilityDensity(diabetesInput.getPregnancies(), mean1.getPregnancies(), variance1.getPregnancies())
				+ calculate.probabilityDensity(diabetesInput.getGlucose(), mean1.getGlucose(), variance1.getGlucose())
				+ calculate.probabilityDensity(diabetesInput.getBloodPressure(), mean1.getBloodPressure(), variance1.getBloodPressure())
				+ calculate.probabilityDensity(diabetesInput.getSkinThickness(), mean1.getSkinThickness(), variance1.getSkinThickness())
				+ calculate.probabilityDensity(diabetesInput.getInsulin(), mean1.getInsulin(), variance1.getInsulin())
				+ calculate.probabilityDensity(diabetesInput.getBmi(), mean1.getBmi(), variance1.getBmi())
				+ calculate.probabilityDensity(diabetesInput.getDiabetesPredigreeFunction(), mean1.getDiabetesPredigreeFunction(), variance1.getDiabetesPredigreeFunction())
				+ calculate.probabilityDensity(diabetesInput.getAge(), mean1.getAge(), variance1.getAge());

		//P(d|0)
		double pd0 = calculate.probabilityDensity(diabetesInput.getPregnancies(), mean0.getPregnancies(), variance0.getPregnancies())
				+ calculate.probabilityDensity(diabetesInput.getGlucose(), mean0.getGlucose(), variance0.getGlucose())
				+ calculate.probabilityDensity(diabetesInput.getBloodPressure(), mean0.getBloodPressure(), variance0.getBloodPressure())
				+ calculate.probabilityDensity(diabetesInput.getSkinThickness(), mean0.getSkinThickness(), variance0.getSkinThickness())
				+ calculate.probabilityDensity(diabetesInput.getInsulin(), mean0.getInsulin(), variance0.getInsulin())
				+ calculate.probabilityDensity(diabetesInput.getBmi(), mean0.getBmi(), variance0.getBmi())
				+ calculate.probabilityDensity(diabetesInput.getDiabetesPredigreeFunction(), mean0.getDiabetesPredigreeFunction(), variance0.getDiabetesPredigreeFunction())
				+ calculate.probabilityDensity(diabetesInput.getAge(), mean0.getAge(), variance0.getAge());

		// P(1|d)
		double p1d = (pd1 + Math.log(p1) )-( (pd1 + Math.log(p1) ) + (pd0 + Math.log(p0) ));

		// P(0|d)
		double p0d = (pd0 + Math.log(p0) )-( (pd1 + Math.log(p1)) + (pd0 + Math.log(p0) ));

		output.collect(new Text(index+""), new Text(p0d + " " + "0"));
		output.collect(new Text(index+""), new Text(p1d + " " + "1"));
		index++;
	}
}