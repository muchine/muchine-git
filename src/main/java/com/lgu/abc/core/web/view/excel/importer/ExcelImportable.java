package com.lgu.abc.core.web.view.excel.importer;

import com.lgu.abc.core.common.file.transfer.FileUpload;
import com.lgu.abc.core.web.view.excel.exception.ExcelImportException;

public interface ExcelImportable {

	int importExcel(FileUpload upload) throws ExcelImportException;
	
}
