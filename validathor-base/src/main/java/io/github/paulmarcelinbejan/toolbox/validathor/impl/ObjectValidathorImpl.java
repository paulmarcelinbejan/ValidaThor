package io.github.paulmarcelinbejan.toolbox.validathor.impl;

import io.github.paulmarcelinbejan.toolbox.validathor.AbstractObjectValidathor;

/**
 * A concrete implementation of ObjectValidathor
 * object to validate is valid if not null
 * validate inner fields
 */
public class ObjectValidathorImpl extends AbstractObjectValidathor {

	public ObjectValidathorImpl() {
		super(true);
	}
	
	public ObjectValidathorImpl(boolean validateInnerFields) {
		super(validateInnerFields);
	}

	@Override
	public boolean isValid(Object toValidate) {
		return toValidate != null;
	}

}
