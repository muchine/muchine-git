package com.lgu.abc.core.plugin.file.impl;

import com.lgu.abc.core.plugin.file.FileClassifiable;
import com.lgu.abc.core.prototype.org.user.User;

/**
 * Company id based file classification policy.
 * @author Sejoon Lim
 * @since 2014. 2. 11.
 *
 */
public class CompanyFileClassifiable implements FileClassifiable {

	@Override
	public String subpath(User user) {
		return user.getCompany().getId() + "/";
	}

}
