package com.bd.service;

import java.util.List;

import com.bd.model.Diabetes;

public class MeanService {
	public double getAveragePregnancies(List<Diabetes> diabetes) {
		double rs = diabetes.stream()
				.mapToDouble(Diabetes::getPregnancies)
				.sum();
		return rs;
	}
	public double getAverageGlucose(List<Diabetes> diabetes) {
		double rs =diabetes.stream()
				.mapToDouble(Diabetes::getGlucose)
				.sum();
		return rs;
	}
	public double getAverageBloodPressure(List<Diabetes> diabetes) {
		double rs = diabetes.stream()
				.mapToDouble(Diabetes::getBloodPressure)
				.sum();
		return rs;
	}
	public double getAverageSkinThickness(List<Diabetes> diabetes) {
		double rs = diabetes.stream()
				.mapToDouble(Diabetes::getSkinThickness)
				.sum();
		return rs;
	}
	public double getAverageInsulin(List<Diabetes> diabetes) {
		double rs = diabetes.stream()
				.mapToDouble(Diabetes::getInsulin)
				.sum();
		return rs;
	}
	public double getAverageBmi(List<Diabetes> diabetes) {
		double rs = diabetes.stream()
				.mapToDouble(Diabetes::getBmi)
				.sum();
		return rs;
	}
	public double getAverageDiabetesPredigreeFunction(List<Diabetes> diabetes) {
		double rs = diabetes.stream()
				.mapToDouble(Diabetes::getDiabetesPredigreeFunction)
				.sum();
		return rs;
	}
	public double getAverageAge(List<Diabetes> diabetes) {
		double rs = diabetes.stream()
				.mapToDouble(Diabetes::getAge)
				.sum();
		return rs;
	}
	public Diabetes meanDiabetes(List<Diabetes> diabetes,int n) {
		Diabetes result = new Diabetes();
		result.setPregnancies(getAveragePregnancies(diabetes)/n);
		result.setGlucose(getAverageGlucose(diabetes)/n);
		result.setBloodPressure(getAverageBloodPressure(diabetes)/n);
		result.setSkinThickness(getAverageSkinThickness(diabetes)/n);
		result.setInsulin(getAverageInsulin(diabetes)/n);
		result.setBmi(getAverageBmi(diabetes)/n);
		result.setDiabetesPredigreeFunction(getAverageDiabetesPredigreeFunction(diabetes)/n);
		result.setAge(getAverageAge(diabetes)/n);
		return result;
	}
}
