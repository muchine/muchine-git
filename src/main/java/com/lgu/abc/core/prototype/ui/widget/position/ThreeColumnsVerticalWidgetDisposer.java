package com.lgu.abc.core.prototype.ui.widget.position;

import com.lgu.abc.core.prototype.ui.widget.domain.WidgetLocation;
import com.lgu.abc.core.prototype.ui.widget.domain.WidgetPosition;

/**
 * This class disposes widgets vertically first on three columns that means left, center and right. Let's say we have 10 widgets to dispose. 
 * This locates first 4 widgets on the left, next 3 widgets on the center and the others on the right.
 * @author Sejoon Lim
 * @since 2014. 3. 24.
 *
 */
public class ThreeColumnsVerticalWidgetDisposer implements WidgetDisposer {

	@Override
	public WidgetLocation location(int index, int size) {
		return new WidgetLocation(position(index, size), order(index, size));
	}
	
	private WidgetPosition position(int index, int size) {
		if (index < getMaxLeftCategoryIndex(size)) return WidgetPosition.left;
		if (index < getMaxCenterCategoryIndex(size)) return WidgetPosition.center;
		
		return WidgetPosition.right;
	}

	private int order(int index, int size) {
		if (index < getMaxLeftCategoryIndex(size)) return index + 1;
		if (index < getMaxCenterCategoryIndex(size)) return index + 1 - getMaxLeftCategoryIndex(size);
		
		return index + 1 - getMaxCenterCategoryIndex(size);
	}
	
	private int getMaxLeftCategoryIndex(int size) {
		int index = size / 3;
		return size % 3 == 0 ? index : index + 1;
	}
	
	private int getMaxCenterCategoryIndex(int size) {
		int remained = size - getMaxLeftCategoryIndex(size);
		
		int index = getMaxLeftCategoryIndex(size) + remained / 2;
		return remained % 2 == 0 ? index : index + 1;
	}

}
