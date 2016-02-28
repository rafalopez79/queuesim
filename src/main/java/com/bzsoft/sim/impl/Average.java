package com.bzsoft.sim.impl;

import com.bzsoft.sim.Statistic;

public class Average implements Statistic {

	private int count;
	private double sum;

	public Average() {
		sum = 0;
		count = 0;
	}

	@Override
	public void addSample(final double s) {
		count ++;
		sum += s;
	}

	public double getValue() {
		return sum / count;
	}

}
