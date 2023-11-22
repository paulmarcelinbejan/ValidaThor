package io.github.paulmarcelinbejan.toolbox.validathor.impl.object;

import io.github.paulmarcelinbejan.toolbox.validathor.AbstractObjectValidathor;

/**
 * A concrete implementation of AbstractObjectValidathor
 * <br> object to validate is always valid
 * <br> inner fields must be validated if not null
 */
public class ValidateInnerFieldsObjectValidathor extends AbstractObjectValidathor {

	public ValidateInnerFieldsObjectValidathor() {
		super(true);
	}

	@Override
	public boolean isValid(Object toValidate) {
		return true;
	}

}
