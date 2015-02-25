package com.lgu.abc.core.common.infra.file.support.icon.type;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.lgu.abc.core.common.infra.file.support.icon.FileIcon;

public class FileIconFactoryTest {

	@Test
	public void testPictureIcon() {
		FileIcon icon = new FilePictureIcon(); 
		
		assertThat(FileIconFactory.icon("gif"), is(icon));
		assertThat(FileIconFactory.icon("jpg"), is(icon));
		assertThat(FileIconFactory.icon("png"), is(icon));
		assertThat(FileIconFactory.icon("bmp"), is(icon));
		assertThat(FileIconFactory.icon("jpeg"), is(icon));
	}
	
	@Test
	public void testZipIcon() {
		FileIcon icon = new FileZipIcon(); 
		
		assertThat(FileIconFactory.icon("zip"), is(icon));
		assertThat(FileIconFactory.icon("rar"), is(icon));
		assertThat(FileIconFactory.icon("arz"), is(icon));
		assertThat(FileIconFactory.icon("7z"), is(icon));
	}
	
	@Test
	public void testHwpIcon() {
		FileIcon icon = new FileHwpIcon(); 
		
		assertThat(FileIconFactory.icon("hwp"), is(icon));
	}
	
	@Test
	public void testExcelIcon() {
		FileIcon icon = new FileExcelIcon(); 
		
		assertThat(FileIconFactory.icon("xls"), is(icon));
		assertThat(FileIconFactory.icon("xlsx"), is(icon));
	}
	
	@Test
	public void testWordIcon() {
		FileIcon icon = new FileWordIcon(); 
		
		assertThat(FileIconFactory.icon("doc"), is(icon));
		assertThat(FileIconFactory.icon("docx"), is(icon));
	}
	
	@Test
	public void testTextIcon() {
		FileIcon icon = new FileTextIcon(); 
		
		assertThat(FileIconFactory.icon("txt"), is(icon));
	}

	@Test
	public void testExeIcon() {
		FileIcon icon = new FileExeIcon(); 
		
		assertThat(FileIconFactory.icon("exe"), is(icon));
	}
	
	@Test
	public void testPdfIcon() {
		FileIcon icon = new FilePdfIcon(); 
		
		assertThat(FileIconFactory.icon("pdf"), is(icon));
	}
	
	@Test
	public void testHtmlIcon() {
		FileIcon icon = new FileHtmlIcon(); 
		
		assertThat(FileIconFactory.icon("html"), is(icon));
		assertThat(FileIconFactory.icon("htm"), is(icon));
		assertThat(FileIconFactory.icon("js"), is(icon));
		assertThat(FileIconFactory.icon("css"), is(icon));
	}
	
	@Test
	public void testPowerpointIcon() {
		FileIcon icon = new FilePowerpointIcon(); 
		
		assertThat(FileIconFactory.icon("ppt"), is(icon));
		assertThat(FileIconFactory.icon("pptx"), is(icon));
	}
	
	@Test
	public void testDefaultIcon() {
		FileIcon icon = new FileDefaultIcon(); 
		
		assertThat(FileIconFactory.icon("eml"), is(icon));
		assertThat(FileIconFactory.icon("aaa"), is(icon));
	}
	
}
