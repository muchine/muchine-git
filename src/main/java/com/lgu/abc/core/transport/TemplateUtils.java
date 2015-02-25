package com.lgu.abc.core.transport;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;

public class TemplateUtils {

	public static String getTemplate(Class<?> type, String filename) {
		try {
			StringWriter writer = new StringWriter();
			IOUtils.copy(type.getResourceAsStream(filename), writer, "UTF-8");
			
			return writer.toString();
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
	
}
