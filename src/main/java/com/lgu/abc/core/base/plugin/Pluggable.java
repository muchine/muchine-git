package com.lgu.abc.core.base.plugin;

import com.lgu.abc.core.common.vo.Name;

public interface Pluggable extends Sortable {

	String id();
	
	Name name();
	
}
