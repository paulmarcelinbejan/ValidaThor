package io.github.paulmarcelinbejan.toolbox.validathor;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

@SuppressWarnings("rawtypes")
public class MapValidathor extends ValidathorParameterizedType<Map> {

	public MapValidathor(boolean toValidateParameterizedTypeElements) {
		super(Map.class, toValidateParameterizedTypeElements);
	}

	@Override
	public boolean isValid(Map toValidate) {
		return toValidate != null && !toValidate.isEmpty();
	}

	@Override
	public Function<Map, Collection<?>> parameterizedTypeElementsToValidate() {
		return (map) -> map.values();
	}

}
