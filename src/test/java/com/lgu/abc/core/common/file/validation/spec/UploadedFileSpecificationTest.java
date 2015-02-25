package com.lgu.abc.core.common.file.validation.spec;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import com.lgu.abc.core.common.file.validation.spec.UploadedFileSpecification;
import com.lgu.abc.core.common.infra.file.File;
import com.lgu.abc.core.common.spec.Specification;
import com.lgu.abc.core.common.vo.Size;
import com.lgu.abc.core.common.vo.Size.Unit;

public class UploadedFileSpecificationTest {

	@Test
	public void test() {
		Specification spec = new UploadedFileSpecification();
		assertThat(spec.isSatisfiedBy(FileSpecificationFixtureFactory.files(1, new Size(10, Unit.MB))), is(true));		
	}
	
	@Test
	public void testNotUploadedFile() {
		Specification spec = new UploadedFileSpecification();
		
		Collection<File> files = new ArrayList<File>();
		files.add(null);
		assertThat(spec.isSatisfiedBy(files), is(false));
	}
	
	@Test
	public void testNullFileId() {
		Specification spec = new UploadedFileSpecification();
		File file = FileSpecificationFixtureFactory.file(new Size(10, Unit.MB));
		file.setId(null);
		
		Collection<File> files = new ArrayList<File>();
		files.add(file);
		
		assertThat(spec.isSatisfiedBy(files), is(false));
	}
	
}
