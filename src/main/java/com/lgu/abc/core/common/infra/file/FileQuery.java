package com.lgu.abc.core.common.infra.file;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.lgu.abc.core.base.domain.query.BaseQuery;
import com.lgu.abc.core.common.infra.file.domain.FileLink;

@ToString(callSuper=true, includeFieldNames=true) @EqualsAndHashCode(callSuper=true)
public @Data class FileQuery extends BaseQuery {

	private FileLink link;
	
	private String path;
	
}
