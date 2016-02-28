package com.bzsoft.sim.impl;

import com.bzsoft.sim.Statistic;

public class QuasiVariance implements Statistic {

	// sum(x*x) + sum(mu*mu) - 2 sum(x*mu)
	//sum (x*x) + n * mu*mu - 2 mu sum(x)
	private int count;
	private double sumSquaredX;
	private double sumX;


	public QuasiVariance() {
		count = 0;
		sumX = 0;
		sumSquaredX = 0;
	}

	@Override
	public void addSample(final double s) {
		count++;
		sumX += s;
		sumSquaredX += s*s;
	}

	public double getValue() {
		return (sumSquaredX + sumX*sumX/count - 2* sumX *sumX/count )/(count-1);
	}

}
