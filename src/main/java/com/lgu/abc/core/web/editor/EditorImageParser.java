package com.lgu.abc.core.web.editor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.lgu.abc.core.web.editor.plugin.EditorImageUploadable;

@Component
public class EditorImageParser extends AbstractEditorImageParser {
	
	private static Pattern pattern = Pattern.compile(EditorImageUploadable.PATH + "([^,\"]*)");
	
	@Override
	protected Pattern pattern() {
		return pattern;
	}
	
	@Override
	protected String basepath() {
		return EditorImageUploadable.PATH;
	}
	
	public static void main(String[] args) {
		String content = "src=\"/image/ckimage/20140307/0f093ccb-7eba-43a2-9536-4fd1e3893f24\" style=\"width: 259px; height: 194px;\" /></p>";
		Matcher matcher = pattern.matcher(content);
		
		if (matcher.find()) System.out.println(matcher.group(1));
	}
	
}
