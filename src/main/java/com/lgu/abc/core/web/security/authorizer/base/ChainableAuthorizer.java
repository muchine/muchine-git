package com.lgu.abc.core.web.security.authorizer.base;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;

import com.lgu.abc.core.web.security.authorizer.Authorizer;

public abstract class ChainableAuthorizer<T, Q> implements Authorizer<T, Q> {

	private final List<Authorizer<T, Q>> chain = new ArrayList<Authorizer<T, Q>>();
	
	public ChainableAuthorizer<T, Q> add(Authorizer<T, Q> authorizer) {
		chain.add(authorizer);
		return this;
	}
	
	@Override
	public final boolean authorizeCreation(T entity) {
		Validate.notEmpty(chain, "authorizer chain is empty.");
		for (Authorizer<T, Q> authorizer : chain) {
			if (!authorizer.authorizeCreation(entity)) return false;
		}
		
		return true;
	}

	@Override
	public boolean authorizeUpdate(T entity) {
		Validate.notEmpty(chain, "authorizer chain is empty.");
		for (Authorizer<T, Q> authorizer : chain) {
			if (!authorizer.authorizeUpdate(entity)) return false;
		}
		
		return true;
	}

	@Override
	public boolean authorizeDeletion(T entity) {
		Validate.notEmpty(chain, "authorizer chain is empty.");
		for (Authorizer<T, Q> authorizer : chain) {
			if (!authorizer.authorizeDeletion(entity)) return false;
		}
		
		return true;
	}

	@Override
	public boolean authorizeEntity(T entity, T found) {
		Validate.notEmpty(chain, "authorizer chain is empty.");
		for (Authorizer<T, Q> authorizer : chain) {
			if (!authorizer.authorizeEntity(entity, found)) return false;
		}
		
		return true;
	}

	@Override
	public boolean authorizeQuery(Q query, Iterable<T> found) {
		Validate.notEmpty(chain, "authorizer chain is empty.");
		for (Authorizer<T, Q> authorizer : chain) {
			if (!authorizer.authorizeQuery(query, found)) return false;
		}
		
		return true;
	}

}
