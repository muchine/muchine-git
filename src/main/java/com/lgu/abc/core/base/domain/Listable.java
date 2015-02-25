package com.lgu.abc.core.base.domain;

import java.util.List;

/**
 * Entity for domain controller to handle multiple creation, update, delete requests.
 * All domain objects handled by domain controller should implement this interface.
 * 
 * @author sejoon
 *
 * @param <T>
 */
public interface Listable<T> {
	List<T> getEntities();
}
