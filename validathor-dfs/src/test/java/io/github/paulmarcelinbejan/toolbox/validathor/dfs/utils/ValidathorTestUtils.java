package io.github.paulmarcelinbejan.toolbox.validathor.dfs.utils;

import java.util.List;

import io.github.paulmarcelinbejan.toolbox.validathor.Validathor;
import io.github.paulmarcelinbejan.toolbox.validathor.ValidathorParameterizedType;
import io.github.paulmarcelinbejan.toolbox.validathor.dfs.ValidathorDFS;
import io.github.paulmarcelinbejan.toolbox.validathor.exception.ValidathorException;
import io.github.paulmarcelinbejan.toolbox.validathor.impl.ObjectValidathor;
import io.github.paulmarcelinbejan.toolbox.validathor.processor.SkipAfterValidationProcessor;
import io.github.paulmarcelinbejan.toolbox.validathor.processor.SkipBeforeValidationProcessor;
import io.github.paulmarcelinbejan.toolbox.validathor.registry.ValidathorRegistry;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidathorTestUtils {

	/**
	 * Validate object through DFS (Depth-first search).
	 * <br> A default {@link ValidathorRegistry} will be used to validate the object.
	 */
	public static void validateObjectDFS(@NonNull Object root, List<Validathor<?>> validathors, List<ValidathorParameterizedType<?>> validathorsParameterizedType) throws ValidathorException {
		
		ValidathorRegistry registry = ValidathorRegistry
				.builder()
				.registerSkipBeforeValidationProcessor(new SkipBeforeValidationProcessor())
				.registerSkipAfterValidationProcessor(new SkipAfterValidationProcessor())
				.registerObjectValidathor(new ObjectValidathor())
				.registerValidathors(validathors)
				.registerValidathorsParameterizedType(validathorsParameterizedType)
				.useCompatibleValidathorIfSpecificNotPresent(true)
				.build();
		
		validateObjectDFS(root, registry);
		
	}
	
	/**
	 * Validate object through DFS (Depth-first search).
	 * <br> A default {@link ValidathorRegistry} will be used to validate the object.
	 */
	public static void validateObjectDFS(@NonNull Object root, SkipBeforeValidationProcessor skipBeforeValidationProcessor, SkipAfterValidationProcessor skipAfterValidationProcessor, List<Validathor<?>> validathors, List<ValidathorParameterizedType<?>> validathorsParameterizedType) throws ValidathorException {
		
		ObjectValidathor objectValidathor = new ObjectValidathor();
		
		ValidathorRegistry registry = new ValidathorRegistry(skipBeforeValidationProcessor, skipAfterValidationProcessor, objectValidathor);
		
		registry.registerValidathors(validathors);
		registry.registerValidathorsParameterizedType(validathorsParameterizedType);
		registry.setUseCompatibleValidathorIfSpecificNotPresent(true);
		
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
		validathorDFS.validate(root);
	}
	
}
