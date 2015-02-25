package com.lgu.abc.core.common.infra.cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lgu.abc.core.prototype.org.user.User;

@Controller
@RequestMapping("/monitor/cache")
public class CacheMonitoringController {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private static final String VIEW = "/monitor/cache";
	
	@Autowired
	private CacheManager cacheManager;
	
	@RequestMapping(method = RequestMethod.GET, value = "")
	public String view() {
		return VIEW;
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/{name}.json")
	@ResponseBody
	public Object read(Model model, @PathVariable String name, @RequestParam String id) {
		Cache cache = cacheManager.getCache(name);
		return cache.get(id) == null ? null : cache.get(id).get();
	}
	
	@RequestMapping(method = RequestMethod.POST, value="")
	public String find(Model model, @RequestParam String name, @RequestParam("id") String id) {
		Cache cache = cacheManager.getCache(name);
		Object value = cache.get(id) == null ? null : cache.get(id).get();
				
		model.addAttribute("value", (value instanceof User) ? printUser(((User) value)) : value);
		model.addAttribute("name", name);
		model.addAttribute("id", id);
		
		return VIEW;
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/evict")
	public String evict(Model model, @RequestParam String name, @RequestParam("id") String id) {
		Cache cache = cacheManager.getCache(name);
		Object value = cache.get(id) == null ? null : cache.get(id).get();
		cache.evict(id);
		
		model.addAttribute("value", value + " evicted.");
		model.addAttribute("name", name);
		model.addAttribute("id", id);
		
		return VIEW;
	}
	
	private String printUser(User user) {
		return user.toString() + " " + user.getDepartmentContext();
	}
	
}
