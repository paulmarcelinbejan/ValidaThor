package io.github.paulmarcelinbejan.toolbox.validathor;

import java.util.Collection;
import java.util.function.Function;

@SuppressWarnings("rawtypes")
public class CollectionValidathor extends ValidathorParameterizedType<Collection> {

	public CollectionValidathor(boolean toValidateParameterizedTypeElements) {
		super(Collection.class, toValidateParameterizedTypeElements);
	}

	@Override
	public boolean isValid(Collection toValidate) {
		return toValidate != null && !toValidate.isEmpty();
	}

	@Override
	public Function<Collection, Collection<?>> parameterizedTypeElementsToValidate() {
		return (collection) -> collection;
	}

}
