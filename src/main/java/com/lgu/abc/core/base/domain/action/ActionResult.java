package com.lgu.abc.core.base.domain.action;

import lombok.Data;
import lombok.Getter;

public @Data class ActionResult {

	public enum ResultType {
		SUCCESS(false), FAILURE(true);
		
		@Getter
		private final boolean error;
		
		private ResultType(boolean error) {
			this.error = error;
		}
		
		public static ResultType get(boolean hasError) {
			for (ResultType type : ResultType.values()) {
				if (type.error == hasError) return type;
			}
			
			throw new IllegalStateException("result type not found for error: " + hasError);
		}
	}
	
	public ActionResult() {}
	
	public ActionResult(boolean successful) {
		this(successful, "");
	}
	
	public ActionResult(boolean successful, String message) {
		this(ResultType.get(!successful), message);
	}
	
	public ActionResult(ResultType type) {
		this(type, "");
	}
	
	public ActionResult(ResultType type, String message) {
		this.type = type;
		this.message = message;
	}
	
	private ResultType type;
	
	private String message;
	
	public boolean isSuccess() {
		return type == ResultType.SUCCESS;
	}

}
