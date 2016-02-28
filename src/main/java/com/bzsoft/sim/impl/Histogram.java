package com.bzsoft.sim.impl;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import com.bzsoft.sim.Range;
import com.bzsoft.sim.Statistic;

public class Histogram implements Statistic {

	private final Range range;
	private final int[] buckets;
	private int total;

	public Histogram(final Range range) {
		this.range = range;
		final int count = (int)Math.ceil((range.getEnd() - range.getIni()) / range.getStep());
		buckets = new int[count];
		total = 0;
	}

	@Override
	public void addSample(final double s) {
		if (s>= range.getIni() && s <= range.getEnd()){
			final int bucket = Math.max((int)Math.ceil((s - range.getIni()) / range.getStep()) -1, 0);
			buckets[bucket]++;
			total ++;
		}
	}


	public double getQuantile(final double x){
		final double quantity = x * total;
		int count = 0;
		int index = 0;
		for(int i = 0; i < buckets.length; i++){
			count += buckets[i];
			if (count >= quantity){
				index = i;
				break;
			}
		}
		return range.getIni() + index * range.getStep();
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Histogram:\n");
		final DecimalFormat df = new DecimalFormat("#####.##");
		df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.ENGLISH));
		for(int i = 0; i < buckets.length; i++){
			final double r = range.getIni() + range.getStep() * i;
			final int b = buckets[i];
			sb.append(df.format(r)).append('\t').append(b).append('\n');
		}
		return sb.toString();
	}
}
