package com.bzsoft.sim.impl;

import java.util.Random;

import com.bzsoft.sim.RandomVar;

public class ExpRandomVar implements RandomVar {

	private final Random rand;
	private final double alpha;

	public ExpRandomVar(final Random rand, final double alpha) {
		this.rand = rand;
		this.alpha = alpha;
	}

	@Override
	public double nextSample() {
		return - Math.log(rand.nextDouble()) / alpha;
	}

}
