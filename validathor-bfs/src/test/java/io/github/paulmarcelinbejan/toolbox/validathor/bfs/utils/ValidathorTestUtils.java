package io.github.paulmarcelinbejan.toolbox.validathor.bfs.utils;

import java.util.List;

import io.github.paulmarcelinbejan.toolbox.validathor.Validathor;
import io.github.paulmarcelinbejan.toolbox.validathor.ValidathorParameterizedType;
import io.github.paulmarcelinbejan.toolbox.validathor.bfs.ValidathorBFS;
import io.github.paulmarcelinbejan.toolbox.validathor.exception.ValidathorException;
import io.github.paulmarcelinbejan.toolbox.validathor.impl.object.NotNullAndValidateInnerFieldsObjectValidathor;
import io.github.paulmarcelinbejan.toolbox.validathor.processor.SkipAfterValidationProcessor;
import io.github.paulmarcelinbejan.toolbox.validathor.processor.SkipBeforeValidationProcessor;
import io.github.paulmarcelinbejan.toolbox.validathor.registry.ValidathorRegistry;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidathorTestUtils {

	/**
	 * Validate object through BFS (Breadth-first search).
	 * <br> A default {@link ValidathorRegistry} will be used to validate the object.
	 */
	public static void validateObjectBFS(@NonNull Object root, List<Validathor<?>> validathors, List<ValidathorParameterizedType<?>> validathorsParameterizedType) throws ValidathorException {
		
		ValidathorRegistry registry = ValidathorRegistry
				.builder()
				.registerSkipBeforeValidationProcessor(new SkipBeforeValidationProcessor())
				.registerSkipAfterValidationProcessor(new SkipAfterValidationProcessor())
				.registerObjectValidathor(new NotNullAndValidateInnerFieldsObjectValidathor())
				.registerValidathors(validathors)
				.registerValidathorsParameterizedType(validathorsParameterizedType)
				.useCompatibleValidathorIfSpecificNotPresent(true)
				.build();
				
		validateObjectBFS(root, registry);
		
	}
	
	/**
	 * Validate object through BFS (Breadth-first search).
	 */
	public static void validateObjectBFS(@NonNull Object root, SkipBeforeValidationProcessor skipBeforeValidationProcessor, SkipAfterValidationProcessor skipAfterValidationProcessor, List<Validathor<?>> validathors, List<ValidathorParameterizedType<?>> validathorsParameterizedType) throws ValidathorException {
		
		NotNullAndValidateInnerFieldsObjectValidathor objectValidathor = new NotNullAndValidateInnerFieldsObjectValidathor();
		
		ValidathorRegistry registry = new ValidathorRegistry(skipBeforeValidationProcessor, skipAfterValidationProcessor, objectValidathor);
		
		registry.registerValidathors(validathors);
		registry.registerValidathorsParameterizedType(validathorsParameterizedType);
		registry.setUseCompatibleValidathorIfSpecificNotPresent(true);
		
		validateObjectBFS(root, registry);
		
	}
	
	/**
	 * Validate object through BFS (Breadth-first search)
	 * <br> Use {@link ValidathorRegistry} to:
	 * <br> - validate only the first level, or the entire hierarchy.
	 * <br> - exclude Classes to be validated
	 * <br> - choose what is valid and what is not
	 */
	public static void validateObjectBFS(@NonNull Object root, @NonNull ValidathorRegistry validathorRegistry) throws ValidathorException {
		ValidathorBFS validathorBFS = new ValidathorBFS(validathorRegistry, false);
		validathorBFS.validate(root);
	}
	
}
