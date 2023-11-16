package io.github.paulmarcelinbejan.toolbox.validathor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

@SuppressWarnings("rawtypes")
public class ArrayListValidathor extends ValidathorParameterizedType<ArrayList> {

	public ArrayListValidathor(boolean toValidateParameterizedTypeElements) {
		super(ArrayList.class, toValidateParameterizedTypeElements);
	}

	@Override
	public boolean isValid(ArrayList toValidate) {
		return toValidate != null && !toValidate.isEmpty();
	}

	@Override
	public Function<ArrayList, Collection<?>> parameterizedTypeElementsToValidate() {
		return (arrayList) -> arrayList;
	}

}
