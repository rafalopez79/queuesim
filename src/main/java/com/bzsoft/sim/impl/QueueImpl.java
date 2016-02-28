package com.bzsoft.sim.impl;

import java.util.LinkedList;
import java.util.List;

import com.bzsoft.sim.Queue;

public class QueueImpl<T> implements Queue<T> {

	private final List<T> list;

	public QueueImpl() {
		list = new LinkedList<T>();
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public void put(final T t) {
		list.add(t);
	}

	@Override
	public T take() {
		if (list.isEmpty()){
			return null;
		}
		return list.remove(0);
	}

}
