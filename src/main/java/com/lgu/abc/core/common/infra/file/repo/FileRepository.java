package com.lgu.abc.core.common.infra.file.repo;

import java.util.Date;
import java.util.List;

import com.lgu.abc.core.base.repository.BaseRepository;
import com.lgu.abc.core.common.infra.file.File;
import com.lgu.abc.core.prototype.org.user.User;

public interface FileRepository extends BaseRepository<File, String> {

	List<File> getOutdatedFiles(Date datetime);
	
	void removeOutdatedFiles(Date datetime);

	void shrink(User user);
	
}
