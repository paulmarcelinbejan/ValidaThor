package com.paulmarcelinbejan.validator.algorithm.dfs;

import static com.paulmarcelinbejan.validator.utils.ValidatorUtils.getFields;
import static com.paulmarcelinbejan.validator.utils.ValidatorUtils.isToValidateClassPrimitive;

import java.util.List;
import java.util.stream.Collectors;

import com.paulmarcelinbejan.validator.Validator;
import com.paulmarcelinbejan.validator.algorithm.dfs.validators.factory.ValidatorFactory;
import com.paulmarcelinbejan.validator.config.ValidatorConfig;
import com.paulmarcelinbejan.validator.entities.FieldInfo;
import com.paulmarcelinbejan.validator.exception.ValidatorException;
import com.paulmarcelinbejan.validator.utils.ValidatorUtils;

public class ValidatorDFS extends Validator {
	
	private final ValidatorFactory validatorFactory;
	
	public ValidatorDFS(final ValidatorConfig validatorConfig) {
		super(validatorConfig);
		this.validatorFactory = new ValidatorFactory(validatorConfig);
	}

	/**
	 * 
	 * Validate output object through DFS (Depth-first search)
	 * 
	 */
	@Override
	public void isValid(Object root) throws ValidatorException {

		List<FieldInfo> fieldsToValidate = getFields(root.getClass())
				.stream()
				.map(field -> ValidatorUtils.buildFieldInfo(root, field))
				.collect(Collectors.toList());
		
		for(FieldInfo fieldInfo : fieldsToValidate) {
			
			if(isToValidateClassPrimitive(fieldInfo)) continue;
			
			validatorFactory.getFieldValidator().validate(fieldInfo, validatorFactory.getParentValidator());
			
		}
		
	}
	
}
