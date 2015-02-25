package com.lgu.abc.core.web.editor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgu.abc.core.common.file.FileManager;
import com.lgu.abc.core.common.infra.file.File;
import com.lgu.abc.core.common.infra.file.Image;
import com.lgu.abc.core.common.infra.file.support.FileFinder;
import com.lgu.abc.core.prototype.org.user.User;

public abstract class AbstractEditorImageParser {

	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private FileManager manager;
		
	public void handle(String content, User actor) {
		logger.debug("content: " + content);
		
		List<Image> images = parse(content);
		for (Image image : images) {
			image.setActor(image.getOwner().getUser());
			manager.confirm(image);
		}
	}
	
	public List<Image> parse(String content) {
		List<Image> parsed = new ArrayList<Image>();
		if (StringUtils.isEmpty(content)) return parsed;
		
		Matcher matcher = pattern().matcher(content);
		
		while(matcher.find()) {
			String path = matcher.group(1);
			logger.debug("found: " + path);
			
			FileFinder finder = FileFinder.instance();
			File file = finder.findByPath(filepath(path));
			if (file != null) parsed.add(new Image(file));
		}
		
		return parsed;
	}
	
	private String filepath(String path) {
		return basepath() + path;
	}

	protected abstract String basepath();
	
	protected abstract Pattern pattern();
	
}
