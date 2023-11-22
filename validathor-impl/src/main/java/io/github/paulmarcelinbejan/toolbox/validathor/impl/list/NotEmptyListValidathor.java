package io.github.paulmarcelinbejan.toolbox.validathor.impl.list;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import io.github.paulmarcelinbejan.toolbox.validathor.ValidathorParameterizedType;

/**
 * A concrete implementation of ValidathorParameterizedType for List interface
 * <br> List is valid if not empty (null is also valid)
 */
@SuppressWarnings("rawtypes")
public class NotEmptyListValidathor extends ValidathorParameterizedType<List> {

	public NotEmptyListValidathor(boolean toValidateParameterizedTypeElements) {
		super(List.class, toValidateParameterizedTypeElements);
	}

	@Override
	public Function<List, Collection<?>> parameterizedTypeElementsToValidate() {
		return list -> list;
	}

	@Override
	public boolean isValid(List toValidate) {
		if(toValidate == null) {
			return true;
		}
		return !toValidate.isEmpty();
	}

}
