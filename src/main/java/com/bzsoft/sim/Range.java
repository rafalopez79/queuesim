package com.bzsoft.sim;

public class Range {

	private final double ini;
	private final double end;
	private final double step;

	public Range(final double ini, final double end, final double step) {
		this.ini = ini;
		this.end = end;
		this.step = step;
	}
	public double getIni() {
		return ini;
	}
	public double getEnd() {
		return end;
	}
	public double getStep() {
		return step;
	}
	@Override
	public String toString() {
		return "Range [ini=" + ini + ", end=" + end + ", step=" + step + "]";
	}


}
