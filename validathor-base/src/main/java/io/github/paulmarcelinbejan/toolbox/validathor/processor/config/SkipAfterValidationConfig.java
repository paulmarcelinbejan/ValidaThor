package io.github.paulmarcelinbejan.toolbox.validathor.processor.config;

import java.util.HashSet;
import java.util.Set;

import io.github.paulmarcelinbejan.toolbox.validathor.enums.PackagesEnum;
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
 * <br>
 * <br>For this reason the "java" package is skipped by default.
 */
@Getter
@Setter
@SuperBuilder
public class SkipAfterValidationConfig {

	@NonNull
	private Set<Class<?>> afterValidationSkipClasses;
	
	@NonNull
	private Set<String> afterValidationSkipPackages;
	
	/**
	 * new instance with default value
	 * <br>validateThenSkipPackages with java package
	 */
	public SkipAfterValidationConfig() {
		afterValidationSkipClasses = new HashSet<>();
		afterValidationSkipPackages = new HashSet<>();
		afterValidationSkipPackages.add(PackagesEnum.JAVA.value);
	}
	
	public SkipAfterValidationConfig(
			Set<Class<?>> afterValidationSkipClasses,
			Set<String> afterValidationSkipPackages) {
		this.afterValidationSkipClasses = afterValidationSkipClasses;
		this.afterValidationSkipPackages = afterValidationSkipPackages;
		this.afterValidationSkipPackages.add(PackagesEnum.JAVA.value);
	}
	
	@java.lang.SuppressWarnings("all")
	public static abstract class SkipAfterValidationConfigBuilder<C extends SkipAfterValidationConfig, B extends SkipAfterValidationConfig.SkipAfterValidationConfigBuilder<C, B>> {
	
		public B withDefault() {
			withDefaultAfterValidationSkipClasses();
			withDefaultAfterValidationSkipPackages();
			return self();
		}
		
		public B withDefaultAfterValidationSkipClasses() {
			this.afterValidationSkipClasses = new HashSet<>();
			return self();
		}
		
		public B withDefaultAfterValidationSkipPackages() {
			this.afterValidationSkipPackages = new HashSet<>();
			return self();
		}
		
	}
	
}
