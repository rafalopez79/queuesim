package com.bzsoft.sim;

public class Client {

	private final double clientArrival;
	private final double serviceTime;

	private double serverEnter;
	private double serverLeave;
	private int queueSize;

	public Client(final double clientArrival, final double serviceTime){
		this.clientArrival = clientArrival;
		this.serviceTime = serviceTime;
	}

	public double getClientArrival(){
		return clientArrival;
	}

	public double getServiceTime(){
		return serviceTime;
	}

	public void setServerEnter(final double serverEnter) {
		this.serverEnter = serverEnter;
	}

	public double getServerEnter(){
		return serverEnter;
	}

	public void setServerLeave(final double serverLeave) {
		this.serverLeave = serverLeave;
	}

	public double getServerLeave(){
		return serverLeave;
	}

	public void setQueueSize(final int queueSize) {
		this.queueSize = queueSize;
	}

	public int getQueueSize() {
		return queueSize;
	}

	@Override
	public String toString() {
		return "Client [clientArrival=" + clientArrival + ", serviceTime=" + serviceTime + ", serverEnter=" + serverEnter + ", serverLeave="
				+ serverLeave + "]";
	}



}
