package com.paulmarcelinbejan.toolbox.validathor.bfs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

import com.paulmarcelinbejan.toolbox.validathor.base.ValidathorBase;
import com.paulmarcelinbejan.toolbox.validathor.bfs.inspector.InspectorBFS;
import com.paulmarcelinbejan.toolbox.validathor.exception.ValidathorException;
import com.paulmarcelinbejan.toolbox.validathor.info.Info;
import com.paulmarcelinbejan.toolbox.validathor.registry.ValidathorRegistry;
import com.paulmarcelinbejan.toolbox.validathor.utils.ValidathorUtils;

import lombok.NonNull;

public class ValidathorBFS extends ValidathorBase {

	public ValidathorBFS(ValidathorRegistry validathorRegistry, boolean collectAllValidationException) {
		super(validathorRegistry, collectAllValidationException);
		inspector = new InspectorBFS(validathorRegistry);
	}
	
	private final InspectorBFS inspector;
	
	@Override
	public void isValid(@NonNull Object toValidate) throws ValidathorException {

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
