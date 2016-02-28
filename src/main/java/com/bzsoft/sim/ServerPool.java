package com.bzsoft.sim;

public interface ServerPool {

	public int size();

	public int idle();

	public void enter();

	public void leave();

}
