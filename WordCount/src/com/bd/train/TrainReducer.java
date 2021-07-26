package com.bd.train;
import java.io.IOException;
import java.util.*;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.*;

import com.bd.fileReader.DiabetesReaderService;
import com.bd.model.Diabetes;
import com.bd.service.MeanService;
import com.bd.service.VarianceService;

public class TrainReducer extends MapReduceBase implements Reducer<Text, Text, Text, Text> {
	
	public void reduce(Text t_key, Iterator<Text> values, OutputCollector<Text,Text> output, Reporter reporter) throws IOException {
		Diabetes mean = new Diabetes();
		Diabetes variance = new Diabetes();
		MeanService meanService = new MeanService();
		VarianceService varianceService = new VarianceService();
		DiabetesReaderService reader = new DiabetesReaderService();
		List<Diabetes> listDiabetes = new ArrayList<Diabetes>();
		Text key = t_key;
		String valuesOut = "";
		String[] valuesSplit;
		int N = 0;
		while (values.hasNext()) {
			Text value = (Text) values.next();
			valuesSplit = value.toString().split("\\s++");
			Diabetes diabetes = new Diabetes();
			diabetes.setPregnancies(Double.parseDouble(valuesSplit[0]));
			diabetes.setGlucose(Double.parseDouble(valuesSplit[1]));
			diabetes.setBloodPressure(Double.parseDouble(valuesSplit[2]));
			diabetes.setSkinThickness(Double.parseDouble(valuesSplit[3]));
			diabetes.setInsulin(Double.parseDouble(valuesSplit[4]));
			diabetes.setBmi(Double.parseDouble(valuesSplit[5]));
			diabetes.setDiabetesPredigreeFunction(Double.parseDouble(valuesSplit[6]));
			diabetes.setAge(Double.parseDouble(valuesSplit[7]));
			listDiabetes.add(diabetes);
			
			N++;
		}
		
		reader.saveToProperties(key.toString().trim(), N);
		
		mean = meanService.meanDiabetes(listDiabetes,N);
		variance = varianceService.varianceDiabetes(listDiabetes, mean, N);
		valuesOut = mean.getPregnancies() + " " + variance.getPregnancies() + " "
						+ mean.getGlucose() + " " + variance.getGlucose() + " "
						+ mean.getBloodPressure() + " " + variance.getBloodPressure() + " "
						+ mean.getSkinThickness() + " " + variance.getSkinThickness() + " "
						+ mean.getInsulin() + " " + variance.getInsulin() + " "
						+ mean.getBmi() + " " + variance.getBmi() + " "
						+ mean.getDiabetesPredigreeFunction() + " " + variance.getDiabetesPredigreeFunction() + " "
						+ mean.getAge() + " " + variance.getAge() ;
		
		output.collect(key, new Text(valuesOut+" "+N));
	}
}
