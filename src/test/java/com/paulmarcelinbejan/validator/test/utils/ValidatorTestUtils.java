package com.paulmarcelinbejan.validator.test.utils;

import com.paulmarcelinbejan.validator.algorithm.bfs.ValidatorBFS;
import com.paulmarcelinbejan.validator.algorithm.dfs.ValidatorDFS;
import com.paulmarcelinbejan.validator.config.ValidatorConfig;
import com.paulmarcelinbejan.validator.exception.ValidatorException;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidatorTestUtils {

	/**
	 * Validate object through BFS (Breadth-first search).
	 * <br> A default {@link ValidatorConfig} will be used to validate the object.
	 */
	public static void validateObjectBFS(@NonNull Object root) throws ValidatorException {
		ValidatorConfig defaultValidatorConfig = ValidatorConfig.builder().getDefault().build();
		validateObjectBFS(root, defaultValidatorConfig);
	}
	
	/**
	 * Validate object through BFS (Breadth-first search)
	 * <br> Use {@link ValidatorConfig} to:
	 * <br> - validate only the first level, or the entire hierarchy.
	 * <br> - exclude Classes to be validated
	 * <br> - choose what is valid and what is not
	 */
	public static void validateObjectBFS(@NonNull Object root, @NonNull ValidatorConfig validatorConfig) throws ValidatorException {
		ValidatorBFS validatorBFS = new ValidatorBFS(validatorConfig);
		validatorBFS.isValid(root);
	}
	
	/**
	 * Validate object through DFS (Depth-first search).
	 * <br> A default {@link ValidatorConfig} will be used to validate the object.
	 */
	public static void validateObjectDFS(@NonNull Object root) throws ValidatorException {
		ValidatorConfig defaultValidatorConfig = ValidatorConfig.builder().getDefault().build();
		validateObjectDFS(root, defaultValidatorConfig);
	}
	
	/**
	 * Validate object through DFS (Depth-first search)
	 * <br> Use {@link ValidatorConfig} to:
	 * <br> - validate only the first level, or the entire hierarchy.
	 * <br> - exclude Classes to be validated
	 * <br> - choose what is valid and what is not
	 */
	public static void validateObjectDFS(@NonNull Object root, @NonNull ValidatorConfig validatorConfig) throws ValidatorException {
		ValidatorDFS validatorDFS = new ValidatorDFS(validatorConfig);
		validatorDFS.isValid(root);
	}
	
}
