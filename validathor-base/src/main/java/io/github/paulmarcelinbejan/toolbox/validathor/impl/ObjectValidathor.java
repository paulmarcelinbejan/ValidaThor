package io.github.paulmarcelinbejan.toolbox.validathor.impl;

import io.github.paulmarcelinbejan.toolbox.validathor.AbstractObjectValidathor;

/**
 * A concrete implementation of ObjectValidathor
 * object to validate is valid if not null
 * validate inner fields
 */
public class ObjectValidathor extends AbstractObjectValidathor {

	public ObjectValidathor() {
		super(true);
	}
	
	public ObjectValidathor(boolean validateInnerFields) {
		super(validateInnerFields);
	}

	@Override
	public boolean isValid(Object toValidate) {
		return toValidate != null;
	}

}
