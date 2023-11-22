package io.github.paulmarcelinbejan.toolbox.validathor.impl.map;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

import io.github.paulmarcelinbejan.toolbox.validathor.ValidathorParameterizedType;

/**
 * A concrete implementation of ValidathorParameterizedType for Map interface
 * <br> Map is valid if not null and not empty.
 * <br> If toValidateParameterizedTypeElements is set to true, it will validate only keys.
 */
@SuppressWarnings("rawtypes")
public class NotNullAndNotEmptyAndValidateKeyMapValidathor extends ValidathorParameterizedType<Map> {

	public NotNullAndNotEmptyAndValidateKeyMapValidathor(boolean toValidateParameterizedTypeElements) {
		super(Map.class, toValidateParameterizedTypeElements);
	}

	@Override
	public Function<Map, Collection<?>> parameterizedTypeElementsToValidate() {
		return Map::keySet;
	}

	@Override
	public boolean isValid(Map toValidate) {
		return toValidate != null && !toValidate.isEmpty();
	}

}
