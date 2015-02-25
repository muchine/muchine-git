package com.lgu.abc.core.prototype.ui.widget.position;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import com.lgu.abc.core.prototype.ui.widget.domain.WidgetPosition;
import com.lgu.abc.core.prototype.ui.widget.position.ThreeColumnsVerticalWidgetDisposer;

public class ThreeColumnsVerticalWidgetDisposerTest {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private final ThreeColumnsVerticalWidgetDisposer disposer = new ThreeColumnsVerticalWidgetDisposer(); 
	
	@Test
	public void test4WidgetsDisposition() {
		final Disposition[] dispositions = new Disposition[] {
			new Disposition(WidgetPosition.left, 1),
			new Disposition(WidgetPosition.left, 2),
			new Disposition(WidgetPosition.center, 1),
			new Disposition(WidgetPosition.right, 1)
		};
		
		test(4, dispositions);
	}
	
	@Test
	public void test5WidgetsDisposition() {
		final Disposition[] dispositions = new Disposition[] {
			new Disposition(WidgetPosition.left, 1),
			new Disposition(WidgetPosition.left, 2),
			new Disposition(WidgetPosition.center, 1),
			new Disposition(WidgetPosition.center, 2),
			new Disposition(WidgetPosition.right, 1)
		};
		
		test(5, dispositions);
	}
	
	@Test
	public void test6WidgetsDisposition() {
		final Disposition[] dispositions = new Disposition[] {
			new Disposition(WidgetPosition.left, 1),
			new Disposition(WidgetPosition.left, 2),
			new Disposition(WidgetPosition.center, 1),
			new Disposition(WidgetPosition.center, 2),
			new Disposition(WidgetPosition.right, 1),
			new Disposition(WidgetPosition.right, 2)
		};
		
		test(6, dispositions);
	}
	
	private void test(int size, Disposition[] dispositions) {
		for (int i = 0; i < size; i++) {
			WidgetPosition position = disposer.location(i, size).getPosition();
			int order = disposer.location(i, size).getOrder();
			
			logger.debug("position: " + position + ", order" + order);
			assertThat(position, is(dispositions[i].getPosition()));
			assertThat(order, is(dispositions[i].getOrder()));
		}
	}
	
}
