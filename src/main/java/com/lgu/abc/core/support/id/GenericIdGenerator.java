package com.lgu.abc.core.support.id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.common.infra.sequence.Sequencer;
import com.lgu.abc.core.support.id.impl.CompanyIdGenerator;
import com.lgu.abc.core.support.id.impl.SequencialIdGenerator;
import com.lgu.abc.core.support.id.impl.UserIdGenerator;

@Component
public class GenericIdGenerator implements IdGenerator {

	private final IdGenerator idGenerator;
	
	@Autowired
	public GenericIdGenerator(Sequencer sequencer) {
		ChainableIdGenerator idGenerator = new SequencialIdGenerator(sequencer);
		idGenerator.setNext(new CompanyIdGenerator()).setNext(new UserIdGenerator());
		
		this.idGenerator = idGenerator;
	}
	
	@Override
	public String generateId(Object entity) {
		return idGenerator.generateId(entity);
	}

	@Override
	public void setMonitor(IdMonitor monitor) {
		idGenerator.setMonitor(monitor);
	}

}
