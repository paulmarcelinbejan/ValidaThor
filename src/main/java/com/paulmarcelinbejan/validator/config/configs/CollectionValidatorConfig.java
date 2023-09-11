package com.paulmarcelinbejan.validator.config.configs;

import com.paulmarcelinbejan.validator.config.configs.common.ElementsValidatorConfig;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * CollectionValidatorConfig gives you the possibilities to configure the validation for a Collection: 
 * <br> - canBeNull 
 * <br> - canBeEmpty 
 * <br> - validateElements
 */
@Getter
@Setter
@SuperBuilder
public class CollectionValidatorConfig extends ElementsValidatorConfig {
	
	/**
	 * new instance with default value
	 */
	public CollectionValidatorConfig() {
		super();
	}
	
	public CollectionValidatorConfig(boolean canBeNull, boolean canBeEmpty, boolean validateElements) {
		super(canBeNull, canBeEmpty, validateElements);
	}
	
}
