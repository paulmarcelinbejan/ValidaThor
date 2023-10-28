package com.github.paulmarcelinbejan.toolbox.validathor;

import java.time.LocalDate;

public class LocalDateValidathor extends Validathor<LocalDate> {

	public LocalDateValidathor() {
		super(LocalDate.class);
	}

	@Override
	public boolean isValid(LocalDate toValidate) {
		return toValidate != null;
	}
	
}
