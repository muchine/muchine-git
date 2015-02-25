package com.lgu.abc.core.mybatis.handlers.graph.policy;


public class StatementNamePolicyFactory {
	
	private static final FieldStatementNamePolicy fieldNamePolicy = new FieldStatementNamePolicy();
	
	@SuppressWarnings("unused")
	private static final ClassStatementNamePolicy classNamePolicy = new ClassStatementNamePolicy();
	
	public static StatementNamePolicy getDefaultPolicy() {
		return fieldNamePolicy;
	}
}
