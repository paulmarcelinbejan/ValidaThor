package io.github.paulmarcelinbejan.toolbox.validathor;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

@SuppressWarnings("rawtypes")
public class MapValidathor extends ValidathorParametrizedType<Map> {

	public MapValidathor(boolean toValidateParametrizedTypeElements) {
		super(Map.class, toValidateParametrizedTypeElements);
	}

	@Override
	public boolean isValid(Map toValidate) {
		return toValidate != null && !toValidate.isEmpty();
	}

	@Override
	public Function<Map, Collection<?>> parametrizedTypeElementsToValidate() {
		return (map) -> map.values();
	}

}
