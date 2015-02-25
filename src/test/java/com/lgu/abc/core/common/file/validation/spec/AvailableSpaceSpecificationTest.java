package com.lgu.abc.core.common.file.validation.spec;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Collection;

import org.junit.Test;

import com.lgu.abc.core.common.file.exception.NoAvailableFileSpaceException;
import com.lgu.abc.core.common.file.validation.spec.AvailableSpaceSpecification;
import com.lgu.abc.core.common.infra.file.File;
import com.lgu.abc.core.common.spec.Specification;
import com.lgu.abc.core.common.vo.Size;
import com.lgu.abc.core.common.vo.Size.Unit;

public class AvailableSpaceSpecificationTest {

	private static final Size AVAILABLE_FILE_SIZE = new Size(30, Unit.MB);
	
	private static final Size UNIT_FILE_SIZE = new Size(10, Unit.MB);
	
	@Test
	public void test() {
		Specification spec = new AvailableSpaceSpecification(AVAILABLE_FILE_SIZE);
		assertThat(spec.isSatisfiedBy(FileSpecificationFixtureFactory.files(3, UNIT_FILE_SIZE)), is(true));		
	}
	
	@Test(expected = NoAvailableFileSpaceException.class)
	public void testExceededAvailableSpace() {
		Specification spec = new AvailableSpaceSpecification(AVAILABLE_FILE_SIZE);
		
		Collection<File> files = FileSpecificationFixtureFactory.files(2, UNIT_FILE_SIZE);
		files.add(FileSpecificationFixtureFactory.file(new Size(UNIT_FILE_SIZE.bytes() + 1)));
		
		spec.isSatisfiedBy(files);
	}
	
}
