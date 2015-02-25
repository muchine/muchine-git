package com.lgu.abc.core.common.infra.sequence.tester;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lgu.abc.core.common.infra.sequence.Sequencer;
import com.lgu.abc.core.common.vo.time.Time;

public class SequencerMultiThreadTester {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private static final int THREAD_COUNT = 5;
	
	private static final int MAX_ITERATION_COUNT = 1000;
	
	private final List<SequencerTester> testers = new ArrayList<SequencerTester>();
	
	private final String sequenceId;
	
	public SequencerMultiThreadTester(Sequencer sequencer, String sequenceId) {
		this.sequenceId = sequenceId;
		initialize(sequencer, sequenceId);
	}
		
	public void run() {
		for (SequencerTester tester : testers) {
			new Thread(tester).start();
		}
		
		waitUntilAllThreadsCompleted();
		validate();
		
//		exportResult();
	}
	
	private void initialize(Sequencer sequencer, String sequenceId) {
		for (int i = 0; i < THREAD_COUNT; i++) {
			testers.add(new SequencerTester(sequencer, sequenceId, MAX_ITERATION_COUNT));
		}
	}
	
	private void waitUntilAllThreadsCompleted() {
		while(true) {
			try {					
				Thread.sleep(1000);
				if (isAllThreadCompleted()) break;
			} catch (InterruptedException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}
	
	private void validate() {
		for (int i = 0; i < testers.size(); i++) {
			SequencerTester tester = testers.get(i);
			logger.debug("tester [" + i + "] " + " min: " + tester.min() + ", max: " + tester.max() + ", elapsed: " + tester.getElapsed());
		}
		
		validateDuplication();
	}
	
	private void validateDuplication() {
		List<Long> sequences = merge();
		
		Set<Long> copied = new HashSet<Long>();
		for (Long sequence : sequences) {
			if (!copied.add(sequence))
				throw new IllegalStateException("duplicate sequence");
		}
	}
	
	private boolean isAllThreadCompleted() {
		for (SequencerTester tester : testers) {
			if (!tester.isCompleted()) return false;
		}
		
		return true;
	}
	
	@SuppressWarnings("unused")
	private void exportResult() {
		List<Long> sequences = merge();
		
		try {
			String stringified = StringUtils.join(sequences, ",");
			String filename = "sequences-" + sequenceId + "-" + new Time().toString() + ".txt";
			
			FileUtils.write(new File(filename), stringified);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private List<Long> merge() {
		List<Long> sequences = new ArrayList<Long>();
		for (SequencerTester tester : testers) {
			sequences.addAll(tester.getResults());
		}
		
		return sequences;
	}
	
}
