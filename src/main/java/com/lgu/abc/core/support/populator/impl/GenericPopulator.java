package com.lgu.abc.core.support.populator.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.lgu.abc.core.support.populator.AbstractPopulator;
import com.lgu.abc.core.support.populator.Populator;
import com.lgu.abc.core.support.populator.PopulatorFactory;
import com.lgu.abc.core.support.populator.PopulationSupporter;
import com.lgu.abc.core.support.populator.impl.list.IdentifiableListPopulator;
import com.lgu.abc.core.support.populator.impl.list.NoAdditionIdentifiableListPopulator;
import com.lgu.abc.core.support.populator.impl.list.ObjectListPopulator;

public class GenericPopulator extends AbstractPopulator implements Populator,
		PopulatorFactory {

	private List<Populator> populators;

	private Populator noAdditionIdentifiableListPopulator;

	public GenericPopulator(boolean forced) {
		populators = new ArrayList<Populator>();

		populators.add(new EntityPopulator(this, forced));
		populators.add(new IdPopulator());
		populators.add(new IdentifiableListPopulator(this));
		populators.add(new ObjectListPopulator());

		noAdditionIdentifiableListPopulator = new NoAdditionIdentifiableListPopulator(
				this);
	}

	@Override
	public Object populate(Object source, Object target) {
		for (Populator p : populators) {
			if (!p.canPopulate(source, target)) continue;
			return p.populate(source, target);
		}

		return target;
	}

	@Override
	public boolean canPopulate(Object source, Object target) {
		for (Populator p : populators) {
			if (p.canPopulate(source, target))
				return true;
		}

		return false;
	}

	@Override
	public Populator create(Field field) {
		if (PopulationSupporter.shouldPreserve(field)
				|| PopulationSupporter.containerAnnotated(field)) {
			logger.trace("return populator for preserving data.");
			return noAdditionIdentifiableListPopulator;
		}

		return this;
	}

	@Override
	public Populator create() {
		return this;
	}

}
