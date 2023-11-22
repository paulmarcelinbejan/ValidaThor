package io.github.paulmarcelinbejan.toolbox.validathor.impl.string;

import io.github.paulmarcelinbejan.toolbox.validathor.Validathor;

/**
 * A concrete implementation of Validathor for String Class
 * String is valid if not null
 */
public class NotNullStringValidathor extends Validathor<String> {

	public NotNullStringValidathor() {
		super(String.class);
	}

	@Override
	public boolean isValid(String toValidate) {
		return toValidate != null;
	}

}
