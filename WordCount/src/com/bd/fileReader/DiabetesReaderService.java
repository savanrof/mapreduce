package com.bd.fileReader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import com.bd.model.Diabetes;

public class DiabetesReaderService {
	private String dir = "D:\\Zoom\\PTDLL\\Output\\";
	private String FILE_NAME = "part-00000";

	public List<Diabetes> getMeanAndVarianceFromFileReader(String directoryOut) throws IOException {
		List<Diabetes> listDiabetes = new ArrayList<Diabetes>();
		Map<Integer, List<Diabetes>> map = new HashMap<>();
		
		Path fileTest = new Path(dir + directoryOut + "\\" + FILE_NAME);
		FileSystem fs = FileSystem.get(new Configuration());
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fs.open(fileTest)));
		String line = bufferedReader.readLine();
		while (line != null) {
			
			String[] lineVal = line.split("\\s+");
			Diabetes mean = new Diabetes();
			mean.setPregnancies(Double.parseDouble(lineVal[1]));
			mean.setGlucose(Double.parseDouble(lineVal[3]));
			mean.setBloodPressure(Double.parseDouble(lineVal[5]));
			mean.setSkinThickness(Double.parseDouble(lineVal[7]));
			mean.setInsulin(Double.parseDouble(lineVal[9]));
			mean.setBmi(Double.parseDouble(lineVal[11]));
			mean.setDiabetesPredigreeFunction(Double.parseDouble(lineVal[13]));
			mean.setAge(Double.parseDouble(lineVal[15]));
			listDiabetes.add(mean);

			Diabetes variance = new Diabetes();
			variance.setPregnancies(Double.parseDouble(lineVal[2]));
			variance.setGlucose(Double.parseDouble(lineVal[4]));
			variance.setBloodPressure(Double.parseDouble(lineVal[6]));
			variance.setSkinThickness(Double.parseDouble(lineVal[8]));
			variance.setInsulin(Double.parseDouble(lineVal[10]));
			variance.setBmi(Double.parseDouble(lineVal[12]));
			variance.setDiabetesPredigreeFunction(Double.parseDouble(lineVal[14]));
			variance.setAge(Double.parseDouble(lineVal[16]));
			listDiabetes.add(variance);
			
			line = bufferedReader.readLine();
		}
		bufferedReader.close();
		return listDiabetes;
	}

	public void saveToProperties(String key, int n) throws IOException {
		OutputStream outputStream = new FileOutputStream("resources/application.properties");
		Properties properties = System.getProperties();
		if (1 == Integer.parseInt(key)) {
			properties.setProperty("Negative", n +"");
		} else {
			properties.setProperty("Positive", n +"");
		}
		properties.store(outputStream, null);
		outputStream.close();
	}

	public Map<String, String> getFromProperties() throws IOException {
		InputStream inputStream = new FileInputStream("resources/application.properties");
		Properties properties = System.getProperties();
		properties.load(inputStream);
		Map<String, String> map = new HashMap<>();
		map.put("negative", System.getProperty("Negative"));
		map.put("positive", System.getProperty("Positive"));
		return map;
	}
}
