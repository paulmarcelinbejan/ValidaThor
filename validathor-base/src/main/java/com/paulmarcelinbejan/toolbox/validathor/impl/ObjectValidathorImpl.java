package com.paulmarcelinbejan.toolbox.validathor.impl;

import com.paulmarcelinbejan.toolbox.validathor.AbstractObjectValidathor;

public class ObjectValidathorImpl extends AbstractObjectValidathor {

	public ObjectValidathorImpl() {
		super(true);
	}

	@Override
	public boolean isValid(Object toValidate) {
		return toValidate != null;
	}

}
