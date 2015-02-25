package com.lgu.abc.core.common.code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.common.code.factory.CodeFactory;
import com.lgu.abc.core.common.code.factory.ColorCodeFactory;
import com.lgu.abc.core.common.code.vo.ClassId;
import com.lgu.abc.core.common.code.vo.CodeEntity;
import com.lgu.abc.core.common.vo.Code;
import com.lgu.abc.core.common.vo.Name;

@Component
public class CodeRegistry {
	
	private Log logger = LogFactory.getLog(this.getClass());

	private static final Map<ClassId, List<Code>> registry = new HashMap<ClassId, List<Code>>();
	
	private static final String namespace = CodeRegistry.class.getCanonicalName();
	
	private final SqlSessionTemplate template;
	
	@Autowired
	private CodeRegistry(SqlSessionTemplate template) {
		this.template = template;
		initialize();
	}
	
	private void initialize() {
		Map<String, ClassId> ids = new HashMap<String, ClassId>();
		Map<ClassId, CodeFactory> factories = new HashMap<ClassId, CodeFactory>();
		
		initializeFactories(factories);
		
		Iterable<CodeEntity> found = template.selectList(namespace + ".findAll");
		for (CodeEntity entity : found) {
			ClassId classId = getClassId(entity, ids);			
			List<Code> codes = getCodes(classId);
			
			CodeFactory factory = factories.get(classId);
			if (factory != null) {
				codes.add(factory.create(entity));
			}
			else {
				codes.add(new Code(classId, entity.getCode(), Name.create(entity.getName())));	
			}			
		}
		
		logger.info(registry.size() + " code class has been loaded.");
	}
	
	private ClassId getClassId(CodeEntity entity, Map<String, ClassId> ids) {
		String codeClass = entity.getClassId();
		ClassId classId = ids.get(codeClass);
		
		if (classId == null) {
			classId = new ClassId(codeClass);
			ids.put(codeClass, classId);
		}
		
		return classId;
	}
	
	private List<Code> getCodes(ClassId classId) {
		List<Code> codes = registry.get(classId);
		if (codes == null) {
			codes = new ArrayList<Code>();
			registry.put(classId, codes);
		}
		
		return codes;
	}
	
	private void initializeFactories(Map<ClassId, CodeFactory> factories) {
		factories.put(Color.CLASS, new ColorCodeFactory());
	}
	
	public static List<Code> codes(ClassId classId) {
		return registry.get(classId);
	}
	
	public static Code code(ClassId classId, String code) {
		List<Code> codes = registry.get(classId);
		for (Code c : codes) {
			if (c.getCode().equals(code)) return c;
		}
		
		return null;
	}
	
	public static Name codeName(ClassId classId, String code) {
		List<Code> codes = registry.get(classId);
		for (Code c : codes) {
			if (c.getCode().equals(String.valueOf(code)))
				return c.getName();
		}
		
		return null;
	}
	
}
