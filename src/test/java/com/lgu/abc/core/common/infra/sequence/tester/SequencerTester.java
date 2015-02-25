package com.lgu.abc.core.common.infra.sequence.tester;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Getter;

import org.apache.commons.lang.StringUtils;

import com.lgu.abc.core.common.infra.sequence.Sequencer;

public class SequencerTester implements Runnable {

	private final Sequencer sequencer;
	
	private final String sequencerId;
	
	private final int maxIterationCount;
	
	@Getter
	private long elapsed;
	
	@Getter
	private boolean completed = false;
	
	@Getter
	private List<Long> results = new ArrayList<Long>();
	
	SequencerTester(Sequencer sequencer, String sequencerId, int maxIterationCount) {
		this.sequencer = sequencer;
		this.sequencerId = sequencerId;
		this.maxIterationCount = maxIterationCount;
	}

	@Override
	public void run() {
		long start = System.currentTimeMillis();
		for (int i = 0; i < maxIterationCount; i++) {
			long sequence = sequencer.getNextSequence(sequencerId);
			results.add(sequence);
		}
		
		completed = true;
		this.elapsed = System.currentTimeMillis() - start;
	}
	
	public void validate() {
		Collections.sort(results);
		for (int i = 0, n = results.size(); i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				if (results.get(i).equals(results.get(j))) throw new IllegalStateException("duplicate sequence");
			}
		}
	}
	
	public void validate(List<Long> results) {
		for (Long sequence : results) {
			if (hasSequence(sequence)) throw new IllegalStateException("duplicate sequence");
		}
	}
	
	public long min() {
		Collections.sort(results);
		return results.get(0);
	}
	
	public long max() {
		Collections.sort(results);
		return results.get(results.size() - 1);
	}
	
	public String stringify() {
		return StringUtils.join(results, ",");
	}
	
	private boolean hasSequence(Long sequence) {
		for (Long generated : results) {
			if (generated.equals(sequence)) return true;
		}
		
		return false;
	}
	
}
