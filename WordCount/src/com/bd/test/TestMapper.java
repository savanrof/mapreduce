package com.bd.test;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
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
		List<Diabetes> meanAndVariance = new ArrayList<Diabetes>();
		CalculateService calculate = new CalculateService();
		DiabetesReaderService reader = new DiabetesReaderService();
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
		
		
		meanAndVariance = reader.getMeanAndVarianceFromFileReader(OUT);
		mean0 = meanAndVariance.get(0);
		variance0 = meanAndVariance.get(1);
		mean1 = meanAndVariance.get(2);
		variance1 = meanAndVariance.get(3);
				
		
		npMap = reader.getFromProperties();
		double positive = Double.parseDouble(npMap.get("positive"));
		double negative = Double.parseDouble(npMap.get("negative"));
		double p1 = positive/(positive+negative);
		double p0 = negative/(positive+negative);
		
		// P(d|1)
		BigDecimal pd1 = calculate.probabilityDensity(diabetesInput.getPregnancies(), mean1.getPregnancies(), variance1.getPregnancies())
				.multiply(calculate.probabilityDensity(diabetesInput.getGlucose(), mean1.getGlucose(), variance1.getGlucose()))
				.multiply(calculate.probabilityDensity(diabetesInput.getGlucose(), mean1.getGlucose(), variance1.getGlucose()))
				.multiply(calculate.probabilityDensity(diabetesInput.getBloodPressure(), mean1.getBloodPressure(), variance1.getBloodPressure()))
				.multiply(calculate.probabilityDensity(diabetesInput.getSkinThickness(), mean1.getSkinThickness(), variance1.getSkinThickness()))
				.multiply(calculate.probabilityDensity(diabetesInput.getInsulin(), mean1.getInsulin(), variance1.getInsulin()))
				.multiply(calculate.probabilityDensity(diabetesInput.getBmi(), mean1.getBmi(), variance1.getBmi()))
				.multiply(calculate.probabilityDensity(diabetesInput.getDiabetesPredigreeFunction(), mean1.getDiabetesPredigreeFunction(), variance1.getDiabetesPredigreeFunction())) 
				.multiply(calculate.probabilityDensity(diabetesInput.getAge(), mean1.getAge(), variance1.getAge()));
		
		//P(d|0)
		BigDecimal pd0 = calculate.probabilityDensity(diabetesInput.getPregnancies(), mean0.getPregnancies(), variance0.getPregnancies())
				.multiply(calculate.probabilityDensity(diabetesInput.getGlucose(), mean0.getGlucose(), variance0.getGlucose()))
				.multiply(calculate.probabilityDensity(diabetesInput.getGlucose(), mean0.getGlucose(), variance0.getGlucose()))
				.multiply(calculate.probabilityDensity(diabetesInput.getBloodPressure(), mean0.getBloodPressure(), variance0.getBloodPressure()))
				.multiply(calculate.probabilityDensity(diabetesInput.getSkinThickness(), mean0.getSkinThickness(), variance0.getSkinThickness()))
				.multiply(calculate.probabilityDensity(diabetesInput.getInsulin(), mean0.getInsulin(), variance0.getInsulin()))
				.multiply(calculate.probabilityDensity(diabetesInput.getBmi(), mean0.getBmi(), variance0.getBmi()))
				.multiply(calculate.probabilityDensity(diabetesInput.getDiabetesPredigreeFunction(), mean0.getDiabetesPredigreeFunction(), variance0.getDiabetesPredigreeFunction())) 
				.multiply(calculate.probabilityDensity(diabetesInput.getAge(), mean0.getAge(), variance0.getAge()));

		BigDecimal mau = pd1.multiply(new BigDecimal(p1)).add(pd0.multiply(new BigDecimal(p0)));

		// P(1|d)
		BigDecimal p1d = calculate.log10(pd1.multiply(new BigDecimal(p1))).subtract(calculate.log10(mau));

		// P(0|d)
		BigDecimal p0d = calculate.log10(pd0.multiply(new BigDecimal(p0))).subtract(calculate.log10(mau));

		output.collect(new Text(index+""), new Text(p0d + " " + "0"));
		output.collect(new Text(index+""), new Text(p1d + " " + "1"));
		index++;
	}

}