package com.lgu.abc.core.base.domain.action.security;

import java.util.Collection;

import com.lgu.abc.core.common.infra.share.domain.Privilege;
import com.lgu.abc.core.common.interfaces.Containable;
import com.lgu.abc.core.prototype.org.Party;

class PrivilegePolicy {

	/**
	 * Calculate a privilege of the given party on multiple containers.
	 * A most privilege principle is applied when two privileges are conflicted. It means that one privilege is "write" and 
	 * the other one is "read", then the party has write privilege because it has more privilege. 
	 * 
	 * @param party the target party to authorize
	 * @param containers the multiple containers to be inspected for the party
	 * @return a privilege of the party on the containers
	 */
	static <T extends Containable> Privilege authorize(Party party, Collection<T> containers) {
		Privilege result = null;
		
		for (T container : containers) {
			Privilege priv = container.authorize(party);
			if (result == null || (priv != null && priv.compareTo(result) > 0)) result = priv;
		}
		
		return result;
	}
	
}
