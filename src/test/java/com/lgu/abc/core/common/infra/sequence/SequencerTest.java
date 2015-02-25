package com.lgu.abc.core.common.infra.sequence;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgu.abc.core.common.infra.sequence.tester.SequencerMultiThreadTester;
import com.lgu.abc.test.common.base.BaseTest;

public class SequencerTest extends BaseTest {
	
	private static final String SEQUENCE_ID = "james.dbo.abc_fetch";
	
	@Autowired
	private Sequencer sequencer;
	
	@Test
	public void testGetNextSequence() {
		// Given
		long sequence = sequencer.getNextSequence(SEQUENCE_ID);
		
		// When
		long generated = sequencer.getNextSequence(SEQUENCE_ID);
		
		// Then
		assertThat(generated, is(sequence + 1));
	}
	
	@Test
	public void testGetNextSequenceWithMultiThreadEnvironment() {
		new SequencerMultiThreadTester(sequencer, SEQUENCE_ID).run();
	}
	
}
