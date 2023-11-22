package io.github.paulmarcelinbejan.toolbox.validathor.impl.string;

import io.github.paulmarcelinbejan.toolbox.validathor.Validathor;

/**
 * A concrete implementation of Validathor for String Class
 * String is valid if not blank (null is also valid)
 */
public class NotBlankStringValidathor extends Validathor<String> {

	public NotBlankStringValidathor() {
		super(String.class);
	}

	@Override
	public boolean isValid(String toValidate) {
		if(toValidate == null) {
			return true;
		}
		return !toValidate.isBlank();
	}

}
