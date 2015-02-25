package com.lgu.abc.core.common.domain.label.interfaces;

import java.util.Collection;

import com.lgu.abc.core.common.domain.label.Label;

public interface LabelAttachable<T extends Label> {

	/**
	 * Return the currently attached labels
	 * @return the currently attached labels
	 */
	Collection<T> labels();
	
	/**
	 * Attach the given labels
	 * @param labels the labels to attach
	 */
	void labels(Collection<T> labels);
	
}
