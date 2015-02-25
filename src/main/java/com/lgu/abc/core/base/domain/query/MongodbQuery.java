package com.lgu.abc.core.base.domain.query;

import org.springframework.data.mongodb.core.query.Query;

public interface MongodbQuery {

	Query build();
	
}
