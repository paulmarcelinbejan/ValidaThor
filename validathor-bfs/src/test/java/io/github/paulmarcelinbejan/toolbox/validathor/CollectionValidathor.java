package io.github.paulmarcelinbejan.toolbox.validathor;

import java.util.Collection;
import java.util.function.Function;

@SuppressWarnings("rawtypes")
public class CollectionValidathor extends ValidathorParametrizedType<Collection> {

	public CollectionValidathor(boolean toValidateParametrizedTypeElements) {
		super(Collection.class, toValidateParametrizedTypeElements);
	}

	@Override
	public boolean isValid(Collection toValidate) {
		return toValidate != null && !toValidate.isEmpty();
	}

	@Override
	public Function<Collection, Collection<?>> parametrizedTypeElementsToValidate() {
		return (collection) -> collection;
	}

}
