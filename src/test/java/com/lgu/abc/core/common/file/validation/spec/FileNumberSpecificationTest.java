package com.lgu.abc.core.common.file.validation.spec;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.lgu.abc.core.common.file.exception.FileNumberExcessException;
import com.lgu.abc.core.common.file.validation.spec.FileNumberSpecification;
import com.lgu.abc.core.common.spec.Specification;
import com.lgu.abc.core.common.vo.Size;
import com.lgu.abc.core.common.vo.Size.Unit;

public class FileNumberSpecificationTest {

	private static final int MAX_FILE_NUMBER = 5;
	
	@Test
	public void test() {
		Specification spec = new FileNumberSpecification(MAX_FILE_NUMBER);
		assertThat(spec.isSatisfiedBy(FileSpecificationFixtureFactory.files(MAX_FILE_NUMBER, new Size(10, Unit.MB))), is(true));		
	}
	
	@Test(expected = FileNumberExcessException.class)
	public void testExceededFileNumber() {
		Specification spec = new FileNumberSpecification(MAX_FILE_NUMBER);
		spec.isSatisfiedBy(FileSpecificationFixtureFactory.files(MAX_FILE_NUMBER + 1, new Size(10, Unit.MB)));
	}
	
}
