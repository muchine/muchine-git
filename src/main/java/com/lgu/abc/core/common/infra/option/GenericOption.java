package com.lgu.abc.core.common.infra.option;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.lgu.abc.core.base.domain.action.RootEntity;
import com.lgu.abc.core.base.utils.IterableUtils;
import com.lgu.abc.core.common.infra.option.exception.GenericOptionNotFoundException;
import com.lgu.abc.core.common.interfaces.Ownable;

@ToString(callSuper=true, includeFieldNames=true) @EqualsAndHashCode(callSuper=true)
public @Data abstract class GenericOption extends RootEntity {

	private String name;
	
	private String value;
	
	protected GenericOption() {}
	
	protected GenericOption(Ownable ownable, String name, String value) {
		setOwnable(ownable);
		this.name = name;
		this.value = value;
	}
	
	public static <T extends GenericOption> int intValue(String name, Iterable<T> options) {
		return new Integer(find(name, options));
	}
	
	public static <T extends GenericOption> boolean booleanValue(String name, Iterable<T> options) {
		return Boolean.parseBoolean(find(name, options));
	}
	
	public static <T extends GenericOption> String find(String name, Iterable<T> options) {
		if (IterableUtils.isEmpty(options)) throw new GenericOptionNotFoundException();
		
		for (GenericOption option : options) {
			if (option.getName().equals(name)) return option.getValue();
		}
		
		throw new GenericOptionNotFoundException();
	}
	
}
