package com.lgu.abc.core.common.infra.file;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lgu.abc.core.common.configuration.AbcConfig;
import com.lgu.abc.core.common.file.MockFileUploader;
import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.web.Url;
import com.lgu.abc.core.web.controller.ControllerUtil;
import com.lgu.abc.test.common.base.BaseTest;
import com.lgu.abc.test.common.base.operation.OperationTester;
import com.lgu.abc.test.common.base.operation.TestRequest;
import com.lgu.abc.test.common.base.operation.factory.OperationTesterFactory;
import com.lgu.abc.test.mock.file.MockFileUploadable;
import com.lgu.abc.test.support.TestUtils;

public class FileControllerTest extends BaseTest {

	@Autowired
	private AbcConfig configuration;
	
	@Autowired
	private FileController controller;
	
	@Autowired
	private OperationTesterFactory testerFactory;
	
	@Autowired
	private TestUploadMonitor monitor;
	
	@Autowired
	private MockFileUploader uploader;
	
	@Test
	public void testUploadNormalFile() throws Exception {
		testUploadFile(false, configuration.upload().file().location().permanent());
	}
	
	@Test
	public void testUploadLargeFile() throws Exception {
//		testUploadFile(true, configuration.upload().file().location().large());
		testUploadFile(true, configuration.upload().file().location().permanent());
	}
	
	private void testUploadFile(boolean large, String location) throws Exception {
		// Given
		monitor.clear();
		
		final MockMultipartFile file = new MockMultipartFile(FileController.UPLOAD_PARAM, "test.txt", "txt", 
				"This is a content of the test file".getBytes());
		
		final Url url = new Url(ControllerUtil.getBaseUrl(controller), "/upload", "");
		TestRequest request = new TestRequest(RequestMethod.POST, url);
		request.setFile(file);
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("type", MockFileUploadable.NAME);
		params.put("large", String.valueOf(large));
		
		// When
		OperationTester tester = testerFactory.create(request, "TEST UPLOAD", params, null);
		ResultActions actions = tester.doTest();
		
		// Then
		assertThat(TestUtils.getContent(actions).contains("success"), is(true));
		
		File uploaded = monitor.getUploaded().get(0);
		assertThat(uploaded.getProperty().getName(), is("test.txt"));
		
		logger.debug("full path: " + uploaded.fullpath() + ", location: " + location);
		assertThat(uploaded.fullpath().contains(location), is(true));
		
		new java.io.File(uploaded.fullpath()).delete(); 
	}
	
	@Test
	public void testUploadFromCKEditor() throws Exception {
		// Given
		final MockMultipartFile file = new MockMultipartFile(FileController.UPLOAD_PARAM, "test.txt", "txt", 
				"This is a content of the test file".getBytes());
		
		final Url url = new Url(ControllerUtil.getBaseUrl(controller), "/upload/ckeditor", "");
		TestRequest request = new TestRequest(RequestMethod.POST, url);
		request.setFile(file);
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("type", MockFileUploadable.NAME);
		params.put("large", String.valueOf(false));
		params.put("CKEditorFuncNum", "1234");
		
		// When
		OperationTester tester = testerFactory.create(request, "TEST UPLOAD FROM CKEDITOR", params, "imageURL");
		ResultActions actions = tester.doTest();
		
		// Then
		assertThat(TestUtils.getResult(actions, "imageURL"), is(notNullValue()));
		assertThat(TestUtils.getResult(actions, "CKEditorFuncNum").toString(), is("1234"));
	}
	
	@Test
	public void testDownloadFile() throws Exception {
		// Given
		TestRequest request = createDownloadRequest(getSession());
		
		// When
		OperationTester tester = testerFactory.create(request, "TEST DOWNLOAD", null, null);
		ResultActions actions = tester.doTest();
		
		// Then
		assertTrue(actions.andReturn().getResponse().getContentLength() > 0);
	}
	
	@Test
	public void testDonwloadUnauthorizedFile() throws Exception {
		// Given
		TestRequest request = createDownloadRequest(getSessionFactory().getOtherCompanyUser());
		
		// When
		OperationTester tester = testerFactory.create(request, "TEST DOWNLOAD UNAUTHORIZED FILE", null, null);
		ResultActions actions = tester.doTest();
		
		// Then
		assertTrue(actions.andReturn().getResponse().getContentLength() == 0);
	}
	
	private TestRequest createDownloadRequest(User actor) {
		File uploaded = uploader.upload(actor, MockFileUploadable.NAME);
		
		Url url = new Url(ControllerUtil.getBaseUrl(controller), "/download/" + uploaded.getId(), "");
		return new TestRequest(RequestMethod.GET, url);
	}
	
	@Test
	public void testDonwloadByExternalLink() throws Exception {
		// Given
		File uploaded = uploader.upload(getSessionFactory().getOtherCompanyUser(), MockFileUploadable.NAME, true);
		assertThat(uploaded.link(), is(notNullValue()));
		
		Url url = new Url(ControllerUtil.getBaseUrl(controller), "/download/external/" + uploaded.link(), "");
		TestRequest request = new TestRequest(RequestMethod.GET, url);
		
		// When
		OperationTester tester = testerFactory.create(request, "TEST DOWNLOAD BY EXTERNAL LINK", null, null);
		ResultActions actions = tester.doTest();
		
		// Then
		assertThat(actions.andReturn().getResponse().getContentLength(), is((int) uploaded.size().bytes()));
	}
		
}
