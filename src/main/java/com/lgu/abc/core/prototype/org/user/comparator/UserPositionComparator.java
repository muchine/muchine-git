package com.lgu.abc.core.prototype.org.user.comparator;

import java.util.Comparator;

import com.lgu.abc.core.prototype.org.user.User;

public class UserPositionComparator implements Comparator<User> {
	
	private static final int NULL_ORDER = 1000;
	
	private static final UserPositionComparator instance = new UserPositionComparator();
	
	private UserPositionComparator() {}
	
	@Override
	public int compare(User o1, User o2) {
		int compared = order(o1) - order(o2);
		if (compared != 0) return compared;
		
		return UserNameComparator.instance().compare(o1, o2);
	}
	
	private int order(User user) {
		return user.getPosition() == null ? NULL_ORDER : user.getPosition().getOrder();		
	}
	
	public static UserPositionComparator instance() {
		return instance;
	}

}
