package com.lgu.abc.core.common.infra.option;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lgu.abc.core.base.operation.OperationType;
import com.lgu.abc.core.common.infra.option.company.GenericCompanyOption;
import com.lgu.abc.core.common.infra.option.company.service.GenericCompanyOptionService;
import com.lgu.abc.core.support.annotation.Operations;
import com.lgu.abc.test.common.base.service.BaseServiceTest;
import com.lgu.abc.test.support.QueryTester;
import com.u2ware.springfield.service.EntityService;

@Operations(type = {
	OperationType.CREATE,
	OperationType.UPDATE,
	OperationType.DELETE,
	OperationType.FIND_FORM
})
public class GenericCompanyOptionServiceTest extends BaseServiceTest<GenericCompanyOption, GenericCompanyOption> {

	@Autowired
	private GenericCompanyOptionService service;
	
	@Override
	protected EntityService<GenericCompanyOption, GenericCompanyOption> initService() {
		return service;
	}

	@Override
	protected GenericCompanyOption getCreated() {
		return new GenericCompanyOption(getSession().getCompany(), "user.test.option", "this is test value.");
	}

	@Override
	protected GenericCompanyOption getUpdated() {
		return new GenericCompanyOption(getSession().getCompany(), "user.test.option", "this is updated value...");
	}

	@Override
	protected List<QueryTester<GenericCompanyOption>> getQuery() {
		List<QueryTester<GenericCompanyOption>> testers = new ArrayList<QueryTester<GenericCompanyOption>>();
		
		GenericCompanyOption query = new GenericCompanyOption();
		query.setOwnable(getSession().getCompany());
		
		testers.add(new QueryTester<GenericCompanyOption>(query, true));
		
		return testers;
	}

}
