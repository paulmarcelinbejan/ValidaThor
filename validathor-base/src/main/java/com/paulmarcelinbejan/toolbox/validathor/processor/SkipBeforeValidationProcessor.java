package com.paulmarcelinbejan.toolbox.validathor.processor;

import java.util.HashSet;
import java.util.Set;

import com.paulmarcelinbejan.toolbox.validathor.processor.config.SkipBeforeValidationConfig;

/**
 * Processor in charge of skipping classes or classes inside a package before the validation.
 */
public class SkipBeforeValidationProcessor {

	private final Set<Class<?>> skipClasses;
	private final Set<String> skipPackages;
	
	public SkipBeforeValidationProcessor() {
		skipClasses = new HashSet<>();
		skipPackages = new HashSet<>();
	}
	
	public SkipBeforeValidationProcessor(final SkipBeforeValidationConfig skipBeforeValidationConfig) {
		this.skipClasses = skipBeforeValidationConfig.getSkipClasses();
		this.skipPackages = skipBeforeValidationConfig.getSkipPackages();
	}
	
	public boolean execute(final Class<?> toValidateClass) {
		return (skipClass(toValidateClass) || skipPackage(toValidateClass));
	}
	
	private boolean skipClass(final Class<?> toValidateClass) {
		return skipClasses.contains(toValidateClass);
	}
	
	private boolean skipPackage(final Class<?> toValidateClass) {
		Package toValidatePackage = toValidateClass.getPackage();
		if(toValidatePackage == null) return false;
		String toValidatePackageName = toValidatePackage.getName();
		return skipPackages.parallelStream().anyMatch(toValidatePackageName::startsWith);
	}
	
	
}
