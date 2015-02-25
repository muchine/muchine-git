package com.lgu.abc.core.common.infra.file.view;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper=true, includeFieldNames=true) @EqualsAndHashCode(callSuper=true)
public @Data class CKEditorFileUploadCommand extends FileUploadCommand {

	private String CKEditorFuncNum;
	
}
