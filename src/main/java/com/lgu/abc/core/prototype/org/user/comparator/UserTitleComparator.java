package com.lgu.abc.core.prototype.org.user.comparator;

import java.util.Comparator;

import com.lgu.abc.core.prototype.org.user.User;

public class UserTitleComparator implements Comparator<User> {

	private static final int NULL_ORDER = 1000;
	
	private static final UserTitleComparator instance = new UserTitleComparator();
	
	private UserTitleComparator() {}
	
	@Override
	public int compare(User o1, User o2) {
		final int compared = order(o1) - order(o2);
		if (compared != 0) return compared;
		
		return UserPositionComparator.instance().compare(o1, o2);
	}
	
	private int order(User user) {
		return user.getTitle() == null ? NULL_ORDER : user.getTitle().getOrder();		
	}
	
	public static UserTitleComparator instance() {
		return instance;
	}

}
