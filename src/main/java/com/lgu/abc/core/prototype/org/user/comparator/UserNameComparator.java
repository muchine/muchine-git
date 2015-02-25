package com.lgu.abc.core.prototype.org.user.comparator;

import java.util.Comparator;

import com.lgu.abc.core.common.vo.comparator.KoreanNameComparator;
import com.lgu.abc.core.prototype.org.user.User;

public class UserNameComparator implements Comparator<User> {

	private static final UserNameComparator instance = new UserNameComparator();
	
	private UserNameComparator() {}
	
	@Override
	public int compare(User o1, User o2) {
		return KoreanNameComparator.instance().compare(o1.getName(), o2.getName());
	}
	
	public static UserNameComparator instance() {
		return instance;
	}

}
