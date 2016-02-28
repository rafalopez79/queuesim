package com.bzsoft.sim;

public interface Event {

	public static final int ARRIVAL = 1;
	public static final int SERVERENTER = 2;
	public static final int SERVERLEAVE = 3;

	public double getTime();

	public Client getClient();

	public int getType();

}
