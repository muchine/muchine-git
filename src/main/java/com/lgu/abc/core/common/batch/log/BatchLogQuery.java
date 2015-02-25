package com.lgu.abc.core.common.batch.log;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.lgu.abc.core.base.domain.query.BaseQuery;

@ToString(callSuper=true, includeFieldNames=true) @EqualsAndHashCode(callSuper=true)
public @Data class BatchLogQuery extends BaseQuery {

	private Date started = new Date();
	
}
