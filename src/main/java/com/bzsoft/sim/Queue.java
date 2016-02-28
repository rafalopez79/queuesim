package com.bzsoft.sim;

public interface Queue<T> {

	public int size();

	public void put(T t);

	public T take();

}
