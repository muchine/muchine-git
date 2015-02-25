package com.lgu.abc.core.web.view.excel.importer;

import java.io.InputStream;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import com.lgu.abc.core.common.file.transfer.FileUpload;
import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.web.view.excel.exception.ExcelImportException;
import com.lgu.abc.core.web.view.excel.exception.UnknownExcelFormatException;

public abstract class AbstractExcelImporter implements ExcelImportable {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Override
	public final int importExcel(FileUpload upload) throws ExcelImportException {
		try {
			logger.debug("imported file: " + upload);
			
			if (upload.getFile() == null) return 0;
			InputStream in = upload.getFile().getInputStream();
			
			HSSFWorkbook workbook = new HSSFWorkbook(in);
			HSSFSheet sheet = workbook.getSheetAt(0);
			
			logger.info("start importing excel...");
			Iterator<Row> iterator = sheet.iterator();
			int imported = process(upload.getActor(), iterator);
			
			logger.debug(imported + " row(s) imported for the file: " + upload);
			return imported;
		} catch(ExcelImportException e) {
			throw e;
		} catch(Exception e) {
			throw new ExcelImportException(new UnknownExcelFormatException(e));
		}
	}
	
	protected abstract int process(User actor, Iterator<Row> iterator) throws ExcelImportException;

}
