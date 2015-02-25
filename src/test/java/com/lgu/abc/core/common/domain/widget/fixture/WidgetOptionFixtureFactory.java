package com.lgu.abc.core.common.domain.widget.fixture;

import java.util.ArrayList;
import java.util.List;

import com.lgu.abc.core.prototype.ui.widget.WidgetOption;
import com.lgu.abc.core.prototype.ui.widget.domain.WidgetCategory;
import com.lgu.abc.core.prototype.ui.widget.domain.WidgetPosition;
import com.lgu.abc.test.support.QueryTester;
import com.lgu.abc.test.support.fixture.AbstractFixtureFactory;

public abstract class WidgetOptionFixtureFactory<T extends WidgetOption, Q extends WidgetOption> extends
		AbstractFixtureFactory<T, Q> {

	@Override
	public T getCreated() {
		T widget = create();
		
		widget.setCategory(WidgetCategory.main);
		widget.setType("approval");
		widget.setItemId("1000");
		widget.setPosition(WidgetPosition.left);
		widget.setOrder(1);
		
		return widget;
	}

	@Override
	public T getUpdated() {
		T widget = getCreated();

		widget.setPosition(WidgetPosition.center);
		widget.setOrder(2);
		
		return widget;
	}
	
	public T getAnotherCreated() {
		T widget = create();

		widget.setCategory(WidgetCategory.mywork);
		widget.setType("profile");
		widget.setPosition(WidgetPosition.right);
		widget.setOrder(1);
		
		return widget;
	}

	@Override
	public List<QueryTester<Q>> getQuery() {
		List<QueryTester<Q>> testers = new ArrayList<QueryTester<Q>>();
		
		@SuppressWarnings("unchecked")
		Q query = (Q) create();
		query.setCategory(getCreated().getCategory());
		
		testers.add(new QueryTester<Q>(query, true));
		return testers;
	}
		
	protected abstract T create();
	
}
