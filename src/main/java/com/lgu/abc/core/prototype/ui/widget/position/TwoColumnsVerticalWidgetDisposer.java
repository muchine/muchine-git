package com.lgu.abc.core.prototype.ui.widget.position;

import com.lgu.abc.core.prototype.ui.widget.domain.WidgetLocation;
import com.lgu.abc.core.prototype.ui.widget.domain.WidgetPosition;

/**
 * This class disposes widgets vertically first on two columns that means left and right. Let's say we have 9 widgets to dispose. 
 * This locates first 5 widgets on the left and the other 4 widgets on the right.
 * @author Sejoon Lim
 * @since 2014. 3. 25.
 *
 */
public class TwoColumnsVerticalWidgetDisposer implements WidgetDisposer {

	@Override
	public WidgetLocation location(int index, int size) {
		return new WidgetLocation(position(index, size), order(index, size));
	}
	
	private WidgetPosition position(int index, int size) {
		if (index < getMaxLeftCategoryIndex(size)) return WidgetPosition.left;
		
		return WidgetPosition.right;
	}

	private int order(int index, int size) {
		if (index < getMaxLeftCategoryIndex(size)) return index + 1;
		
		return index + 1 - getMaxLeftCategoryIndex(size);
	}

	private int getMaxLeftCategoryIndex(int size) {
		int index = size / 2;
		return size % 2 == 0 ? index : index + 1;
	}
	
}
