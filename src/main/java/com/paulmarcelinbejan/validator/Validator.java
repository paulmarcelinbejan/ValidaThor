package com.paulmarcelinbejan.validator;

import com.paulmarcelinbejan.validator.config.ValidatorConfig;
import com.paulmarcelinbejan.validator.exception.ValidatorException;

import lombok.NonNull;

public abstract class Validator {
	
	protected Validator(@NonNull final ValidatorConfig validatorConfig) {
		this.validatorConfig = validatorConfig;
	}
	
	protected final ValidatorConfig validatorConfig;
	
	public abstract void isValid(final Object root) throws ValidatorException;
	
}
