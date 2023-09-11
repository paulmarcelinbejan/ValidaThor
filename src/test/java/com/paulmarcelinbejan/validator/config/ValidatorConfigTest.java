package com.paulmarcelinbejan.validator.config;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.paulmarcelinbejan.validator.config.configs.CollectionValidatorConfig;
import com.paulmarcelinbejan.validator.config.configs.MapValidatorConfig;
import com.paulmarcelinbejan.validator.config.configs.ObjectValidatorConfig;
import com.paulmarcelinbejan.validator.config.configs.SkipValidatorConfig;
import com.paulmarcelinbejan.validator.config.configs.StringValidatorConfig;
import com.paulmarcelinbejan.validator.config.configs.ValidateThenSkipValidatorConfig;

class ValidatorConfigTest {
	
	@Test
	void testDefaultValidatorConfigValues() {
		
		ValidatorConfig validatorConfig = ValidatorConfig.builder().getDefault().build();
		SkipValidatorConfig skipValidatorConfig = validatorConfig.getSkipValidatorConfig();
		ValidateThenSkipValidatorConfig validateThenSkipValidatorConfig = validatorConfig.getValidateThenSkipValidatorConfig();
		StringValidatorConfig stringValidatorConfig = validatorConfig.getStringValidatorConfig();
		CollectionValidatorConfig collectionValidatorConfig = validatorConfig.getCollectionValidatorConfig();
		MapValidatorConfig mapValidatorConfig = validatorConfig.getMapValidatorConfig();
		ObjectValidatorConfig objectValidatorConfig = validatorConfig.getObjectValidatorConfig();
		
		assertTrue(skipValidatorConfig.getSkipClasses().isEmpty());
		assertTrue(skipValidatorConfig.getSkipPackages().isEmpty());
		
		assertTrue(validateThenSkipValidatorConfig.getValidateThenSkipClasses().isEmpty());
		assertFalse(validateThenSkipValidatorConfig.getValidateThenSkipPackages().isEmpty());
		
		assertFalse(stringValidatorConfig.isCanBeEmpty());
		assertFalse(stringValidatorConfig.isCanBeNull());
		
		assertFalse(collectionValidatorConfig.isCanBeEmpty());
		assertFalse(collectionValidatorConfig.isCanBeNull());
		assertTrue(collectionValidatorConfig.isValidateElements());
		
		assertFalse(mapValidatorConfig.isCanBeEmpty());
		assertFalse(mapValidatorConfig.isCanBeNull());
		assertTrue(mapValidatorConfig.isValidateElements());
		
		assertFalse(objectValidatorConfig.isCanBeNull());
		assertTrue(objectValidatorConfig.isValidateInnerFields());
		
	}
	
}
