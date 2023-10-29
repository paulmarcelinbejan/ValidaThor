package io.github.paulmarcelinbejan.toolbox.validathor.exception;

import java.text.MessageFormat;

import io.github.paulmarcelinbejan.toolbox.validathor.Validathor;
import lombok.Getter;

/**
 * ValidathorException used when validation fails.
 */
@Getter
public class ValidathorException extends Exception {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 4000665275004954309L;

	/**
	 * the Validathor that has caused the exception
	 */
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
	
	/**
	 * Format the message pattern with the parameters
	 */
	private static String toString(ExceptionMessagePattern messagePattern, Object... parameters) {
		return MessageFormat.format(messagePattern.value, parameters);
	}
	
}
