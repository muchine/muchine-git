package com.lgu.abc.core.common.infra.file.fixture;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.lgu.abc.core.common.infra.file.File;
import com.lgu.abc.core.common.infra.file.FileQuery;
import com.lgu.abc.core.common.infra.file.domain.FileProperty;
import com.lgu.abc.core.common.infra.file.domain.FileVolume;
import com.lgu.abc.core.common.vo.Size;
import com.lgu.abc.test.support.QueryTester;
import com.lgu.abc.test.support.fixture.AbstractFixtureFactory;

@Component
public class FileFixtureFactory extends AbstractFixtureFactory<File, FileQuery> {

	private static String FILE_PATH	= "test";
	private static String FILE_NAME	= "test.jpg";
	
	@Override
	public File getCreated() {
		FileProperty property = new FileProperty(FILE_NAME, FILE_PATH + "/123");
		FileVolume volume = new FileVolume(new Size(7000), true);
		
		return new File(property, volume, true);
	}

	@Override
	public File getUpdated() {
		File file = getCreated();
		file.getProperty().setPath(FILE_PATH + "/123-updated");
		file.setTemp(false);
		
		return file;
	}

	@Override
	public List<QueryTester<FileQuery>> getQuery() {
		List<QueryTester<FileQuery>> testers = new ArrayList<QueryTester<FileQuery>>();
		
		FileQuery query = new FileQuery();
		query.setOwnable(getSession());
		
		testers.add(new QueryTester<FileQuery>(query, true));
		
		query = new FileQuery();
		query.setPath(getUpdated().getProperty().getPath());
		
		testers.add(new QueryTester<FileQuery>(query, true));
		
		return testers;
	}

}
