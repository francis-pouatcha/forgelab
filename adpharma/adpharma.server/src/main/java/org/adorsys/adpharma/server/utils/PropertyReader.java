package org.adorsys.adpharma.server.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class PropertyReader {


	

	public static Object getPropertyValue(Object source, String fieldName) {
		Object  value = null ;
		try {
			Field field = source.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			 value = field.get(source);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return value ;
		
	}
	
	public static <T> void copyFields(T source, T target) throws Exception{
	    Class<?> clazz = source.getClass();

	    for (Field field : clazz.getFields()) {
	        Object value = field.get(source);
	        field.set(target, value);
	    }
	}

	

}
