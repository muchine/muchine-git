package com.lgu.abc.core.support.converter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lgu.abc.core.mybatis.handlers.type.DateTypeHandler;

public abstract class AbstractLegacyConverter<T, B> implements Converter<T, B> {
	
	protected Log logger = LogFactory.getLog(this.getClass());

	protected String beanizeDate(Date date) {
		return date == null ? null : DateTypeHandler.formatter.print(date, null);
	}
	
	protected Date materializeDate(String date) {
		if (date == null) return null;
		
		try {
			return DateTypeHandler.formatter.parse(date, null);
		} catch (ParseException e) {
			throw new IllegalStateException(e);
		}
	}
	
	protected String beanizeBoolean(Boolean b) {
		return beanizeBoolean(b, true);
	}
	
	protected String beanizeBoolean(Boolean b, boolean nullableReturn) {
		if (nullableReturn && b == null) return null;
		return b ? "Y" : "N";
	}
	
	protected Boolean materializeBoolean(String b) {
		return materializeBoolean(b, true);
	}
	
	protected Boolean materializeBoolean(String b, boolean nullableReturn) {
		return nullableReturn && b == null ? null : "Y".equals(b);
	}
	
	protected String beanizeList(List<?> entities) {
		if (entities == null) return null;
		
		StringBuilder sb = new StringBuilder();
		for (Object o : entities) {
			if (sb.length() > 0) sb.append(",");
			sb.append(o.toString());
		}
		
		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	protected <L> List<L> materializeList(String beans, Class<L> type) {
		return materializeList(beans, type, new Convertable<L>() {

			@Override
			public L convert(String item) {
				return (L) item;
			}
			
		});
		
//		if (beans == null) return null;
//		
//		List<L> entities = new ArrayList<L>();
//		String[] tokens = beans.split(",");
//		
//		if (tokens.length == 0) return null;
//		
//		for (String token : tokens)
//			entities.add((L) token);
//		
//		return entities;
	}
	
	protected List<String> materializeIds(String beans) {
		return materializeList(beans, String.class, new Convertable<String>() {

			@Override
			public String convert(String item) {
				return item;
			}
			
		});
	}
	
	private <L> List<L> materializeList(String beans, Class<L> type, Convertable<L> convertable) {
		if (beans == null) return null;
		
		List<L> entities = new ArrayList<L>();
		String[] tokens = beans.split(",");
		
		if (tokens.length == 0) return null;
		
		for (String token : tokens)
			entities.add(convertable.convert(token));
		
		return entities;
	}
		
	private static interface Convertable<L> {
		
		L convert(String item);
		
	}	
	
}
