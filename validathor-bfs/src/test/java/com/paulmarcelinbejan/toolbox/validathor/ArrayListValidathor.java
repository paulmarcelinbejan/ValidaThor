package com.paulmarcelinbejan.toolbox.validathor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

@SuppressWarnings("rawtypes")
public class ArrayListValidathor extends ValidathorParametrizedType<ArrayList> {

	public ArrayListValidathor(boolean toValidateParametrizedTypeElements) {
		super(ArrayList.class, toValidateParametrizedTypeElements);
	}

	@Override
	public boolean isValid(ArrayList toValidate) {
		return toValidate != null && !toValidate.isEmpty();
	}

	@Override
	public Function<ArrayList, Collection<?>> parametrizedTypeElementsToValidate() {
		return (arrayList) -> arrayList;
	}

}
