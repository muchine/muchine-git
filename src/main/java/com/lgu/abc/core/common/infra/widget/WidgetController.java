package com.lgu.abc.core.common.infra.widget;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lgu.abc.core.base.controller.AbstractHomeController;
import com.lgu.abc.core.exception.http.ResourceNotFoundException;
import com.lgu.abc.core.module.Module;
import com.lgu.abc.core.module.ModuleUtils;
import com.lgu.abc.core.plugin.ui.widget.loader.WidgetLoadable;
import com.lgu.abc.core.plugin.ui.widget.loader.WidgetLoadableRegistry;

@Controller
@RequestMapping("/common/widget")
public class WidgetController extends AbstractHomeController implements WidgetLoadableRegistry {
	
	private static final List<WidgetLoadable> loadables = new ArrayList<WidgetLoadable>();
	
	@Override
	public synchronized void add(WidgetLoadable loadable) {
		loadables.add(loadable);
	}
	
	@RequestMapping("/{type}/{itemId}")
	public ModelAndView loadByItemId(@PathVariable String type, @PathVariable String itemId) {
		WidgetLoadable loadable = find(type);
		if (loadable == null) throw new ResourceNotFoundException();
				
		ModelAndView mav = loadable.load(actor(), itemId);
		mav.addObject("widgetName", loadable.name());
		
		return mav;
	}

	private WidgetLoadable find(String type) {
		for (WidgetLoadable loadable : loadables) {
			if (!loadable.id().equals(type)) continue;
			
			Module module = loadable.module(actor().getCompany());
			return ModuleUtils.isEnabled(module) ? loadable : null;
		}
		
		return null;
	}	

}
