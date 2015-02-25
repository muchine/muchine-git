package com.lgu.abc.core.common.infra.sequence.procedure;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lgu.abc.core.common.infra.sequence.Sequence;
import com.lgu.abc.core.common.infra.sequence.Sequencer;

@Component
public class ProcedureSequencer implements Sequencer {

	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private SqlSessionTemplate template;
	
	/**
	 * Get next sequence by executing a stored procedure. Need to consider whether to set propagation as NOT_SUPPORT or REQUIRES_NEW 
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@Override
	public long getNextSequence(String id) {
		Sequence sequence = template.selectOne("com.lgu.abc.common.sequence.ProcedureSequencer.read", id);
//		logger.debug("sequence: " + sequence);
		
		return sequence.getSequence();
	}

}
