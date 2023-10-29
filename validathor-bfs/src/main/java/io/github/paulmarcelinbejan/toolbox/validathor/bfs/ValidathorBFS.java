package io.github.paulmarcelinbejan.toolbox.validathor.bfs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

import io.github.paulmarcelinbejan.toolbox.validathor.base.AlgorithmValidathorBase;
import io.github.paulmarcelinbejan.toolbox.validathor.bfs.inspector.InspectorBFS;
import io.github.paulmarcelinbejan.toolbox.validathor.exception.ValidathorException;
import io.github.paulmarcelinbejan.toolbox.validathor.info.Info;
import io.github.paulmarcelinbejan.toolbox.validathor.registry.ValidathorRegistry;
import io.github.paulmarcelinbejan.toolbox.validathor.utils.ValidathorUtils;
import lombok.NonNull;

/**
 * ValidathorBFS uses Breadth-first search to validate the object
 */
public class ValidathorBFS extends AlgorithmValidathorBase {

	public ValidathorBFS(ValidathorRegistry validathorRegistry, boolean collectAllValidationException) {
		super(validathorRegistry, collectAllValidationException);
		inspector = new InspectorBFS(validathorRegistry);
	}
	
	/**
	 * inspector
	 */
	private final InspectorBFS inspector;
	
	@Override
	public void validate(@NonNull Object toValidate) throws ValidathorException {

		List<ValidathorException> exceptions = new ArrayList<>();
		
		// Populate queue with the root object fields
		Queue<Info> queue = ValidathorUtils.getFields(toValidate.getClass())
				.stream()
				.map(field -> ValidathorUtils.buildInfo(toValidate, field))
				.collect(Collectors.toCollection(LinkedList::new));
		
		while (!queue.isEmpty()) {
			
			Info currentlyExploring = queue.remove();
			
			List<Info> toExplore;
			try {
				
				toExplore = inspector.validate(currentlyExploring);
				
				// add the object to explore to the queue
				queue.addAll(toExplore);
				
			} catch (ValidathorException e) {
				if(!collectAllValidationException) {
					throw e;
				}
				exceptions.add(e);
			}
		}
		
		if(!exceptions.isEmpty()) {
	        String errorMessage = exceptions.stream()
	        		.map(ValidathorException::getMessage)
	                .collect(Collectors.joining("\n\n"));
	        throw new ValidathorException(errorMessage);
		}
		
	}

}
