package com.github.paulmarcelinbejan.toolbox.validathor.dfs.utils;

import java.util.List;

import com.github.paulmarcelinbejan.toolbox.validathor.Validathor;
import com.github.paulmarcelinbejan.toolbox.validathor.ValidathorParametrizedType;
import com.github.paulmarcelinbejan.toolbox.validathor.dfs.ValidathorDFS;
import com.github.paulmarcelinbejan.toolbox.validathor.exception.ValidathorException;
import com.github.paulmarcelinbejan.toolbox.validathor.impl.ObjectValidathorImpl;
import com.github.paulmarcelinbejan.toolbox.validathor.processor.SkipAfterValidationProcessor;
import com.github.paulmarcelinbejan.toolbox.validathor.processor.SkipBeforeValidationProcessor;
import com.github.paulmarcelinbejan.toolbox.validathor.registry.ValidathorRegistry;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidathorTestUtils {

	/**
	 * Validate object through DFS (Depth-first search).
	 * <br> A default {@link ValidathorRegistry} will be used to validate the object.
	 */
	public static void validateObjectDFS(@NonNull Object root, List<Validathor<?>> validathors, List<ValidathorParametrizedType<?>> validathorsParametrizedType) throws ValidathorException {
		
		SkipBeforeValidationProcessor skipBeforeValidationProcessor = new SkipBeforeValidationProcessor();
		SkipAfterValidationProcessor skipAfterValidationProcessor = new SkipAfterValidationProcessor();
		ObjectValidathorImpl objectValidathorImpl = new ObjectValidathorImpl();
		
		ValidathorRegistry registry = new ValidathorRegistry(skipBeforeValidationProcessor, skipAfterValidationProcessor, objectValidathorImpl, true);
		
		registry.registerValidathors(validathors);
		registry.registerValidathorsParametrizedType(validathorsParametrizedType);
		
		validateObjectDFS(root, registry);
		
	}
	
	/**
	 * Validate object through DFS (Depth-first search).
	 * <br> A default {@link ValidathorRegistry} will be used to validate the object.
	 */
	public static void validateObjectDFS(@NonNull Object root, SkipBeforeValidationProcessor skipBeforeValidationProcessor, SkipAfterValidationProcessor skipAfterValidationProcessor, List<Validathor<?>> validathors, List<ValidathorParametrizedType<?>> validathorsParametrizedType) throws ValidathorException {
		
		ObjectValidathorImpl objectValidathorImpl = new ObjectValidathorImpl();
		
		ValidathorRegistry registry = new ValidathorRegistry(skipBeforeValidationProcessor, skipAfterValidationProcessor, objectValidathorImpl, true);
		
		registry.registerValidathors(validathors);
		registry.registerValidathorsParametrizedType(validathorsParametrizedType);
		
		validateObjectDFS(root, registry);
		
	}
	
	/**
	 * Validate object through DFS (Depth-first search)
	 * <br> Use {@link ValidathorRegistry} to:
	 * <br> - validate only the first level, or the entire hierarchy.
	 * <br> - exclude Classes to be validated
	 * <br> - choose what is valid and what is not
	 */
	public static void validateObjectDFS(@NonNull Object root, @NonNull ValidathorRegistry validathorRegistry) throws ValidathorException {
		ValidathorDFS validathorDFS = new ValidathorDFS(validathorRegistry, false);
		validathorDFS.isValid(root);
	}
	
}
