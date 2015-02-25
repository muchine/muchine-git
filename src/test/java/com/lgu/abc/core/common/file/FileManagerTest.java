package com.lgu.abc.core.common.file;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;

import com.lgu.abc.core.common.file.exception.IllegalFileStatusException;
import com.lgu.abc.core.common.file.transfer.FileUpload;
import com.lgu.abc.core.common.infra.file.File;
import com.lgu.abc.core.common.infra.file.support.FileFinder;
import com.lgu.abc.core.common.vo.time.Time;
import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.test.common.base.BaseTest;
import com.lgu.abc.test.mock.file.MockFileUploadable;

public class FileManagerTest extends BaseTest {

	@Autowired
	private FileManager manager;
	
	@Autowired
	private FileFinder finder;
	
	@Test
	public void testUpload() {
		printTestHeader("TEST UPLOAD");
		
		// When
		File uploaded = upload();
		logger.debug("uploaded: " + uploaded);
		
		// Then
		validateUploaded(uploaded);
		assertThat(uploaded.isTemp(), is(true));
	}
	
	@Test
	public void testCopy() {
		printTestHeader("TEST COPY");
		
		// When
		File confirmed = confirm(getSession());
		File copied = manager.copy(MockFileUploadable.NAME, confirmed);		
		
		// Then
		assertThat(copied.size(), is(confirmed.size()));
		validateUploaded(copied);
	}
	
	@Test
	public void testConfirm() {
		printTestHeader("TEST CONFIRM");
		File confirmed = confirm(getSession());
		
		// Then
		validateUploaded(confirmed);
		assertThat(confirmed.isTemp(), is(false));
	}
	
	@Test
	public void testUpdate() {
		// Given
		File uploaded = upload();
		
		// When
		update(uploaded);
		
		// Then
		File updated = finder.findById(uploaded.getId());
		validateUploaded(updated);
		assertThat(updated.isTemp(), is(true));
		assertThat(updated.getVolume().isLarge(), is(true));
	}
	
	@Test(expected = IllegalFileStatusException.class)
	public void testUpdateConfirmedFile() {
		update(confirm(getSession()));
	}
	
	private void update(File uploaded) {
		printTestHeader("TEST UPDATE");
		
		assertThat(uploaded.getVolume().isLarge(), is(false));
		uploaded.getVolume().setLarge(true);
		
		// When
		manager.update(uploaded);		
	}
	
	@Test
	public void testInvalidate() {
		// Given
		printTestHeader("TEST INVALIDATE");
		File confirmed = confirm(getSession());
		
		// When
		manager.invalidate(confirmed, getSession());
		
		// Then
		File invalidated = finder.findById(confirmed.getId());
		
		validateUploaded(invalidated);
		assertThat(invalidated.isTemp(), is(true));
	}
	
	@Test
	public void testCreateExternalLink() {
		// Given
		File confirmed = confirm(getSession());
		Date expired = new Time().plusMinutes(30).toDate();
		
		// When
		String link = manager.createExternalLink(confirmed.getId(), expired);
		
		// Then
		File found = finder.findById(confirmed.getId());
		logger.debug("found: " + found);
		assertThat(found.getLink().getLink(), is(link));
	}
		
	private File confirm(User session) {
		// Given
		File uploaded = upload();
		
		// When
		uploaded.setActor(session);
		manager.confirm(uploaded);
		
		return uploaded;
	}
		
	private File upload() {
		try {
			final MockMultipartFile file = new MockMultipartFile("test", "test.txt", "txt", 
					"This is a content of the test file".getBytes());
			return manager.upload(MockFileUploadable.NAME, new FileUpload(getSession(), file));
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private void validateUploaded(File uploaded) {
		java.io.File file = new java.io.File(uploaded.fullpath());
		logger.debug("filepath: " + uploaded.fullpath());
		assertThat(file.exists(), is(true));
		
		file.delete();
	}
	
}
