package com.lgu.abc.core.common.infra.file.support;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgu.abc.core.common.infra.file.File;
import com.lgu.abc.core.common.infra.file.fixture.FileFixtureGenerator;
import com.lgu.abc.core.common.infra.file.support.FileFinder;
import com.lgu.abc.test.common.base.BaseTest;

public class FileFinderTest extends BaseTest {

	@Autowired
	private FileFixtureGenerator generator;
	
	@Autowired
	private FileFinder finder;
	
	@Test
	public void testFindById() {
		File file = generator.generate();
		
		File found = finder.findById(file.getId());
		logger.debug("found: " + found);
		assertThat(found, is(notNullValue()));
	}
	
	@Test
	public void testFindByLink() {
		// Given
		File file = createLargeFile();
		
		// When
		File found = finder.findByLink(file.getLink().getLink());
		
		// Then
		logger.debug("found: " + found);
		assertThat(found, is(notNullValue()));
	}
	
	@Test
	public void testFindByPath() {
		// Given
		File file = generator.generate();
		
		// When
		File found = finder.findByPath(file.path());
		
		// Then
		logger.debug("found: " + found);
		assertThat(found, is(notNullValue()));
	}
	
	private File createLargeFile() {
		File file = generator.generate();
		assertThat(file.getVolume().isLarge(), is(true));
		
		return file;
	}
		
}
