package com.lgu.abc.core.prototype.org.user.support;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.lgu.abc.core.base.security.role.company.CompanyRole;
import com.lgu.abc.core.base.utils.IterableUtils;
import com.lgu.abc.core.common.vo.contact.domain.Email;
import com.lgu.abc.core.prototype.org.company.Company;
import com.lgu.abc.core.prototype.org.company.support.CompanyFinder;
import com.lgu.abc.core.prototype.org.user.UserQuery;
import com.u2ware.springfield.service.EntityService;

public abstract class AbstractUserFinder<T> {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	protected final EntityService<T, UserQuery> service;
	
	@Autowired
	private CompanyFinder companyFinder;
		
	protected AbstractUserFinder(EntityService<T, UserQuery> service) {
		this.service = service;
	}
	
	public T findById(String userId) {
		return service.read(create(userId)); 
	}
	
	public T findByLoginId(String loginId, String companyId) {
		UserQueryBuilder builder = new UserQueryBuilder();
		builder
			.company(companyId)
			.loginId(loginId);
		
		Iterable<T> found = service.find(builder.build(), null);
		return IterableUtils.isEmpty(found) ? null : found.iterator().next();
	}
	
	public Iterable<T> findByDepartmentId(String departmentId) {
		UserQueryBuilder builder = new UserQueryBuilder();
		builder.department(departmentId);
		
		return service.find(builder.build(), null);
	}
	
	public Iterable<T> findByCompanyId(String companyId) {
		UserQueryBuilder builder = new UserQueryBuilder();
		builder.company(companyId);
		
		return service.find(builder.build(), null);
	}
	
	public Iterable<T> findByUserIds(List<String> userIds) {
		if (userIds == null || userIds.isEmpty()) return new ArrayList<T>();
		
		UserQueryBuilder builder = new UserQueryBuilder();
		builder.users(userIds);
		
		return service.find(builder.build(), null);
	}
	
	public T findByEmail(Email email) {
		if (StringUtils.isEmpty(email.getDomain())) return null;
		
		Company company = companyFinder.findByPrimaryDomain(email.getDomain());
		if (company == null) return null;
		
		return findByLoginId(email.getName(), company.getId());
	}
	
	public T findByJabberId(String jid) {
		UserQueryBuilder builder = new UserQueryBuilder().jid(jid);
		
		Iterable<T> found = service.find(builder.build(), null);
		return IterableUtils.isEmpty(found) ? null : found.iterator().next();
	}
	
	public Iterable<T> findByOrganization(List<String> userIds, List<String> departmentIds) {
		logger.debug("parameter: userIds = " + userIds + ", departmentIds = " + departmentIds);
		if (CollectionUtils.isEmpty(userIds) && CollectionUtils.isEmpty(departmentIds)) return new ArrayList<T>();	
		
		UserQueryBuilder builder = new UserQueryBuilder()
			.organization(userIds, departmentIds);
		
		return service.find(builder.build(), null);
	}
	
	public Iterable<T> findByCompanyRole(CompanyRole role) {
		UserQueryBuilder builder = new UserQueryBuilder()
			.companyRole(role);
		
		return service.find(builder.build(), null);
	}
		
	public Iterable<T> search(String text, String companyId) {
		UserQueryBuilder builder = new UserQueryBuilder()
			.company(companyId)
			.search(text);
		
		return service.find(builder.build(), null);
	}
	
	public Iterable<T> search(UserSearcher searcher) {
		return service.find(searcher.query(), null);
	}
	
	protected abstract T create(String id);
	
}
