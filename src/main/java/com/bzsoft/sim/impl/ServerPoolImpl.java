package com.bzsoft.sim.impl;

import com.bzsoft.sim.ServerPool;

public class ServerPoolImpl implements ServerPool {

	private final int size;
	private  int idle;

	public ServerPoolImpl(final int size) {
		this.size = size;
		this.idle = size;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public int idle() {
		return idle;
	}

	@Override
	public void enter() {
		idle--;
	}

	@Override
	public void leave() {
		idle++;
	}

}
