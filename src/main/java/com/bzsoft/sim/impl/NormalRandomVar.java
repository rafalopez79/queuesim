package com.bzsoft.sim.impl;

import java.util.Random;

import com.bzsoft.sim.RandomVar;

public class NormalRandomVar implements RandomVar {

	private final Random rand;
	private final double mu;
	private final double sigma;

	public NormalRandomVar(final Random rand, final double mu, final double sigma) {
		this.rand = rand;
		this.mu = mu;
		this.sigma = sigma;
	}

	@Override
	public double nextSample() {
		return rand.nextGaussian() * sigma + mu;
	}

}
