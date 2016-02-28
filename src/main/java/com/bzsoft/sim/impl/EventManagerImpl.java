package com.bzsoft.sim.impl;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

import com.bzsoft.sim.Event;
import com.bzsoft.sim.EventManager;

public class EventManagerImpl implements EventManager {

	private final Queue<Event> eventQueue;

	public EventManagerImpl() {
		final Comparator<Event> comparator = new Comparator<Event>(){
			@Override
			public int compare(final Event o1, final Event o2) {
				final double t1 = o1.getTime();
				final double t2 = o2.getTime();
				return t1 < t2 ? -1 : t1 > t2 ? 1 : 0;
			}
		};
		eventQueue = new PriorityQueue<Event>(1024, comparator);
	}

	@Override
	public Event nextEvent() {
		return eventQueue.poll();
	}

	@Override
	public void addEvent(final Event e) {
		eventQueue.offer(e);
	}

}
