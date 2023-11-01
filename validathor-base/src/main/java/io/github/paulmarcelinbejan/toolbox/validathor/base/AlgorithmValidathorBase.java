package io.github.paulmarcelinbejan.toolbox.validathor.base;

import io.github.paulmarcelinbejan.toolbox.validathor.exception.ValidathorException;
import io.github.paulmarcelinbejan.toolbox.validathor.registry.ValidathorRegistry;
import lombok.RequiredArgsConstructor;

/**
 * AlgorithmValidathorBase for different Algorithm (BFS, DFS)
 */
@RequiredArgsConstructor
public abstract class AlgorithmValidathorBase {
	
	/**
	 * Configure the validation through ValidathorRegistry
	 */
	protected final ValidathorRegistry validathorRegistry;
	
	/**
	 * configure if an exception must be thrown immediately, or must be collected and throwned at the end of the validation.
	 */
	protected final boolean collectAllValidationException;
	
	/**
	 * validate the object
	 */
	public abstract void validate(final Object toValidate) throws ValidathorException;
	
}
