package com.lgu.abc.core.common.vo;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

public final @Data class Tag {
	
	private final String name;
	
	public Tag(String name) {
		this.name = name;
	}
	
	public static String text(List<Tag> tags) {
		if (tags == null) return null;
		
		StringBuilder sb = new StringBuilder();
		
		for (Tag tag : tags) {
			if (sb.length() > 0) sb.append(",");
			sb.append(tag.getName());
		}
		
		return sb.toString();
	}
	
	public static List<Tag> parse(String text) {
		if (text == null) return null;
		
		String[] tokens = text.split(",");
		if (tokens == null || tokens.length == 0) return null;		
		
		List<Tag> tags = new ArrayList<Tag>();
		
		for (String token : tokens) {
			tags.add(new Tag(token));
		}
		
		return tags;
	}
	
}
