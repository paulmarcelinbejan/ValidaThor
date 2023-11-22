package io.github.paulmarcelinbejan.toolbox.validathor.impl.string;

import io.github.paulmarcelinbejan.toolbox.validathor.Validathor;

/**
 * A concrete implementation of Validathor for String Class
 * String is valid if not empty (null is also valid)
 */
public class NotEmptyStringValidathor extends Validathor<String> {

	public NotEmptyStringValidathor() {
		super(String.class);
	}

	@Override
	public boolean isValid(String toValidate) {
		if(toValidate == null) {
			return true;
		}
		return !toValidate.isEmpty();
	}

}
