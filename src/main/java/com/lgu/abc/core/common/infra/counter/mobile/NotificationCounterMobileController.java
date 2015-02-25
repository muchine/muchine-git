package com.lgu.abc.core.common.infra.counter.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lgu.abc.core.common.infra.counter.mobile.command.MailCounterMobileCommand;
import com.lgu.abc.core.common.infra.counter.mobile.view.MailCounterMobileView;
import com.lgu.abc.core.common.infra.counter.mobile.view.MainCounterMobileView;
import com.lgu.abc.core.prototype.org.company.Company;
import com.lgu.abc.core.prototype.org.company.support.CompanyFinder;
import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.prototype.org.user.repo.UserRegistry;
import com.lgu.abc.core.prototype.org.user.support.UserFinder;
import com.lgu.abc.core.web.mobile.MobileView;
import com.lgu.abc.core.web.mobile.MobileViewResolver;

@Controller
@RequestMapping("/integration/mobile/common/counter")
public class NotificationCounterMobileController {

	@Autowired
	private MobileViewResolver resolver;
	
	@Autowired
	private UserFinder userFinder;
	
	@Autowired
	private CompanyFinder companyFinder;
	
	@RequestMapping(method = RequestMethod.POST, value = "/main.xml")
	public String getMainCounters(Model model, @RequestParam("EMPID") String empId) {
		User user = UserRegistry.get(empId);
		
		MainCounterMobileView view = new MainCounterMobileView(user);
		return resolver.resolve(model, new MobileView(view));
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/mail.xml")
	@ResponseBody
	public String getMailCounter(Model model, MailCounterMobileCommand command, BindingResult errors) {
		Company company = companyFinder.findByAccessDomain(command.getDomain());
		User user = userFinder.findByLoginId(command.getUserid(), company.getId());
		
		MailCounterMobileView view = new MailCounterMobileView(user);
		return resolver.resolve(new MobileView(view));
	}
	
}
