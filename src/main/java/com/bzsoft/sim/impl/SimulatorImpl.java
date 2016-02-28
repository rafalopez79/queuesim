package com.bzsoft.sim.impl;

import java.util.Random;

import com.bzsoft.sim.Client;
import com.bzsoft.sim.Event;
import com.bzsoft.sim.EventManager;
import com.bzsoft.sim.Queue;
import com.bzsoft.sim.RandomVar;
import com.bzsoft.sim.Range;
import com.bzsoft.sim.ServerPool;
import com.bzsoft.sim.Simulator;

public class SimulatorImpl implements Simulator {

	private final RandomVar arrivalRVar;
	private final RandomVar serviceRVar;
	private final Queue<Client> queue;
	private final ServerPool sp;
	private final EventManager em;
	private double clock;

	public SimulatorImpl(
			final RandomVar arrivalRVar,
			final RandomVar serviceRVar,
			final Queue<Client> queue, final ServerPool sp) {
		this.arrivalRVar = arrivalRVar;
		this.serviceRVar = serviceRVar;
		this.queue = queue;
		this.sp = sp;
		this.clock = 0d;
		this.em = new EventManagerImpl();
	}

	@Override
	public void boot(){
		final Client c = createClient(clock, arrivalRVar, serviceRVar);
		final double arrival = c.getClientArrival();
		final Event e = new EventImpl(arrival, c, Event.ARRIVAL);
		em.addEvent(e);
	}

	private static Client createClient(final double clock, final RandomVar arrivalRVar,
			final RandomVar serviceRVar){
		final double arrival = arrivalRVar.nextSample() + clock;
		final double service = serviceRVar.nextSample();
		final Client client = new Client(arrival, service);
		return client;
	}

	@Override
	public Client step(){
		final Event e = em.nextEvent();
		if (e != null){
			final Client out = process(e);
			return out;
		}
		return null;
	}

	private Client process(final Event e){
		clock = e.getTime();
		final Client c = e.getClient();
		if (e.getType() == Event.ARRIVAL){
			//genero siguiente llegada
			final Client cn = createClient(clock, arrivalRVar, serviceRVar);
			em.addEvent(new EventImpl(cn.getClientArrival(), cn, Event.ARRIVAL));
			//miro cola
			c.setQueueSize(queue.size());
			if (sp.idle() > 0){
				//genero server enter
				em.addEvent(new EventImpl(clock, c, Event.SERVERENTER));
			}else{
				//a la cola
				queue.put(c);
			}
		}else if (e.getType() == Event.SERVERENTER){
			sp.enter();
			c.setServerEnter(clock);
			em.addEvent(new EventImpl(c.getServiceTime() + clock, c, Event.SERVERLEAVE));
		}else if (e.getType() == Event.SERVERLEAVE){
			sp.leave();
			c.setServerLeave(clock);
			// miro en la cola
			final Client qc = queue.take();
			if (qc != null){
				em.addEvent(new EventImpl(clock, qc, Event.SERVERENTER));
			}
			return c;
		}
		return null;
	}


	public static void main(final String[] args) {
		final long inittime = System.currentTimeMillis();
		final int m = 2;
		final Random rand = new Random(0);
		final double mu = 1;
		final double lambda = 0.5;
		System.out.println("Theory TotalTime(M/M/1): "+1 / (mu - lambda));
		final RandomVar arrivalRVar = new ExpRandomVar(rand, lambda);
		final RandomVar serviceRVar = new ExpRandomVar(rand, mu);
		final Queue<Client> queue = new QueueImpl<Client>();
		final ServerPool sp = new ServerPoolImpl(m);
		final Simulator s = new SimulatorImpl(arrivalRVar, serviceRVar, queue, sp);
		s.boot();
		final Average queueAverageSize = new Average();
		final Average systemTimeAverage = new Average();
		final Average waitingTimeAverage = new Average();
		final QuasiVariance systemTimeVar = new QuasiVariance();
		final QuasiVariance systemTimeMeanVar = new QuasiVariance();
		final Histogram systemTimeHistogram = new Histogram(new Range(0, 10, 0.5));
		final Histogram waitingTimeHistogram = new Histogram(new Range(0, 100, 0.1));
		final Histogram queueSizeHistogram = new Histogram(new Range(0, 20, 1));
		final int numClients = 10000000;
		int clientCount = 0;
		do{
			final Client c = s.step();
			if (c != null ){
				final double systemTime = c.getServerLeave() - c.getClientArrival();
				final double waitingTime = c.getServerEnter() - c.getClientArrival();
				//stats
				queueAverageSize.addSample(c.getQueueSize());
				systemTimeAverage.addSample(systemTime);
				waitingTimeAverage.addSample(waitingTime);
				systemTimeVar.addSample(systemTime);
				systemTimeHistogram.addSample(systemTime);
				waitingTimeHistogram.addSample(waitingTime);
				queueSizeHistogram.addSample(c.getQueueSize());
				systemTimeMeanVar.addSample(systemTimeAverage.getValue());
				clientCount++;
			}
		}while(clientCount < numClients);
		final long endtime = System.currentTimeMillis();
		System.out.println("CPU time: "+(endtime - inittime)/1000d+"s");
		System.out.println("avgTotalTime="+systemTimeAverage.getValue());
		System.out.println("avgTotalTime AVG STDev="+systemTimeMeanVar.getValue());
		System.out.println("avgTotalTime STDev="+Math.sqrt(systemTimeVar.getValue()));
		System.out.println("avgQSize="+queueAverageSize.getValue());
		System.out.println("avg Theoretical QSize="+computeWMean(lambda, mu, m));
		System.out.println("System Time 0.9-Quantile="+systemTimeHistogram.getQuantile(0.9));
		//System.out.println("System Time "+systemTimeHistogram);
		//System.out.println("Waitin Time "+waitingTimeHistogram);
		//System.out.println("QueueSize "+queueSizeHistogram);
	}

	private static final double computeWMean(final double lambda, final double mu, final int m){
		final double p0 = computeP0(lambda, mu, m);
		final double I = lambda / mu;
		final double rho = I / m;
		return Math.pow(I, m) * rho * p0 / (factorial(m) * ( 1 - rho) * (1 - rho));
	}

	private static final double computeP0(final double lambda, final double mu, final int m){
		double sum = 0;
		final double I = lambda / mu;
		final double rho = I / m;
		for(int i = 0; i < m; i++){
			sum += Math.pow(I, i)/factorial(i);
		}
		sum += Math.pow(I, m) / (factorial(m) * ( 1 - rho));
		return 1 / sum;
	}
	private static final int factorial(final int i){
		if ( i == 0){
			return 1;
		}
		return i * factorial(i-1);
	}
}
