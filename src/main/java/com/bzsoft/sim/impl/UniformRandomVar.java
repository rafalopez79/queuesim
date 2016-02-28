package com.bzsoft.sim.impl;

import java.util.Random;

import com.bzsoft.sim.RandomVar;

public class UniformRandomVar implements RandomVar {

	private final Random rand;
	private final double a;
	private final double b;

	public UniformRandomVar(final Random rand, final double a, final double b) {
		this.rand = rand;
		this.a = a;
		this.b = b;
	}

	@Override
	public double nextSample() {
		return a + (b-a)*rand.nextDouble();
	}

}
