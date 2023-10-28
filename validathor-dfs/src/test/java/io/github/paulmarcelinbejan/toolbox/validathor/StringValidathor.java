package io.github.paulmarcelinbejan.toolbox.validathor;

public class StringValidathor extends Validathor<String> {

	public StringValidathor() {
		super(String.class);
	}

	@Override
	public boolean isValid(String toValidate) {
		return toValidate != null && !toValidate.isEmpty();
	}
	
}
