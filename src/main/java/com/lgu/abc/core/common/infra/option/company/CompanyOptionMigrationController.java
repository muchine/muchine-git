package com.lgu.abc.core.common.infra.option.company;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.lgu.abc.core.base.domain.action.CompanyManagementEntity;
import com.lgu.abc.core.common.infra.option.company.repo.GenericCompanyOptionRepository;
import com.lgu.abc.core.prototype.org.company.Company;
import com.lgu.abc.core.prototype.org.company.support.CompanyFinder;
import com.lgu.abc.core.prototype.org.user.SystemUser;

@Controller
@RequestMapping("/core/common/option/company")
public class CompanyOptionMigrationController {

	@Autowired
	private SqlSessionTemplate template;
	
	@Autowired
	private CompanyFinder finder;
	
	@Autowired
	private GenericCompanyOptionRepository repository;
	
	@RequestMapping(method = RequestMethod.POST, value = "/migrate")
	@ResponseStatus(HttpStatus.OK)
	public void migrate(@RequestParam String className) throws Exception {
		migrate(Class.forName(className));
	}
	
	public void migrate(Class<?> type) {
		Iterable<Company> companies = finder.findAll();
		
		for (Company company : companies) {
			CompanyManagementEntity found = template.selectOne(type.getCanonicalName() + ".read", company.getId());
			found.setActor(SystemUser.create(company));
			
			List<GenericCompanyOption> options = found.marshall();
			for (GenericCompanyOption option : options) {
				repository.save(option);
			}
		}		
	}
	
}
