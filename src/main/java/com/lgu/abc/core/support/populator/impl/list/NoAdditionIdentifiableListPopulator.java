package com.lgu.abc.core.support.populator.impl.list;

import com.lgu.abc.core.common.interfaces.Identifiable;
import com.lgu.abc.core.support.populator.Populator;
import com.lgu.abc.core.support.populator.impl.GenericPopulator;

public class NoAdditionIdentifiableListPopulator extends IdentifiableListPopulator implements Populator {

	public NoAdditionIdentifiableListPopulator(GenericPopulator populator) {
		super(populator);
	}
	
	@Override
	protected boolean shouldAdd(Identifiable source) {
		/*
		 * No element should be added to the target list when the aggregate strategy is delete-insert or authority 
		 */
		logger.debug("skip to add element to target list... source id: " + source.getId());
		return false;
	}

}
