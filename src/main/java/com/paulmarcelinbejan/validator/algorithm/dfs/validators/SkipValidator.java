package com.paulmarcelinbejan.validator.algorithm.dfs.validators;

import java.util.Set;

import com.paulmarcelinbejan.validator.config.configs.SkipValidatorConfig;
import com.paulmarcelinbejan.validator.utils.ValidatorUtils;

public class SkipValidator {
	
	private final Set<Class<?>> skipClasses;
	private final Set<String> skipPackages;
	
	public SkipValidator(final SkipValidatorConfig skipValidatorConfig) {
		this.skipClasses = skipValidatorConfig.getSkipClasses();
		this.skipPackages = skipValidatorConfig.getSkipPackages();
	}
	
	public boolean execute(final Class<?> toValidateClass) {
		return (skipClass(toValidateClass) || skipPackage(toValidateClass));
	}
	
	private boolean skipClass(final Class<?> toValidateClass) {
		return skipClasses.contains(toValidateClass);
	}
	
	private boolean skipPackage(final Class<?> toValidateClass) {
		String toValidatePackageName = toValidateClass.getPackage().getName();
		return ValidatorUtils.isContainedInSet(skipPackages, toValidatePackageName);
	}
	
}
