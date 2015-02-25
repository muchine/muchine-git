package com.lgu.abc.core.web.editor;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lgu.abc.core.web.Url;
import com.lgu.abc.core.web.controller.ControllerUtil;
import com.lgu.abc.core.web.editor.EditorHomeController;
import com.lgu.abc.test.common.base.BaseTest;
import com.lgu.abc.test.common.base.operation.OperationTester;
import com.lgu.abc.test.common.base.operation.TestRequest;
import com.lgu.abc.test.common.base.operation.factory.OperationTesterFactory;

public class EditorHomeControllerTest extends BaseTest {

	@Autowired
	private EditorHomeController controller;

	@Autowired
	private OperationTesterFactory testerFactory;

	@Test
	public void testGuideDaum() throws Exception {
		TestRequest request = new TestRequest(RequestMethod.GET, new Url(ControllerUtil.getBaseUrl(controller), "/daum/guide.jstl", ""));
		OperationTester tester = testerFactory.create(request, "TEST GUIDEDAUM",null, null);
		tester.doTest();
	}
}