package com.lgu.abc.core.prototype.workspace;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.lgu.abc.core.base.domain.query.BaseQuery;
import com.lgu.abc.core.common.query.Searcher;

@ToString(callSuper=true, includeFieldNames=true) @EqualsAndHashCode(callSuper=true)
public @Data class WorkspaceQuery extends BaseQuery {

	private String type;
	
	private String status;
	
	private String memberId;
	
	private List<String> departmentIds;
	
	private Searcher searcher;
	
	public void search(String text) {
		this.searcher = new Searcher(text);
	}
	
}
