package com.lgu.abc.core.common.infra.file;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgu.abc.core.base.operation.OperationType;
import com.lgu.abc.core.common.file.MockFileUploader;
import com.lgu.abc.core.common.infra.file.fixture.FileFixtureFactory;
import com.lgu.abc.core.common.infra.file.repo.FileRepository;
import com.lgu.abc.core.common.infra.file.service.FileService;
import com.lgu.abc.core.common.infra.file.service.FileServiceImpl;
import com.lgu.abc.core.support.annotation.Operations;
import com.lgu.abc.test.common.base.service.BaseServiceTest;
import com.lgu.abc.test.mock.file.MockFileUploadable;
import com.u2ware.springfield.service.EntityService;

@Operations(type = {
	OperationType.CREATE,
	OperationType.UPDATE,
	OperationType.DELETE,
	OperationType.FIND_FORM
})
public class FileServiceTest extends BaseServiceTest<File, FileQuery> {

	@Autowired
	private FileService service;
	
	@Autowired
	private FileFixtureFactory factory;
	
	@Autowired
	private MockFileUploader uploader;
	
	@Override
	protected EntityService<File, FileQuery> initService() {
		setFixtureFactory(factory);
		return service;
	}
	
	@Test
	public void testShrink() {
		// Given
		File file = uploader.upload(getSession(), MockFileUploadable.NAME);
		List<File> files = new ArrayList<File>();
		files.add(file);
		
		FileRepository repository = mock(FileRepository.class);
		when(repository.findAll(any())).thenReturn(files);
		
		FileService service = new FileServiceImpl(repository);
		
		// When
		service.shrink(getSession());
		
		// Then
		assertThat(new java.io.File(file.fullpath()).exists(), is(false));
	}
	
}
