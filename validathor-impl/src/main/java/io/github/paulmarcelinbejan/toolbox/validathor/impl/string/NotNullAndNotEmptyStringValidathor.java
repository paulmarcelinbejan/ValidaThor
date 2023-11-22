package io.github.paulmarcelinbejan.toolbox.validathor.impl.string;

import io.github.paulmarcelinbejan.toolbox.validathor.Validathor;

/**
 * A concrete implementation of Validathor for String Class
 * String is valid if not null and not empty
 */
public class NotNullAndNotEmptyStringValidathor extends Validathor<String> {

	public NotNullAndNotEmptyStringValidathor() {
		super(String.class);
	}

	@Override
	public boolean isValid(String toValidate) {
		return toValidate != null && !toValidate.isEmpty();
	}

}
