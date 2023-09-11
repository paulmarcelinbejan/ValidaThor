package com.paulmarcelinbejan.validator.algorithm.dfs.validators.base;

import static com.paulmarcelinbejan.validator.enums.ValidatorType.COLLECTION;
import static com.paulmarcelinbejan.validator.enums.ValidatorType.MAP;
import static com.paulmarcelinbejan.validator.enums.ValidatorType.STRING;

import com.paulmarcelinbejan.validator.algorithm.dfs.validators.CollectionValidator;
import com.paulmarcelinbejan.validator.algorithm.dfs.validators.MapValidator;
import com.paulmarcelinbejan.validator.algorithm.dfs.validators.ObjectValidator;
import com.paulmarcelinbejan.validator.algorithm.dfs.validators.StringValidator;
import com.paulmarcelinbejan.validator.entities.FieldInfo;
import com.paulmarcelinbejan.validator.enums.ValidatorType;
import com.paulmarcelinbejan.validator.exception.ValidatorException;

public class FieldValidator {

	private StringValidator stringValidator;
	private CollectionValidator collectionValidator;
	private MapValidator mapValidator;
	private ObjectValidator objectValidator;
	
	public FieldValidator(final StringValidator stringValidator, final CollectionValidator collectionValidator, final MapValidator mapValidator, final ObjectValidator objectValidator) {
		this.stringValidator = stringValidator;
		this.collectionValidator = collectionValidator;
		this.mapValidator = mapValidator;
		this.objectValidator = objectValidator;
	}
	
	public void validate(final FieldInfo fieldInfo, final ParentValidator parentValidator) throws ValidatorException {
		ValidatorType validatorType = ValidatorType.getValidatorType(fieldInfo.getToValidateClass());
		
		if(validatorType == STRING) {
			stringValidator.validate(fieldInfo);
		} else if (validatorType == COLLECTION) {
			collectionValidator.validate(fieldInfo, parentValidator);
		} else if (validatorType == MAP) {
			mapValidator.validate(fieldInfo, parentValidator);
		} else {
			objectValidator.validate(fieldInfo, parentValidator, this);
		}

	}
	
}
