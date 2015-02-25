package com.lgu.abc.core.common.batch.log.repo;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lgu.abc.core.base.repository.BaseSqlRepository;
import com.lgu.abc.core.common.batch.log.BatchLog;

@SuppressWarnings("unchecked")
@Repository
public class BatchLogRepositoryImpl extends BaseSqlRepository<BatchLog, String> implements BatchLogRepository {

	@Autowired
	public BatchLogRepositoryImpl(SqlSessionTemplate sqlSessionTemplate) {
		super(BatchLog.class, sqlSessionTemplate);
	}

}
