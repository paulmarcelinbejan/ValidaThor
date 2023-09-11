package com.paulmarcelinbejan.validator.algorithm.dfs.validators.factory;

import com.paulmarcelinbejan.validator.algorithm.dfs.validators.CollectionValidator;
import com.paulmarcelinbejan.validator.algorithm.dfs.validators.MapValidator;
import com.paulmarcelinbejan.validator.algorithm.dfs.validators.ObjectValidator;
import com.paulmarcelinbejan.validator.algorithm.dfs.validators.SkipValidator;
import com.paulmarcelinbejan.validator.algorithm.dfs.validators.StringValidator;
import com.paulmarcelinbejan.validator.algorithm.dfs.validators.ValidateThenSkipValidator;
import com.paulmarcelinbejan.validator.algorithm.dfs.validators.base.FieldValidator;
import com.paulmarcelinbejan.validator.algorithm.dfs.validators.base.ParentValidator;
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
