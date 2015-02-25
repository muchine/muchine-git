package com.lgu.abc.core.web.security.xss;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

public class XssFilterTest {
	
	protected final Log logger = LogFactory.getLog(getClass());

	@Test
	public void testNewLine() {
		final String input = "abcde\nfgh";
		String cleaned = clean(input);
		
		logger.debug("cleaned: " + cleaned);
		assertThat(cleaned.contains("\n"), is(true));
	}
	
	@Test
	public void testImageSource() {
		final String input = "<table align=\"center\" valign=\"left\" style=\"margin:1px 2px 3px 4px\"><tr title=\"aaaaa\"><td><img src=\"/abc.com/aaa.jpg\"/><span style=\"font-size:10px;\"></span></td></tr></table";
		String cleaned = clean(input);
		logger.debug("cleaned: " + cleaned);
		
		String[] contained = new String[] {"title", "src", "/abc.com", "style"};
		
		for (String c : contained) assertThat(cleaned.contains(c), is(true));
	}
	
	@Test
	public void testTextarea() {
		String input = "<textarea>.aa {width:10px}</textarea>";
		String cleaned = clean(input);
		
		logger.debug("cleaned: " + cleaned);
	}
	
	@Test
	public void testStyleTag() {
		String input = "<style type=\"text/css\"><!--.aa {width:10px}--></style>";
		String cleaned = clean(input);
		
		logger.debug("cleaned: " + cleaned);
	}
	
	private String clean(String input) {
		return Jsoup.clean(input, "", XssWhiteList.supported(), new Document.OutputSettings().prettyPrint(false));
	}	
	
}
