package com.bzsoft.sim.impl;

import java.util.Random;

import com.bzsoft.sim.RandomVar;

public class DisplacedExpRandomVar implements RandomVar {

	private final Random rand;
	private final double alpha;
	private final double delta;

	public DisplacedExpRandomVar(final Random rand, final double alpha, final double delta) {
		this.rand = rand;
		this.alpha = alpha;
		this.delta = delta;
	}

	@Override
	public double nextSample() {
		return delta - Math.log(rand.nextDouble()) / alpha;
	}

}
