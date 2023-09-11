package com.paulmarcelinbejan.toolbox.validathor.base;

import com.paulmarcelinbejan.toolbox.validathor.exception.ValidathorException;
import com.paulmarcelinbejan.toolbox.validathor.registry.ValidathorRegistry;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class ValidathorBase {
	
	protected final ValidathorRegistry validathorRegistry;
	
	protected final boolean collectAllValidationException;
	
	public abstract void isValid(final Object toValidate) throws ValidathorException;
	
}