package com.lgu.abc.core.web.view.csv;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.Validate;
import org.springframework.web.servlet.view.AbstractView;

import com.lgu.abc.core.common.file.download.transmit.FileTransmitter;
import com.lgu.abc.core.common.file.download.transmit.Transmittable;
import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.web.session.SessionManager;
import com.lgu.abc.core.web.view.base.exporter.TabularContent;
import com.lgu.abc.core.web.view.base.exporter.TabularHeader;

public final class CsvView extends AbstractView {

	private final TabularHeader header;
	private final TabularContent data;
	
	public CsvView(TabularHeader header, TabularContent data) {
		this.header = header;
		this.data = data;
	}
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		User session = SessionManager.getSession(request);
		Validate.notNull(session, "user session is null.");
		
		final Locale locale = session.getLocale();
		
		final String[] title = header.labels(locale);
		final Iterable<String[]> content = data.content(model, locale);
		final String filename = data.filename(locale);
		
		Validate.notNull(content, "data content is null.");
		Validate.notNull(filename, "file name is null.");
		
		FileTransmitter transmitter = new FileTransmitter(response);
		transmitter.setContentType("text/csv");
		transmitter.setFileName(filename, request);
		transmitter.transmitToResponse(new Transmittable() {

			@Override
			public void transmit(OutputStream stream) throws IOException {
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stream));
				
				try {
					writeRow(writer, title);
					for (String[] row : content) writeRow(writer, row);
					
					writer.flush();
				} finally {
					writer.close();	
				}				
			}
			
		});
	}
	
	private void writeRow(BufferedWriter writer, String[] row) throws IOException {
		int count = 0;
		for (String cell : row) {
			if (count++ > 0) writer.write(",");
			writer.write(refine(cell));
		}
		
		/*
		 * If use newLine() method, line feed character might be different based on a system platform.
		 * Therefore, csv file is not showed correctly in an excel program. To avoid this, we use carriage return and line feed
		 * (traditional windows line feed characters) for a new line.
		 */
//		writer.newLine();
		writer.write("\r\n");
	}
	
	private String refine(String value) {
		if (value == null) return "";
		
		String refined = value.replaceAll(",", " ");
		refined = refined.replaceAll("\n", " ");
		refined = refined.replaceAll("\r", " ");
		refined = refined.replaceAll("\r\n", " ");
		
		return refined;
	}

}
