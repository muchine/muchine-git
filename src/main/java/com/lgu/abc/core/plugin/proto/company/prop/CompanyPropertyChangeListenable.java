package com.lgu.abc.core.plugin.proto.company.prop;

import com.lgu.abc.core.prototype.org.company.Company;

/**
 * Represents a listener that can receive notification when company properties are changed.
 * @author Sejoon Lim
 * @since 2014. 9. 17.
 */
public interface CompanyPropertyChangeListenable {

	/**
	 * This method is called when a company name is changed.
	 * @param company the company whose name has been changed
	 */
	void nameChanged(Company company);
	
}
