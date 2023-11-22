package io.github.paulmarcelinbejan.toolbox.validathor.impl.list;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import io.github.paulmarcelinbejan.toolbox.validathor.ValidathorParameterizedType;

/**
 * A concrete implementation of ValidathorParameterizedType for List interface
 * <br> List is valid if not null and not empty
 */
@SuppressWarnings("rawtypes")
public class NotNullAndNotEmptyListValidathor extends ValidathorParameterizedType<List> {

	protected NotNullAndNotEmptyListValidathor(boolean toValidateParameterizedTypeElements) {
		super(List.class, toValidateParameterizedTypeElements);
	}

	@Override
	public Function<List, Collection<?>> parameterizedTypeElementsToValidate() {
		return list -> list;
	}

	@Override
	public boolean isValid(List toValidate) {
		return toValidate != null && !toValidate.isEmpty();
	}

}
