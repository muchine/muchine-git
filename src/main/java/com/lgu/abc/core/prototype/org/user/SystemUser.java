package com.lgu.abc.core.prototype.org.user;

import com.lgu.abc.core.common.configuration.AbcConfig;
import com.lgu.abc.core.common.vo.Name;
import com.lgu.abc.core.prototype.org.company.Company;
import com.lgu.abc.core.prototype.org.user.domain.ProfileImage;

public class SystemUser extends User {
	
	public static final String SYSTEM_USER_ID = "9999999999";
	
	public static final ProfileImage photo = new ProfileImage("0", "/resources/abc/images/uplusbox.png");
	
	// This should be deprecated
	public static final String OLD_SYSTEM_USER_ID = "0";
	
	private static final SystemUser instance = new SystemUser();
	
	private Company company;
	
	private SystemUser() {
		this(Name.fromBundle("common.system.admin.name"));
	}
	
	public SystemUser(Name name) {
		initialize(name, photo);
	}
	
	public SystemUser(Name name, User user) {
		super(user);
		
		initialize(name, photo);
		this.company = user.getCompany();
	}
		
	private void initialize(Name name, ProfileImage photo) {
		setId(SYSTEM_USER_ID);
		setName(name);
		setPhoto(photo);
		setLoaded(true);
	}

	@Override
	public String getText() {
		return getName().getValue(getLocale());
	}
	
	@Override
	public Company getCompany() {
		return company;
	}
	
	@Override
	public String getEmail() {
		AbcConfig configuration = AbcConfig.instance();
		return configuration == null ? "" : configuration.formMailSend().address();
	}

	public static SystemUser instance() {
		return instance;
	}
	
	public static SystemUser notificator() {
		return new SystemUser(Name.fromBundle("org.user.social.feed.system.name"));
	}
	
	public static SystemUser notificator(User user) {
		return new SystemUser(Name.fromBundle("org.user.social.feed.system.name"), user);
	}
	
	public static SystemUser create(Company company) {
		SystemUser user = new SystemUser();
		user.company = company;
		
		return user;
	}

	public static boolean isSystemUser(String userId) {
		return SYSTEM_USER_ID.equals(userId) || OLD_SYSTEM_USER_ID.equals(userId);
	}
	
}
