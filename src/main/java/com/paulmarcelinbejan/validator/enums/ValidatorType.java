package com.paulmarcelinbejan.validator.enums;

import java.util.Collection;
import java.util.Map;

public enum ValidatorType {

	STRING,
	COLLECTION, 
	MAP,
	OBJECT;
	
	public static ValidatorType getValidatorType(final Class<?> fieldClass) {
		if(String.class.equals(fieldClass)) {
			return STRING;
		}
		if(Collection.class.isAssignableFrom(fieldClass)) {
			return COLLECTION;
		}
		if(Map.class.isAssignableFrom(fieldClass)) {
			return MAP;
		}
		return OBJECT;
	}
	
}
