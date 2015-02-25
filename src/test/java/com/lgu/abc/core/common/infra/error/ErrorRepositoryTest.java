package com.lgu.abc.core.common.infra.error;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgu.abc.core.common.infra.error.repo.ErrorRepository;
import com.lgu.abc.test.common.base.BaseTest;

import com.lgu.abc.core.common.infra.error.Error;

public class ErrorRepositoryTest extends BaseTest {

	@Autowired
	private ErrorRepository repository;
	
	@Test
	public void testCreateError() {
		repository.create(new Error("172.20.240.21", "This is test message", "orgos.biz", "172.20.240.21", "10000"));
	}
	
}
