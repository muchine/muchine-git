package com.lgu.abc.core.web.view.excel.exporter;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.Validate;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.lgu.abc.core.common.file.download.transmit.FileTransmitter;
import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.web.session.SessionManager;
import com.lgu.abc.core.web.view.base.exporter.TabularContent;
import com.lgu.abc.core.web.view.excel.exporter.style.PrintStyle;
import com.lgu.abc.core.web.view.excel.exporter.style.SheetStyle;

public final class ExcelView extends AbstractExcelView {
	
	public static final String DATA_SOURCE	= "datasource";
	public static final String FILE_NAME		= "filename";
	
	private final ExcelSheet sheet;	
	private final ExcelHeader header;	
	private final TabularContent data;
	
	public ExcelView(ExcelSheet sheet, ExcelHeader header, TabularContent data) {
		this.sheet = sheet;
		this.header = header;
		this.data = data;
	}

	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
		User session = SessionManager.getSession(request);
		Validate.notNull(session, "user session is null.");
		
		final Locale locale = session.getLocale();
		
		Sheet sheet = workbook.createSheet(this.sheet.name(locale));
		setSheetStyle(sheet);
		
		createHeaderCells(sheet, locale);
		
		final Iterable<String[]> content = data.content(model, locale);
		final String fileName = data.filename(locale);
		
		createDataCells(sheet, content, locale);
		
		FileTransmitter transmitter = new FileTransmitter(response);
		transmitter.setFileName(fileName, request);
	}
	
	private void setSheetStyle(Sheet sheet) {
		final SheetStyle sheetStyle = this.sheet.style();
		
		sheet.setDisplayGridlines(sheetStyle.isDisplayGridLines());
        sheet.setPrintGridlines(sheetStyle.isPrintGridLines());
        sheet.setFitToPage(sheetStyle.isFitToPage());
        sheet.setHorizontallyCenter(sheetStyle.isHorizontallyCenter());
        sheet.setAutobreaks(sheetStyle.isAutobreaks());
        
        final PrintStyle printStyle = sheetStyle.getPrintStyle();
        
        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(printStyle.isLandscape());
        printSetup.setFitHeight(printStyle.getFitHeight());
        printSetup.setFitWidth(printStyle.getFitWidth());
	}
	
	private void createHeaderCells(Sheet sheet, Locale locale) {
		final Workbook workbook = sheet.getWorkbook();
		
		final CellStyle style = workbook.createCellStyle();
		header.style(style);
		
		final Font font = workbook.createFont();
		header.font(font);
		
		style.setFont(font);
		
		Row row = sheet.createRow(0);
		row.setHeightInPoints(12.75f);
		
		String[] titles = header.labels(locale);
		for (int i = 0, n = titles.length; i < n ; i++) {
			Cell cell = row.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(style);
		}
	}
	
	private void createDataCells(Sheet sheet, Iterable<String[]> data, Locale locale) {
		int i = 1;
		for (String[] cells : data) {
			Row row = sheet.createRow(i++);
			
			for (int j = 0, n = cells.length; j < n; j++) {
				row.createCell(j).setCellValue(format(cells[j]));				
			}
		}
	}
	
	private String format(String value) {
		return value == null ? "" : value;
	}

}
