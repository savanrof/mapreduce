package com.bd.service;

public class CalculateService {
	private final double pi = Math.PI;
	public double probability(int a, int b) {
		return a/b;
	}
	private double left(double variance) {
		double result = Math.sqrt(2*pi*variance);
		return Math.log(1/result);
	}
	private double right(double newValue, double mean, double variance) {
		double result = (Math.pow((newValue - mean),2))/variance;
		return Math.exp(-result/2);
	}
	public double probabilityDensity(double newValue, double mean, double variance) {
		return left(variance)+Math.log(right(newValue,mean,variance));
	}
	
}
