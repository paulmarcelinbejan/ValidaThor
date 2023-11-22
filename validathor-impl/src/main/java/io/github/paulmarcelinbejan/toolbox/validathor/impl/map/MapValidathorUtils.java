package io.github.paulmarcelinbejan.toolbox.validathor.impl.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MapValidathorUtils {

	public static Collection<?> keysAndValues(Map<?, ?> map) {
		List<Object> toValidate = new ArrayList<>();
		
		// Add keys
		toValidate.addAll(map.keySet());
		// Add values
		toValidate.addAll(map.values());

		return toValidate;
	}
	
}
