package com.lgu.abc.core.common.query;

import lombok.Getter;

public class Condition<T> {

	public enum Operator {
		AND, OR;
	}
	
	public enum Equality {
		LESS_THAN("<"), LESS_THAN_AND_EQUAL("<="), EQUAL("="), GREATER_THAN(">"), GREATER_THAN_AND_EQUAL(">="), LIKE("LIKE"), IN("IN");
		
		private final String code;
		
		private Equality(String code) {
			this.code = code;
		}

		@Override
		public String toString() {
			return code;
		}
	}
	
	@Getter
	private final Operator op;
	
	@Getter
	private final Equality equality;
	
	@Getter
	private T value;
	
	public Condition() {
		this(Operator.AND, Equality.EQUAL);
	}
	
	public Condition(T value) {
		this();
		this.value = value;
	}
	
	public Condition(Operator op, Equality equality) {
		this.op = op;
		this.equality = equality;
	}
		
	public Condition(Operator op, Equality equality, T value) {
		this(op, equality);
		this.value = value;
	}
	
}
