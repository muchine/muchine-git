package com.lgu.abc.core.common.infra.option;

import java.util.List;

import com.lgu.abc.core.common.interfaces.Ownable;

public interface OptionMarshallable<T extends GenericOption> {

	Ownable ownable();
	
	List<T> marshall();
	
	void unmarshall(Ownable ownable, Iterable<T> options);
	
}
