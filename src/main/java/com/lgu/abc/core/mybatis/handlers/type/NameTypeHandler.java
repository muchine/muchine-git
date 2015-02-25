package com.lgu.abc.core.mybatis.handlers.type;

import java.util.Locale;

import org.apache.ibatis.type.MappedTypes;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.common.vo.Name;
import com.lgu.abc.core.support.formatter.NameFormatter;

/**
 * Handles string data for Name object. If data has a couple of columns for
 * multi language support, Name object is mapped to those columns in the level
 * of mapper statement. However, if there is one column for data this handler
 * maps it to Name object based on user language confgiguration.
 * 
 * @author sejoon
 * 
 */
@MappedTypes(Name.class)
@Component
public class NameTypeHandler extends SpringFormatterTypeHandler<Name> {

	public NameTypeHandler() {
		super(NameFormatter.fromLocale(Locale.KOREAN), null);
	}

}
