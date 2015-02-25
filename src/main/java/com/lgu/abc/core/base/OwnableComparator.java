package com.lgu.abc.core.base;

import java.util.Comparator;

import com.lgu.abc.core.base.domain.action.RootEntity;
import com.lgu.abc.core.prototype.org.user.User;

public class OwnableComparator implements Comparator<RootEntity> {

	private final User actor;
	
	public OwnableComparator(User actor) {
		this.actor = actor;
	}
	
	@Override
	public int compare(RootEntity o1, RootEntity o2) {
		String actorId = actor.getId();
		
		int compared = o1.getOwnable().order(actorId) - o2.getOwnable().order(actorId);
		if (compared != 0) return compared;
		
		compared = compareOwnableId(o1, o2);
		if (compared != 0) return compared;
		
		return compareId(o1.getId(), o2.getId());
	}
	
	private int compareOwnableId(RootEntity o1, RootEntity o2) {
		return compareId(o1.getOwnable().getId(), o2.getOwnable().getId()); 
	}
	
	private int compareId(String id1, String id2) {
		int compared = id1.length() - id2.length();
		if (compared != 0) return compared;
		
		return id1.compareTo(id2);
	}
		
}
