package com.lgu.abc.core.web.view;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.u2ware.springfield.controller.EntityHandler;
import com.u2ware.springfield.controller.HandlerMetamodel;

public class ViewResolver<T, Q> {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private final HandlerMetamodel<T, Q> metamodel;
	
	public ViewResolver(HandlerMetamodel<T, Q> metamodel) {
		this.metamodel = metamodel;
	}
	
	public String validate(Model model, Object entity, String commandMethod, BindingResult errors) throws Exception {
		if (errors.hasErrors()) {
			return resolve(model, commandMethod, entity, null, null, null);
		}
		
		return null;
	}

	public String resolve(Model model, String commandMethod,  Object entity, Object query, Pageable pageable, Object queryResult) throws Exception{
		model.addAttribute(EntityHandler.MODEL_ENTITY, entity);
		model.addAttribute(EntityHandler.MODEL_ENTITY_METAMODEL, metamodel);
		
		model.addAttribute(EntityHandler.MODEL_QUERY, query);
		model.addAttribute(EntityHandler.MODEL_QUERY_PAGEABLE, pageable);
		model.addAttribute(EntityHandler.MODEL_QUERY_RESULT, queryResult);

		ServletRequestAttributes attrs = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attrs.getRequest();
		
		String commandMethodAppend = "";

		String commandId = entity != null ? metamodel.getIdentityUri(entity) : "";
		String requestUri = request.getRequestURI();
		String extension = StringUtils.getFilenameExtension(requestUri);
		String viewName = metamodel.getTopLevelMapping()+"/"
				+commandMethod+commandMethodAppend
				+(StringUtils.hasText(extension) ? "."+extension : "");
		
		if(StringUtils.hasText(metamodel.getAttributesCSV())){
			viewName = viewName + "?" + metamodel.getAttributesCSV();
		}
		
		model.addAttribute(EntityHandler.COMMAND_PATH , metamodel.getTopLevelMapping());
		model.addAttribute(EntityHandler.COMMAND_ID , commandId);
		model.addAttribute(EntityHandler.COMMAND_METHOD , commandMethod);
		model.addAttribute(EntityHandler.COMMAND_EXTENSION , extension ==  null ? "" : "."+extension);
		model.addAttribute(EntityHandler.COMMAND_VIEW , viewName);

		return viewName;
	}
	
	public String resolveEntities(Model model, String commandMethod, List<T> entities, Q query, Pageable pageable, Object queryResult) 
			throws Exception {

		model.addAttribute(EntityHandler.MODEL_ENTITY, entities);
		model.addAttribute(EntityHandler.MODEL_ENTITY_METAMODEL, metamodel);

		model.addAttribute(EntityHandler.MODEL_QUERY, query);
		model.addAttribute(EntityHandler.MODEL_QUERY_PAGEABLE, pageable);
		model.addAttribute(EntityHandler.MODEL_QUERY_RESULT, queryResult);

		ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		HttpServletRequest request = attrs.getRequest();

		String commandMethodAppend = "";

		StringBuilder sb = new StringBuilder();
		
		if (entities != null) {
			for (T entity : entities) {
				if (sb.length() > 1) sb.append(",");
				sb.append(metamodel.getIdentityUri(entity));
			}
		}
		
		String commandId = sb.toString();
		String requestUri = request.getRequestURI();
		String extension = StringUtils.getFilenameExtension(requestUri);
		String viewName = metamodel.getTopLevelMapping() + "/" + commandMethod
				+ commandMethodAppend
				+ (StringUtils.hasText(extension) ? "." + extension : "");

		if (StringUtils.hasText(metamodel.getAttributesCSV())) {
			viewName = viewName + "?" + metamodel.getAttributesCSV();
		}

		// logger.info("command_seq: "+metamodel.getSeq());
		logger.info("command_path: " + metamodel.getTopLevelMapping());
		logger.info("command_id: " + commandId);
		logger.info("command_method: " + commandMethod);
		logger.info("command_extension: " + extension);
		logger.info("command_view: " + viewName);

		model.addAttribute(EntityHandler.COMMAND_PATH, metamodel.getTopLevelMapping());
		model.addAttribute(EntityHandler.COMMAND_ID, commandId);
		model.addAttribute(EntityHandler.COMMAND_METHOD, commandMethod);
		model.addAttribute(EntityHandler.COMMAND_EXTENSION, extension == null ? "" : "."
				+ extension);
		model.addAttribute(EntityHandler.COMMAND_VIEW, viewName);

		return viewName;
	}
	
}
