package com.bd.service;

import java.util.List;

import com.bd.model.Diabetes;

public class VarianceService {
	public double getVariancePregnancies(List<Diabetes> list, Diabetes diabetes) {
		double mean = diabetes.getPregnancies();
		return list.stream()
			.mapToDouble(d -> Math.pow(d.getPregnancies() - mean, 2))
			.sum();
	}
	public double getVarianceGlucose(List<Diabetes> list, Diabetes diabetes) {
		double mean = diabetes.getGlucose();
		return list.stream()
				.mapToDouble(d -> Math.pow(d.getGlucose() - mean, 2))
				.sum();
	}
	public double getVarianceBloodPressure(List<Diabetes> list, Diabetes diabetes) {
		double mean = diabetes.getBloodPressure();
		return list.stream()
				.mapToDouble(d -> Math.pow(d.getBloodPressure() - mean, 2))
				.sum();
	}
	public double getVarianceSkinThickness(List<Diabetes> list, Diabetes diabetes) {
		double mean = diabetes.getSkinThickness();
		return list.stream()
				.mapToDouble(d -> Math.pow(d.getSkinThickness() - mean, 2))
				.sum();
	}
	public double getVarianceInsulin(List<Diabetes> list, Diabetes diabetes) {
		double mean = diabetes.getInsulin();
		return list.stream()
				.mapToDouble(d -> Math.pow(d.getInsulin() - mean, 2))
				.sum();
	}
	public double getVarianceBmi(List<Diabetes> list, Diabetes diabetes) {
		double mean = diabetes.getBmi();
		return list.stream()
				.mapToDouble(d -> Math.pow(d.getBmi() - mean, 2))
				.sum();
	}
	public double getVarianceDiabetesPredigreeFunction(List<Diabetes> list, Diabetes diabetes) {
		double mean = diabetes.getDiabetesPredigreeFunction();
		return list.stream()
				.mapToDouble(d -> Math.pow(d.getDiabetesPredigreeFunction() - mean, 2))
				.sum();
	}
	public double getVarianceAge(List<Diabetes> list, Diabetes diabetes) {
		double mean = diabetes.getAge();
		return list.stream()
				.mapToDouble(d -> Math.pow(d.getAge() - mean, 2))
				.sum();
	}
	public Diabetes varianceDiabetes(List<Diabetes> list, Diabetes diabetes, int n) {
		Diabetes result = new Diabetes();
		result.setPregnancies(getVariancePregnancies(list,diabetes)/n);
		result.setGlucose(getVarianceGlucose(list,diabetes)/n);
		result.setBloodPressure(getVarianceBloodPressure(list,diabetes)/n);
		result.setSkinThickness(getVarianceSkinThickness(list,diabetes)/n);
		result.setInsulin(getVarianceInsulin(list,diabetes)/n);
		result.setBmi(getVarianceBmi(list,diabetes)/n);
		result.setDiabetesPredigreeFunction(getVarianceDiabetesPredigreeFunction(list,diabetes)/n);
		result.setAge(getVarianceAge(list,diabetes)/n);
		return result;
	}
}
