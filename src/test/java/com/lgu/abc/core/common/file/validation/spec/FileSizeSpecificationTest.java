package com.lgu.abc.core.common.file.validation.spec;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.lgu.abc.core.common.file.exception.FileSizeExcessException;
import com.lgu.abc.core.common.file.validation.spec.FileSizeSpecification;
import com.lgu.abc.core.common.spec.Specification;
import com.lgu.abc.core.common.vo.Size;
import com.lgu.abc.core.common.vo.Size.Unit;

public class FileSizeSpecificationTest {

	private static final Size MAX_FILE_SIZE = new Size(10, Unit.MB);
	
	@Test
	public void test() {
		Specification spec = new FileSizeSpecification(MAX_FILE_SIZE);
		assertThat(spec.isSatisfiedBy(FileSpecificationFixtureFactory.files(2, MAX_FILE_SIZE)), is(true));		
	}
	
	@Test(expected = FileSizeExcessException.class)
	public void testExceededFileSize() {
		Specification spec = new FileSizeSpecification(MAX_FILE_SIZE);
		
		Size exceeded = new Size(MAX_FILE_SIZE.bytes() + 1);
		spec.isSatisfiedBy(FileSpecificationFixtureFactory.files(2, exceeded));
	}
	
}
