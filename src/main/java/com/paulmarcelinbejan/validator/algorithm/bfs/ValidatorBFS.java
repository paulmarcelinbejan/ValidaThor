package com.paulmarcelinbejan.validator.algorithm.bfs;

import static com.paulmarcelinbejan.validator.enums.InfoType.FIELD;
import static com.paulmarcelinbejan.validator.utils.ValidatorUtils.getFields;
import static com.paulmarcelinbejan.validator.utils.ValidatorUtils.isToValidateClassPrimitive;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

import com.paulmarcelinbejan.validator.Validator;
import com.paulmarcelinbejan.validator.algorithm.bfs.validators.factory.ValidatorFactory;
import com.paulmarcelinbejan.validator.config.ValidatorConfig;
import com.paulmarcelinbejan.validator.entities.FieldInfo;
import com.paulmarcelinbejan.validator.entities.Info;
import com.paulmarcelinbejan.validator.entities.ParentInfo;
import com.paulmarcelinbejan.validator.exception.ValidatorException;
import com.paulmarcelinbejan.validator.utils.ValidatorUtils;

public class ValidatorBFS extends Validator {
	
	private final ValidatorFactory validatorFactory;
	
	public ValidatorBFS(final ValidatorConfig validatorConfig) {
		super(validatorConfig);
		this.validatorFactory = new ValidatorFactory(validatorConfig);
	}
	
	/**
	 * 
	 * Validate output object through BFS (Breadth-first search)
	 * 
	 */
	@Override
	public void isValid(final Object root) throws ValidatorException {
		
		// Populate queue with the root object fields
		Queue<Info> queue = getFields(root.getClass())
				.stream()
				.map(field -> ValidatorUtils.buildFieldInfo(root, field))
				.collect(Collectors.toCollection(LinkedList::new));
		
		while (!queue.isEmpty()) {
			
			Info currentlyExploring = queue.remove();
			
			if(isToValidateClassPrimitive(currentlyExploring)) continue;
			
			List<? extends Info> toExplore;
			if(currentlyExploring.getType() == FIELD) {
				toExplore = validatorFactory.getFieldValidator().validate((FieldInfo) currentlyExploring);
			} else {
				toExplore = validatorFactory.getParentValidator().validate((ParentInfo) currentlyExploring);
			}
	
			// add the object to explore to the queue
			queue.addAll(toExplore);
		}
		
	}
	
}
