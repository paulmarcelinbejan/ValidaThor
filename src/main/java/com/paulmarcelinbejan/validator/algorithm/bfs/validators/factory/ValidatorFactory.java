package com.paulmarcelinbejan.validator.algorithm.bfs.validators.factory;

import com.paulmarcelinbejan.validator.algorithm.bfs.validators.CollectionValidator;
import com.paulmarcelinbejan.validator.algorithm.bfs.validators.MapValidator;
import com.paulmarcelinbejan.validator.algorithm.bfs.validators.ObjectValidator;
import com.paulmarcelinbejan.validator.algorithm.bfs.validators.SkipValidator;
import com.paulmarcelinbejan.validator.algorithm.bfs.validators.StringValidator;
import com.paulmarcelinbejan.validator.algorithm.bfs.validators.ValidateThenSkipValidator;
import com.paulmarcelinbejan.validator.algorithm.bfs.validators.base.FieldValidator;
import com.paulmarcelinbejan.validator.algorithm.bfs.validators.base.ParentValidator;
import com.paulmarcelinbejan.validator.config.ValidatorConfig;

import lombok.Getter;

@Getter
public class ValidatorFactory {
	
	public ValidatorFactory(final ValidatorConfig validatorConfig) {
		
		skipValidator = new SkipValidator(validatorConfig.getSkipValidatorConfig());
		validateThenSkipValidator = new ValidateThenSkipValidator(validatorConfig.getValidateThenSkipValidatorConfig());
		
		stringValidator = new StringValidator(validatorConfig.getStringValidatorConfig());
		collectionValidator = new CollectionValidator(validatorConfig.getCollectionValidatorConfig());
		mapValidator = new MapValidator(validatorConfig.getMapValidatorConfig());
		objectValidator = new ObjectValidator(validatorConfig.getObjectValidatorConfig(), skipValidator, validateThenSkipValidator);
		
		fieldValidator = new FieldValidator(stringValidator, collectionValidator, mapValidator, objectValidator);
		parentValidator = new ParentValidator(validatorConfig.getObjectValidatorConfig(), fieldValidator, skipValidator, validateThenSkipValidator);
		
	}
	
	private ParentValidator parentValidator;
	private FieldValidator fieldValidator;
	private ObjectValidator objectValidator;
	private StringValidator stringValidator;
	private CollectionValidator collectionValidator;
	private MapValidator mapValidator;
	private SkipValidator skipValidator;
	private ValidateThenSkipValidator validateThenSkipValidator;
	
}
