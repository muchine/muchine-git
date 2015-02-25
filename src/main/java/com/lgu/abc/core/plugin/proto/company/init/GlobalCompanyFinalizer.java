package com.lgu.abc.core.plugin.proto.company.init;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import com.lgu.abc.core.base.plugin.SortableComparator;
import com.lgu.abc.core.prototype.org.company.Company;

@Component
public class GlobalCompanyFinalizer {

	private static final List<CompanyFinalizable> finalizables = new ArrayList<CompanyFinalizable>();
	
	public synchronized void add(CompanyFinalizable initializable) {
		finalizables.add(initializable);
		Collections.sort(finalizables, SortableComparator.instance());
	}
	
	public void finalize(Company company) {
		for (CompanyFinalizable finalizable : finalizables) {
			finalizable.finalize(company);
		}
	}
	
	List<CompanyFinalizable> all() {
		return finalizables;
	}

}
