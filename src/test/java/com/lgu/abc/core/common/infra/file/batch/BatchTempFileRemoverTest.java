package com.lgu.abc.core.common.infra.file.batch;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgu.abc.core.common.file.MockFileUploader;
import com.lgu.abc.core.common.infra.file.File;
import com.lgu.abc.core.common.infra.file.repo.FileRepository;
import com.lgu.abc.core.common.vo.time.Time;
import com.lgu.abc.test.common.base.BaseTest;
import com.lgu.abc.test.mock.file.MockFileUploadable;

public class BatchTempFileRemoverTest extends BaseTest {

	@Autowired
	private MockFileUploader uploader;
	
	@Autowired
	private FileRepository repository;
	
	@Autowired
	private BatchTempFileRemover remover;
	
	@Test
	public void testProcess() {
		// Given
		File uploaded = uploader.upload(getSession(), MockFileUploadable.NAME);
		File expired = expired();
		assertThat(new java.io.File(expired.fullpath()).exists(), is(true));
		
		// When
		remover.process();
		
		// Then
		File found = repository.read(uploaded);
		assertThat(found, is(notNullValue()));
		assertThat(found.isTemp(), is(true));
		
		File removed = repository.read(expired);
		assertThat(removed, is(nullValue()));
		assertThat(new java.io.File(expired.fullpath()).exists(), is(false));
	}
	
	private File expired() {
		File expired = uploader.upload(getSession(), MockFileUploadable.NAME);
		expired.setExpiredDate(new Time().minusDays(1).toDate());
		
		repository.update(expired);
		return expired;
	}
	
}
