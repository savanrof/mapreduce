package com.bd.service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class CalculateService {
	private final double pi = Math.PI;
	public double probability(int a, int b) {
		return a/b;
	}
	private BigDecimal left(double variance) {
		double result = Math.sqrt(2*pi*variance);
		return new BigDecimal(1/result);
	}
	private BigDecimal right(double newValue, double mean, double variance) {
		double result = (Math.pow((newValue - mean),2))/variance;
		return new BigDecimal(Math.exp(-result/2));
	}
	public BigDecimal probabilityDensity(double newValue, double mean, double variance) {
		return left(variance).multiply(right(newValue,mean,variance));
	}
    public BigDecimal log10(BigDecimal b) {
        final int NUM_OF_DIGITS = 18 + 2;

        MathContext mc = new MathContext(NUM_OF_DIGITS, RoundingMode.HALF_EVEN);
        if (b.signum() <= 0) {
            throw new ArithmeticException("log of a negative number! (or zero)");
        } else if (b.compareTo(BigDecimal.ONE) == 0) {
            return BigDecimal.ZERO;
        } else if (b.compareTo(BigDecimal.ONE) < 0) {
            return (log10((BigDecimal.ONE).divide(b, mc))).negate();
        }

        StringBuilder sb = new StringBuilder();
        int leftDigits = b.precision() - b.scale();
        sb.append(leftDigits - 1).append(".");

        int n = 0;
        while (n < NUM_OF_DIGITS) {
            b = (b.movePointLeft(leftDigits - 1)).pow(10, mc);
            leftDigits = b.precision() - b.scale();
            sb.append(leftDigits - 1);
            n++;
        }
        BigDecimal ans = new BigDecimal(sb.toString());
        ans = ans.round(new MathContext(ans.precision() - ans.scale() + 18, RoundingMode.HALF_EVEN));
        return ans;
    }
}
