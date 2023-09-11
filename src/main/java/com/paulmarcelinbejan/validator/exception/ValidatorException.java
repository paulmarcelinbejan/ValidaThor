package com.paulmarcelinbejan.validator.exception;

import com.paulmarcelinbejan.validator.exception.ExceptionKeys.Placeholder;

import lombok.Getter;

@Getter
public class ValidatorException extends Exception {

	private static final long serialVersionUID = 4322679387724844414L;
	
	private ExceptionKeys key;

	public ValidatorException(Exception e) {
	    super(e);
	}
	
	public ValidatorException(ExceptionKeys key, String name, String value) {
	    super(toString(key, name, value));
	    this.key = key;
	}
	
	private static String toString(ExceptionKeys key, String name, String value) {
		return replacePlaceholder(key.value, name, value);
	}
	
	private static String replacePlaceholder(String exceptionMessage, String name, String value) {
		exceptionMessage = exceptionMessage.replaceAll(Placeholder.FIELD_NAME.value, name);
		exceptionMessage = exceptionMessage.replaceAll(Placeholder.OUTER_VALUE.value, value);
		return exceptionMessage;
	}
	
}
