package com.lgu.abc.core.prototype.org.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgu.abc.core.base.service.DomainServiceImpl;
import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.prototype.org.user.UserQuery;
import com.lgu.abc.core.prototype.org.user.repo.UserRepository;

@Service
public class UserServiceImpl extends DomainServiceImpl<User, UserQuery> implements UserService {

	private final UserRepository repository;
	
	@Autowired
	public UserServiceImpl(UserRepository repository) {
		super(repository);
		this.repository = repository;
	}

	@Override
	public User read(String id) {
		return repository.read(id);
	}
	
}
