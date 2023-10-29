package io.github.paulmarcelinbejan.toolbox.validathor.inspector;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import io.github.paulmarcelinbejan.toolbox.validathor.Validathor;
import io.github.paulmarcelinbejan.toolbox.validathor.ValidathorParametrizedType;
import io.github.paulmarcelinbejan.toolbox.validathor.registry.ValidathorRegistry;

/**
 * InspectorBase
 */
public abstract class InspectorBase {

	protected InspectorBase(ValidathorRegistry registry) {
		this.registry = registry;
		mapValidathors = registry.getValidathors().stream().collect(Collectors.toMap(Validathor::getTypeParameterClass, validathor -> validathor));
		mapValidathorsParametrizedType = registry.getValidathorsParametrizedType().stream().collect(Collectors.toMap(ValidathorParametrizedType::getTypeParameterClass, validathor -> validathor));
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
	 * mapValidathorsParametrizedType
	 */
	protected final Map<Class<?>, ValidathorParametrizedType<?>> mapValidathorsParametrizedType;
	
	/**
	 * cacheMapCompatibleValidathors
	 */
	protected Map<Class<?>, Validathor<?>> cacheMapCompatibleValidathors = new HashMap<>();
	
	/**
	 * cacheMapCompatibleValidathorsParametrizedType
	 */
	protected Map<Class<?>, ValidathorParametrizedType<?>> cacheMapCompatibleValidathorsParametrizedType = new HashMap<>();
	
}
