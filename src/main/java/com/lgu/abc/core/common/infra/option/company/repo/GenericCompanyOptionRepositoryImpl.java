package com.lgu.abc.core.common.infra.option.company.repo;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lgu.abc.core.base.repository.BaseSqlRepository;
import com.lgu.abc.core.common.infra.option.company.GenericCompanyOption;

@SuppressWarnings("unchecked")
@Repository
public class GenericCompanyOptionRepositoryImpl extends BaseSqlRepository<GenericCompanyOption, String> implements GenericCompanyOptionRepository {

	@Autowired
	public GenericCompanyOptionRepositoryImpl(SqlSessionTemplate sqlSessionTemplate) {
		super(GenericCompanyOption.class, sqlSessionTemplate);
	}

}
