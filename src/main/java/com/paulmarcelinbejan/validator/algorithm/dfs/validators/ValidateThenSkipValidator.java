package com.paulmarcelinbejan.validator.algorithm.dfs.validators;

import java.util.Set;

import com.paulmarcelinbejan.validator.config.configs.ValidateThenSkipValidatorConfig;
import com.paulmarcelinbejan.validator.utils.ValidatorUtils;

public class ValidateThenSkipValidator {
	
	private final Set<Class<?>> validateThenSkipClasses;
	private final Set<String> validateThenSkipPackages;
	
	public ValidateThenSkipValidator(final ValidateThenSkipValidatorConfig validateThenSkipValidatorConfig) {
		this.validateThenSkipClasses = validateThenSkipValidatorConfig.getValidateThenSkipClasses();
		this.validateThenSkipPackages = validateThenSkipValidatorConfig.getValidateThenSkipPackages();
	}
	
	public boolean execute(final Class<?> toValidateClass) {
		return (validateThenSkipClass(toValidateClass) || validateThenSkipPackage(toValidateClass));
	}
	
	private boolean validateThenSkipClass(final Class<?> toValidateClass) {
		return validateThenSkipClasses.contains(toValidateClass);
	}
	
	private boolean validateThenSkipPackage(final Class<?> toValidateClass) {
		String toValidatePackageName = toValidateClass.getPackage().getName();
		return ValidatorUtils.isContainedInSet(validateThenSkipPackages, toValidatePackageName);
	}
	
}
