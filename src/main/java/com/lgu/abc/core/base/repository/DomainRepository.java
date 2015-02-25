package com.lgu.abc.core.base.repository;

import java.io.Serializable;

public interface DomainRepository<T, P, ID extends Serializable> 
		extends BaseRepository<T, ID> {
	
}
