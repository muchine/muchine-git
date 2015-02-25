package com.lgu.abc.core.mybatis.handlers.remover;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lgu.abc.core.base.domain.action.ActionEntity;

public abstract class AbstractCascadeRemover<T extends ActionEntity> implements CascadeRemover<T> {

	protected Log logger = LogFactory.getLog(this.getClass());
	
}
