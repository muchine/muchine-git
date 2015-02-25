package com.lgu.abc.core.common.file.validation.spec;

import java.util.ArrayList;
import java.util.Collection;

import com.lgu.abc.core.common.infra.file.File;
import com.lgu.abc.core.common.infra.file.domain.FileProperty;
import com.lgu.abc.core.common.infra.file.domain.FileVolume;
import com.lgu.abc.core.common.vo.Size;

public class FileSpecificationFixtureFactory {

	public static Collection<File> files(int number, Size size) {
		Collection<File> files = new ArrayList<File>();
		for (int i = 0; i < number; i++) {
			File file = file(size);
			file.setId(String.valueOf(i));
			files.add(file);
		}
		
		return files;
	}
	
	public static File file(Size size) {
		FileProperty property = new FileProperty("test file", "/test/path/file");
		FileVolume volume = new FileVolume(size, false);

		return new File(property, volume, true);
	}
	
}
