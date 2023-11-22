package io.github.paulmarcelinbejan.toolbox.validathor.impl.object;

import io.github.paulmarcelinbejan.toolbox.validathor.AbstractObjectValidathor;

/**
 * A concrete implementation of AbstractObjectValidathor
 * <br> object to validate is valid if not null
 * <br> inner fields doesn't have to be validated
 */
public class NotNullObjectValidathor extends AbstractObjectValidathor {

	public NotNullObjectValidathor() {
		super(false);
	}

	@Override
	public boolean isValid(Object toValidate) {
		return toValidate != null;
	}

}
