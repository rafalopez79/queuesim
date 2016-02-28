package com.bzsoft.sim.impl;

import com.bzsoft.sim.RandomVar;

public class DeterministicRandomVar implements RandomVar {

	private final double delta;

	public DeterministicRandomVar(final  double delta) {
		this.delta = delta;
	}

	@Override
	public double nextSample() {
		return delta;
	}

}
