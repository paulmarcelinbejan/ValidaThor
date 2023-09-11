package com.paulmarcelinbejan.validator.config.configs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * ObjectValidatorConfig gives you the possibilities to configure the validation for a Non-Specified-Object: 
 * <br> - canBeNull 
 * <br> - validateInnerFields 
 */
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
public class ObjectValidatorConfig {
	
	private boolean canBeNull;
	private boolean validateInnerFields;
	
	/**
	 * new instance with default value
	 */
	public ObjectValidatorConfig() {
		canBeNull = false;
		validateInnerFields = true;
	}
	
	@java.lang.SuppressWarnings("all")
	public static abstract class ObjectValidatorConfigBuilder<C extends ObjectValidatorConfig, B extends ObjectValidatorConfig.ObjectValidatorConfigBuilder<C, B>> {
		
		public B getDefault() {
			this.canBeNull = false;
			this.validateInnerFields = true;
			return self();
		}
		
	}
	
}
