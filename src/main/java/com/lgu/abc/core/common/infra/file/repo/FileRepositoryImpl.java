package com.lgu.abc.core.common.infra.file.repo;

import java.util.Date;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lgu.abc.core.base.repository.AbstractSqlRepository;
import com.lgu.abc.core.common.infra.file.File;
import com.lgu.abc.core.prototype.org.user.User;

@SuppressWarnings("unchecked")
@Repository
public class FileRepositoryImpl extends AbstractSqlRepository<File, String> implements FileRepository {

	private final String batchRemoveStatement;
	
	private final String batchFindStatement;
	
	@Autowired
	public FileRepositoryImpl(SqlSessionTemplate sqlSessionTemplate) {
		super(File.class, sqlSessionTemplate);
		
		this.batchRemoveStatement = getNamespace() + ".batchDeleteAllTempFiles";
		this.batchFindStatement = getNamespace() + ".batchFindAllTempFiles";
	}
	
	@Override
	public void shrink(User user) {
		getTemplate().update(getNamespace() + ".shrink", user.getId());
	}
	
	@Override
	public List<File> getOutdatedFiles(Date datetime) {
		return getTemplate().selectList(batchFindStatement, datetime);
	}

	@Override
	public void removeOutdatedFiles(Date datetime) {
		getTemplate().delete(batchRemoveStatement, datetime);
	}

	@Override
	protected void preHandle(File entity) {
		
	}

}
