package springbook.user.dao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.SQLErrorCodes;

import springbook.user.domain.User;

// 1. ResultSet¿« String value
// 2. user¿« setter
public class UserMapper implements RowMapper<User> {	
	private Map<String,String> mappingMap;
	
	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		SQLErrorCodes s;
		return null;
	}
	
	private User getUsersFromProperties(ResultSet rs) throws SQLException {
		Class<User> clazz = User.class;
		Method[] methods = clazz.getDeclaredMethods();
		for(Method method : methods) {
			if(method.getName().startsWith("set")) {
				 String columnValue = mappingMap.get( getFiledNameFromGettersAndSetters(method.getName(),"set") );
				 if(columnValue == null) 
					 continue;
				 try {
					method.invoke(rs.getString(columnValue), null);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}			
		}		
		try {
			return clazz.newInstance();
		}
		catch(InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	private String getFiledNameFromGettersAndSetters(String fullname,String prefix) {
		return fullname.substring(prefix.length()).toLowerCase();
	}
	
	private String getFieldNameFromUsingCamelRules(String name,String prefix) {		
		char[] charArr = name.substring(prefix.length()).toCharArray();
		charArr[0] = toLowerCase(charArr[0]);
		return new String(charArr);
	}
	
	private char toLowerCase(char ch) {
		if(ch >= 'A' && ch <= 'Z') {
			return (char)(ch + 'a' - 'A');
		}
		return ch;
	}
	
}







