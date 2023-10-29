package io.github.paulmarcelinbejan.toolbox.validathor.processor.config;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * SkipBeforeValidationConfig gives you the possibilities to skip the validation of: 
 * <br> - specific classes using <b>skipClasses</b>
 * <br> - classes present in a package using <b>skipPackages</b>
 */
@Getter
@Setter
@SuperBuilder
public class SkipBeforeValidationConfig {
	
	@NonNull
	private Set<Class<?>> beforeValidationSkipClasses;
	
	@NonNull
	private Set<String> beforeValidationSkipPackages;
	
	/**
	 * new instance with default value
	 */
	public SkipBeforeValidationConfig() {
		beforeValidationSkipClasses = new HashSet<>();
		beforeValidationSkipPackages = new HashSet<>();
	}
	
	public SkipBeforeValidationConfig(
			Set<Class<?>> beforeValidationSkipClasses,
			Set<String> beforeValidationSkipPackages) {
		this.beforeValidationSkipClasses = beforeValidationSkipClasses;
		this.beforeValidationSkipPackages = beforeValidationSkipPackages;
	}
	
	@java.lang.SuppressWarnings("all")
	public static abstract class SkipBeforeValidationConfigBuilder<C extends SkipBeforeValidationConfig, B extends SkipBeforeValidationConfig.SkipBeforeValidationConfigBuilder<C, B>> {
	
		public B withDefault() {
			withDefaultBeforeValidationSkipClasses();
			withDefaultBeforeValidationSkipPackages();
			return self();
		}
		
		public B withDefaultBeforeValidationSkipClasses() {
			this.beforeValidationSkipClasses = new HashSet<>();
			return self();
		}
		
		public B withDefaultBeforeValidationSkipPackages() {
			this.beforeValidationSkipPackages = new HashSet<>();
			return self();
		}
		
	}
	
}
