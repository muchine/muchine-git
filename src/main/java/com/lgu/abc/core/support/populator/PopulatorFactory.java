package com.lgu.abc.core.support.populator;

import java.lang.reflect.Field;

public interface PopulatorFactory {

	Populator create(Field field);
	
	Populator create();
	
}
