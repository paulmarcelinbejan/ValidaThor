package io.github.paulmarcelinbejan.toolbox.validathor.impl;

import io.github.paulmarcelinbejan.toolbox.validathor.AbstractObjectValidathor;

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
