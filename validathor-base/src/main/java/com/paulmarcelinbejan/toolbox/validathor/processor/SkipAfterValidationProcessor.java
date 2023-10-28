package com.paulmarcelinbejan.toolbox.validathor.processor;

import java.util.HashSet;
import java.util.Set;

import com.paulmarcelinbejan.toolbox.validathor.enums.PackagesEnum;
import com.paulmarcelinbejan.toolbox.validathor.processor.config.SkipAfterValidationConfig;

/**
 * Processor in charge of skipping classes or classes inside a package after the validation.
 * 
 * The skip is intended to avoid validation of inner fields.
 * 
 * For Parametrized Type class, it will skip fields but non necessary the validation of the elements.
 * Validation of elements rely on the property toValidateParametrizedTypeElements of the specific ValidathorParametrizedType.
 */
public class SkipAfterValidationProcessor {
	
	private final Set<Class<?>> validateThenSkipClasses;
	private final Set<String> validateThenSkipPackages;
	
	public SkipAfterValidationProcessor() {
		validateThenSkipClasses = new HashSet<>();
		validateThenSkipPackages = new HashSet<>();
		validateThenSkipPackages.add(PackagesEnum.JAVA.value);
	}
	
	public SkipAfterValidationProcessor(final SkipAfterValidationConfig skipAfterValidationConfig) {
		this.validateThenSkipClasses = skipAfterValidationConfig.getValidateThenSkipClasses();
		this.validateThenSkipPackages = skipAfterValidationConfig.getValidateThenSkipPackages();
		validateThenSkipPackages.add(PackagesEnum.JAVA.value);
	}
	
	public boolean execute(final Class<?> toValidateClass) {
		return validateThenSkipEnum(toValidateClass) || validateThenSkipClass(toValidateClass) || validateThenSkipPackage(toValidateClass);
	}
	
	private boolean validateThenSkipEnum(final Class<?> toValidateClass) {
		return toValidateClass.isEnum();
	}
	
	private boolean validateThenSkipClass(final Class<?> toValidateClass) {
		return validateThenSkipClasses.contains(toValidateClass);
	}
	
	private boolean validateThenSkipPackage(final Class<?> toValidateClass) {
		Package toValidatePackage = toValidateClass.getPackage();
		if(toValidatePackage == null) return false;
		String toValidatePackageName = toValidatePackage.getName();
		return validateThenSkipPackages.parallelStream().anyMatch(toValidatePackageName::startsWith);
	}
	
}
