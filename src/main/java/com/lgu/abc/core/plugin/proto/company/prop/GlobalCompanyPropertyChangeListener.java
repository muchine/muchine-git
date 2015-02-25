package com.lgu.abc.core.plugin.proto.company.prop;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.lgu.abc.core.prototype.org.company.Company;

/**
 * Global implementation that contains all local implementation class of each module. The local implementation in each module should 
 * take the reference of this class via Spring bean injection and add itself in order to get company property change event notification. 
 * @author Sejoon Lim
 * @since 2014. 9. 17.
 *
 */
@Component
public class GlobalCompanyPropertyChangeListener implements CompanyPropertyChangeListenable {

	private final List<CompanyPropertyChangeListenable> listenables = new ArrayList<CompanyPropertyChangeListenable>();
	
	/**
	 * Register local implementation to this class.
	 * @param listenable the local implementation of company property change listener
	 */
	public synchronized void add(CompanyPropertyChangeListenable listenable) {
		listenables.add(listenable);
	}

	@Override
	public void nameChanged(Company company) {
		for (CompanyPropertyChangeListenable listenable : listenables) {
			listenable.nameChanged(company);
		}
	}

}
