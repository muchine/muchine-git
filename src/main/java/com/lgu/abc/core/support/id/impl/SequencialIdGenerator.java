package com.lgu.abc.core.support.id.impl;

import org.apache.commons.lang.Validate;

import com.lgu.abc.core.common.infra.sequence.Sequencer;
import com.lgu.abc.core.support.id.ChainableIdGenerator;
import com.lgu.abc.core.support.id.annotation.Sequence;

/**
 * Get current and next sequences. Utilize Sequence class annotation to get sequence information. 
 * 
 * @author sejoon
 *
 */
public class SequencialIdGenerator extends ChainableIdGenerator {
	
	private final Sequencer sequencer;
	
	public SequencialIdGenerator(Sequencer sequencer) {
		this.sequencer = sequencer;
	}
	
	@Override
	protected String generate(Object entity) {
		Sequence annotation = getSequence(entity.getClass());
		return String.valueOf(sequencer.getNextSequence(annotation.table()));
	}
	
	@Override
	public boolean canSupport(Object entity) {
		Validate.notNull(entity, "entity is null.");
		return getSequence(entity.getClass()) != null;
	}
	
	private Sequence getSequence(Class<?> type) {
		return type.getAnnotation(Sequence.class);
	}

}
