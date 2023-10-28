package io.github.paulmarcelinbejan.toolbox.validathor.processor.config;

import java.util.HashSet;
import java.util.Set;

import io.github.paulmarcelinbejan.toolbox.validathor.enums.PackagesEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * SkipAfterValidationConfig gives you the possibilities to validate first, and then skip: 
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
public class SkipAfterValidationConfig {
	
	@NonNull
	private Set<Class<?>> validateThenSkipClasses;
	
	@NonNull
	private Set<String> validateThenSkipPackages;
	
	/**
	 * new instance with default value
	 * <br>validateThenSkipPackages with java package
	 */
	public SkipAfterValidationConfig() {
		validateThenSkipClasses = new HashSet<>();
		validateThenSkipPackages = new HashSet<>();
		validateThenSkipPackages.add(PackagesEnum.JAVA.value);
	}
	
	@java.lang.SuppressWarnings("all")
	public static abstract class SkipAfterValidationConfigBuilder<C extends SkipAfterValidationConfig, B extends SkipAfterValidationConfig.SkipAfterValidationConfigBuilder<C, B>> {
	
		/**
		 * validateThenSkipPackages contains java package
		 */
		public B withDefault() {
			withDefaultValidateThenSkipClasses();
			withDefaultValidateThenSkipPackages();
			return self();
		}
		
		public B withDefaultValidateThenSkipClasses() {
			Set<Class<?>> validateThenSkipClasses = new HashSet<>();
			this.validateThenSkipClasses = validateThenSkipClasses;
			return self();
		}
		
		/**
		 * validateThenSkipPackages contains java package
		 */
		public B withDefaultValidateThenSkipPackages() {
			Set<String> validateThenSkipPackages = new HashSet<>();
			validateThenSkipPackages.add(PackagesEnum.JAVA.value);
			this.validateThenSkipPackages = validateThenSkipPackages;
			return self();
		}
		
	}
	
}
