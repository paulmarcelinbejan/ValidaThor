package com.paulmarcelinbejan.toolbox.validathor.processor.config;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class SkipBeforeValidationConfig {
	
	@NonNull
	private Set<Class<?>> skipClasses;
	
	@NonNull
	private Set<String> skipPackages;
	
	/**
	 * new instance with default value
	 */
	public SkipBeforeValidationConfig() {
		skipClasses = new HashSet<>();
		skipPackages = new HashSet<>();
	}
	
	@java.lang.SuppressWarnings("all")
	public static abstract class SkipBeforeValidationConfigBuilder<C extends SkipBeforeValidationConfig, B extends SkipBeforeValidationConfig.SkipBeforeValidationConfigBuilder<C, B>> {
	
		public B withDefault() {
			withDefaultSkipClasses();
			withDefaultSkipPackages();
			return self();
		}
		
		public B withDefaultSkipClasses() {
			this.skipClasses = new HashSet<>();
			return self();
		}
		
		public B withDefaultSkipPackages() {
			this.skipPackages = new HashSet<>();
			return self();
		}
		
	}
	
}
