package com.paulmarcelinbejan.validator.config.configs;

import java.util.HashSet;
import java.util.Set;

import com.paulmarcelinbejan.validator.enums.PackagesValidatorConfig;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * ValidateThenSkipValidatorConfig gives you the possibilities to validate first, and then skip: 
 * <br> - specific classes using <b>validateThenSkipClasses</b>
 * <br> - classes present in a package using <b>validateThenSkipPackages</b>
 * <p>
 * Example:
 * <br>BigDecimal is a class that you may want to validate, to be sure that is not null, but you probably want to skip the validation of internal fields.
 */
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
public class ValidateThenSkipValidatorConfig {
	
	@NonNull
	private Set<Class<?>> validateThenSkipClasses;
	
	@NonNull
	private Set<String> validateThenSkipPackages;
	
	/**
	 * new instance with default value
	 */
	public ValidateThenSkipValidatorConfig() {
		validateThenSkipClasses = new HashSet<>();
		validateThenSkipPackages = new HashSet<>();
	}
	
	@java.lang.SuppressWarnings("all")
	public static abstract class ValidateThenSkipValidatorConfigBuilder<C extends ValidateThenSkipValidatorConfig, B extends ValidateThenSkipValidatorConfig.ValidateThenSkipValidatorConfigBuilder<C, B>> {
	
		public B getDefault() {
			getDefaultValidateThenSkipClasses();
			getDefaultValidateThenSkipPackages();
			return self();
		}
		
		public B getDefaultValidateThenSkipClasses() {
			Set<Class<?>> validateThenSkipClasses = new HashSet<>();
			this.validateThenSkipClasses = validateThenSkipClasses;
			return self();
		}
		
		public B getDefaultValidateThenSkipPackages() {
			Set<String> validateThenSkipPackages = new HashSet<>();
			validateThenSkipPackages.add(PackagesValidatorConfig.JAVA.value);
			this.validateThenSkipPackages = validateThenSkipPackages;
			return self();
		}
		
	}
	
}
