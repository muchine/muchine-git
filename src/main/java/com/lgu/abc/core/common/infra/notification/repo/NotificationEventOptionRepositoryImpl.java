package com.lgu.abc.core.common.infra.notification.repo;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lgu.abc.core.base.repository.BaseSqlRepository;
import com.lgu.abc.core.common.infra.notification.NotificationEventOption;

@SuppressWarnings("unchecked")
@Repository
public class NotificationEventOptionRepositoryImpl extends BaseSqlRepository<NotificationEventOption, String> 
		implements NotificationEventOptionRepository {

	@Autowired
	public NotificationEventOptionRepositoryImpl(SqlSessionTemplate sqlSessionTemplate) {
		super(NotificationEventOption.class, sqlSessionTemplate);
	}

}
