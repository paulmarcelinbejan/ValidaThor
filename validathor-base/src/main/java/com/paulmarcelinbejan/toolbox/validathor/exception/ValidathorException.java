package com.paulmarcelinbejan.toolbox.validathor.exception;

import java.text.MessageFormat;

import com.paulmarcelinbejan.toolbox.validathor.Validathor;

import lombok.Getter;

@Getter
public class ValidathorException extends Exception {

	private static final long serialVersionUID = 4000665275004954309L;

	private Validathor<?> causedBy;
	
	public ValidathorException(String message) {
	    super(message);
	}
	
	public ValidathorException(Validathor<?> causedBy, ExceptionMessagePattern messagePattern) {
	    super(messagePattern.value);
	    this.causedBy = causedBy;
	}
	
	public ValidathorException(Validathor<?> causedBy, ExceptionMessagePattern messagePattern, Object... parameters) {
		super(toString(messagePattern, parameters));
		this.causedBy = causedBy;
	}
	
	private static String toString(ExceptionMessagePattern messagePattern, Object... parameters) {
		return MessageFormat.format(messagePattern.value, parameters);
	}
	
}
