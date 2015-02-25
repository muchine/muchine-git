package com.lgu.abc.core.mybatis.handlers.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

public abstract class AbstractTypeHandler<T> implements TypeHandler<T> {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Override
	public void setParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
		if (parameter == null) 
			ps.setNull(i, Types.NULL);
		else
			ps.setString(i, print(parameter));
	}

	@Override
	public T getResult(ResultSet rs, String columnName) throws SQLException {
		if (rs.getObject(columnName) == null) return null;
		return parseWithExceptionAbstraction(rs.getString(columnName));
	}

	@Override
	public T getResult(ResultSet rs, int columnIndex) throws SQLException {
		if (rs.getObject(columnIndex) == null) return null;
		return parseWithExceptionAbstraction(rs.getString(columnIndex));
	}

	@Override
	public T getResult(CallableStatement cs, int columnIndex) throws SQLException {
		if (cs.getObject(columnIndex) == null) return null;
		return parseWithExceptionAbstraction(cs.getString(columnIndex));
	}
	
	private T parseWithExceptionAbstraction(String text) throws SQLException {
		try {
			return parse(text);
		}
		catch (ParseException e) {
			throw new SQLException(e);
		}
	}
	
	protected abstract String print(T object);
	
	protected abstract T parse(String text) throws ParseException;

}
