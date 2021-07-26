package com.bd.model;

public class Diabetes {
	private double pregnancies;
	private double glucose;
	private double bloodPressure;
	private double skinThickness;
	private double insulin;
	private double bmi;
	private double diabetesPredigreeFunction;
	private double age;
	public double getPregnancies() {
		return pregnancies;
	}
	public void setPregnancies(double pregnancies) {
		this.pregnancies = pregnancies;
	}
	public double getGlucose() {
		return glucose;
	}
	public void setGlucose(double glucose) {
		this.glucose = glucose;
	}
	public double getBloodPressure() {
		return bloodPressure;
	}
	public void setBloodPressure(double bloodPressure) {
		this.bloodPressure = bloodPressure;
	}
	public double getSkinThickness() {
		return skinThickness;
	}
	public void setSkinThickness(double skinThickness) {
		this.skinThickness = skinThickness;
	}
	public double getInsulin() {
		return insulin;
	}
	public void setInsulin(double insulin) {
		this.insulin = insulin;
	}
	public double getBmi() {
		return bmi;
	}
	public void setBmi(double bmi) {
		this.bmi = bmi;
	}
	public double getDiabetesPredigreeFunction() {
		return diabetesPredigreeFunction;
	}
	public void setDiabetesPredigreeFunction(double diabetesPredigreeFunction) {
		this.diabetesPredigreeFunction = diabetesPredigreeFunction;
	}
	public double getAge() {
		return age;
	}
	public void setAge(double age) {
		this.age = age;
	}
	public Diabetes() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Diabetes(double pregnancies, double glucose, double bloodPressure, double skinThickness, double insulin,
			double bmi, double diabetesPredigreeFunction, int age) {
		super();
		this.pregnancies = pregnancies;
		this.glucose = glucose;
		this.bloodPressure = bloodPressure;
		this.skinThickness = skinThickness;
		this.insulin = insulin;
		this.bmi = bmi;
		this.diabetesPredigreeFunction = diabetesPredigreeFunction;
		this.age = age;
	}
	@Override
	public String toString() {
		return "Diabetes [pregnancies=" + pregnancies + ", glucose=" + glucose + ", bloodPressure=" + bloodPressure
				+ ", skinThickness=" + skinThickness + ", insulin=" + insulin + ", bmi=" + bmi
				+ ", diabetesPredigreeFunction=" + diabetesPredigreeFunction + ", age=" + age + "]";
	}
	
}
