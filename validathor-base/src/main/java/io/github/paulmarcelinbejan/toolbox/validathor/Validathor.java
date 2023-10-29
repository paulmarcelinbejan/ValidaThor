package io.github.paulmarcelinbejan.toolbox.validathor;

import java.util.Collections;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Base Validathor
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Validathor<T> {

	/**
	 * class of the parameter type T
	 */
	private final Class<T> typeParameterClass;
	
	/**
	 * return true if the parameter is valid, false otherwise
	 */
	public abstract boolean isValid(T toValidate);
	
	/**
	 * return the list of fields name that must be skipped from beeing validated
	 */
	public List<String> getFieldsNameToSkip() {
		return Collections.emptyList();
	}
	
}
