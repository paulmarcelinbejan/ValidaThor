package io.github.paulmarcelinbejan.toolbox.validathor.impl.object;

import io.github.paulmarcelinbejan.toolbox.validathor.AbstractObjectValidathor;

/**
 * A concrete implementation of AbstractObjectValidathor
 * <br> object to validate is valid if not null
 * <br> inner fields must be validated
 */
public class NotNullAndValidateInnerFieldsObjectValidathor extends AbstractObjectValidathor {

	public NotNullAndValidateInnerFieldsObjectValidathor() {
		super(true);
	}

	@Override
	public boolean isValid(Object toValidate) {
		return toValidate != null;
	}

}
