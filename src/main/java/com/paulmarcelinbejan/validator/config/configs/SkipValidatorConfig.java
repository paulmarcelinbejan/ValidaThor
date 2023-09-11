package com.paulmarcelinbejan.validator.config.configs;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * SkipValidatorConfig gives you the possibilities to skip the validation of: 
 * <br> - specific classes using <b>skipClasses</b>
 * <br> - classes present in a package using <b>skipPackages</b>
 */
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
public class SkipValidatorConfig {
	
	@NonNull
	private Set<Class<?>> skipClasses;
	
	@NonNull
	private Set<String> skipPackages;
	
	/**
	 * new instance with default value
	 */
	public SkipValidatorConfig() {
		skipClasses = new HashSet<>();
		skipPackages = new HashSet<>();
	}
	
	@java.lang.SuppressWarnings("all")
	public static abstract class SkipValidatorConfigBuilder<C extends SkipValidatorConfig, B extends SkipValidatorConfig.SkipValidatorConfigBuilder<C, B>> {
	
		public B getDefault() {
			getDefaultSkipClasses();
			getDefaultSkipPackages();
			return self();
		}
		
		public B getDefaultSkipClasses() {
			this.skipClasses = new HashSet<>();
			return self();
		}
		
		public B getDefaultSkipPackages() {
			this.skipPackages = new HashSet<>();
			return self();
		}
		
	}
	
}
