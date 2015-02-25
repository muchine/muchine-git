package com.lgu.abc.core.common.interfaces;

import com.lgu.abc.core.common.NullOwnable;
import com.lgu.abc.core.common.vo.Name;
import com.lgu.abc.core.prototype.org.company.Company;
import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.prototype.workspace.Workspace;


public interface Ownable extends Containable {
	
	public enum OwnerType {
		USER(User.class), WORKSPACE(Workspace.class), COMPANY(Company.class), NULL(NullOwnable.class);
		
		private final Class<?> type;
		
		private OwnerType(Class<?> type) {
			this.type = type;
		}
		
		public Class<?> getClassType() {
			return type;
		}
	}
	
	OwnerType getType();
	
	Name getName();
	
	int order(String actorId);
	
}
