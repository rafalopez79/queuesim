package com.bzsoft.sim.impl;

import com.bzsoft.sim.Client;
import com.bzsoft.sim.Event;

public class EventImpl implements Event {

	private final double time;
	private final Client client;
	private final int type;

	public EventImpl(final double time, final Client client, final int type) {
		this.time = time;
		this.client = client;
		this.type = type;
	}



	@Override
	public double getTime() {
		return time;
	}

	@Override
	public Client getClient() {
		return client;
	}

	@Override
	public int getType() {
		return type;
	}



	@Override
	public String toString() {
		return "EventImpl [time=" + time + ", client=" + client + ", type=" + type + "]";
	}

}
