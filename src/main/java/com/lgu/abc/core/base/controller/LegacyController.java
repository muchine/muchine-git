package com.lgu.abc.core.base.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

import com.lgu.abc.core.base.operation.OperationRegistry;
import com.lgu.abc.core.base.operation.OperationType;
import com.lgu.abc.core.support.converter.Converter;
import com.lgu.abc.core.support.converter.ExceptionProcessingConverter;
import com.lgu.abc.core.web.session.SessionManager;
import com.u2ware.springfield.controller.EntityHandler;
import com.u2ware.springfield.controller.HandlerMetamodel;
import com.u2ware.springfield.domain.EntityPageImpl;
import com.u2ware.springfield.service.EntityService;

public class LegacyController<T, Q, DT, DQ> extends EntityHandler<T, Q> {
	
	protected OperationRegistry registry;
	
	private final Converter<DT, T> entityConverter;
	
//	private final Converter<DQ, Q> queryConverter;
	
	@Autowired
	private SessionManager sessionManager;

	public LegacyController(EntityService<T, Q> service, HandlerMetamodel<T, Q> metamodel, Converter<DT, T> entityConverter, Converter<DQ, Q> queryConverter) {
		super(service, metamodel);
		
		this.entityConverter = new ExceptionProcessingConverter<DT, T>(entityConverter);
//		this.queryConverter = new ExceptionProcessingConverter<DQ, Q>(queryConverter);
	}
	
	/*
	 * Operation methods
	 */
	public List<OperationType> getSupportOperations() {
		return this.registry.getSupportOperations();
	}

	public boolean isSupportedOperation(OperationType type) {
		return this.registry.canSupport(type);
	}

	protected String resolveViewName(String view, String viewType) throws Exception {
		logger.debug("View name: " + view);
		
		String[] tokens = view.split("\\.");
		view = tokens[0] + "." + viewType;
		
		return view;
	}
	
	@SuppressWarnings("unchecked")
	protected T beanizeEntity(Model model) {
		T bean = this.entityConverter.beanize((DT) model.asMap().get(MODEL_ENTITY));
		model.addAttribute(MODEL_ENTITY, bean);
		
		return bean;
	}
	
	@SuppressWarnings("unchecked")
	protected Iterable<T> beanizeQueryResult(Model model) {
		Iterable<DT> iterable = (Iterable<DT>) model.asMap().get(MODEL_QUERY_RESULT);
		if (iterable == null) return null;
		
		List<T> beans = new ArrayList<T>();
		for (DT e : iterable)
			beans.add(this.entityConverter.beanize(e));
		
		if (iterable instanceof EntityPageImpl) {
			Page<T> page = new EntityPageImpl<T>(beans, (EntityPageImpl<DT>) iterable);
			model.addAttribute(MODEL_QUERY_RESULT, page);
			
			return page;
		}			
		else {
			model.addAttribute(MODEL_QUERY_RESULT, beans);
			return beans;
		}
	}
	
	protected Long actor() {
		return Long.parseLong(sessionManager.getSession().getId());
	}
	
}
