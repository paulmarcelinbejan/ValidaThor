package io.github.paulmarcelinbejan.toolbox.validathor.inspector;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import io.github.paulmarcelinbejan.toolbox.validathor.Validathor;
import io.github.paulmarcelinbejan.toolbox.validathor.ValidathorParameterizedType;
import io.github.paulmarcelinbejan.toolbox.validathor.registry.ValidathorRegistry;

/**
 * InspectorBase
 */
public abstract class InspectorBase {

	protected InspectorBase(ValidathorRegistry registry) {
		this.registry = registry;
		mapValidathors = registry.getValidathors().stream().collect(Collectors.toMap(Validathor::getTypeParameterClass, validathor -> validathor));
		mapValidathorsParameterizedType = registry.getValidathorsParameterizedType().stream().collect(Collectors.toMap(ValidathorParameterizedType::getTypeParameterClass, validathor -> validathor));
	}

	/**
	 * registry
	 */
	protected final ValidathorRegistry registry;
	
	/**
	 * mapValidathors
	 */
	protected final Map<Class<?>, Validathor<?>> mapValidathors;
	
	/**
	 * mapValidathorsParameterizedType
	 */
	protected final Map<Class<?>, ValidathorParameterizedType<?>> mapValidathorsParameterizedType;
	
	/**
	 * cacheMapCompatibleValidathors
	 */
	protected Map<Class<?>, Validathor<?>> cacheMapCompatibleValidathors = new HashMap<>();
	
	/**
	 * cacheMapCompatibleValidathorsParameterizedType
	 */
	protected Map<Class<?>, ValidathorParameterizedType<?>> cacheMapCompatibleValidathorsParameterizedType = new HashMap<>();
	
}
