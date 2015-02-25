package com.lgu.abc.core.common.infra.file.view;

import javax.validation.constraints.NotNull;

import lombok.Data;

public @Data class FileUploadCommand {

	@NotNull
	private String type;
	
	private boolean large;
	
}
