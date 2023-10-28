package com.github.paulmarcelinbejan.toolbox.validathor;

import lombok.Getter;

@Getter
public abstract class AbstractObjectValidathor extends Validathor<Object> {

	protected final boolean validateInnerFields;
	
	protected AbstractObjectValidathor(boolean validateInnerFields) {
		super(Object.class);
		this.validateInnerFields = validateInnerFields;
	}
	
}
