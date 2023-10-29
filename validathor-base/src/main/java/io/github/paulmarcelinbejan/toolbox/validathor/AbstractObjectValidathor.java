package io.github.paulmarcelinbejan.toolbox.validathor;

import lombok.Getter;

/**
 * Base Validathor to validate Object, also known as default Validathor.
 * AbstractObjectValidathor will be used whenever a field must be validated but there isn't the specific Validathor class.
 */
@Getter
public abstract class AbstractObjectValidathor extends Validathor<Object> {

	protected final boolean validateInnerFields;
	
	protected AbstractObjectValidathor(boolean validateInnerFields) {
		super(Object.class);
		this.validateInnerFields = validateInnerFields;
	}
	
}
