package com.lgu.abc.core.base.repository.vo;

import org.apache.commons.lang.Validate;

import lombok.Data;
import lombok.Getter;

public @Data class Rearrangement {

	public enum Direction {
		BACKWARD("+"), FORWARD("-");

		@Getter
		private final String value;
		
		private Direction(String value) {
			this.value = value;
		}
	}
	
	private final Direction direction;
	
	private final RearrangementPosition position;
	
	public Rearrangement(Direction direction, int from, int to) {
		Validate.isTrue(from != to, "new position is equal to previous one.");
		
		this.direction = direction;
		this.position = new RearrangementPosition(from, to);
	}
	
	public Rearrangement(int from, int to) {
		Validate.isTrue(from >= 0, "from must be greater than or equal to 0.");
		Validate.isTrue(to >= 0, "to must be greater than or equal to 0.");
		Validate.isTrue(from != to, "new position is equal to previous one. previous: " + from + " to: " + to);
		
		/*
		 * If from is 6 and to is 3, elements locating 3 to 5 should move backward that means increasing 1.
		 * On the other hand if from 2 and to 7, elements 3 to 7 should move forward, i.e decreasing 1.
		 */
		if (from < to) {
			this.direction = Direction.FORWARD;
			this.position = new RearrangementPosition(from + 1, to);
		}
		else {
			this.direction = Direction.BACKWARD;
			this.position = new RearrangementPosition(to, from - 1);
		}
	}
	
}
