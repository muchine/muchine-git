package com.lgu.abc.core.web.editor.plugin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.plugin.file.FileUploadableRegistry;
import com.lgu.abc.core.plugin.file.impl.AbstractImageUploadable;

@Component
public class EditorImageUploadable extends AbstractImageUploadable {

	public static final String NAME = "editor";
	
	public static final String PATH = "ckimage/";
	
	@Autowired
	protected EditorImageUploadable(FileUploadableRegistry registry) {
		super(registry);
	}

	@Override
	public String name() {
		return NAME;
	}

	@Override
	public String path() {
		return PATH;
	}

}
