package com.paulmarcelinbejan.validator.config.configs;

import com.paulmarcelinbejan.validator.config.configs.common.ElementsValidatorConfig;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * MapValidatorConfig gives you the possibilities to configure the validation for a Map: 
 * <br> - canBeNull 
 * <br> - canBeEmpty 
 * <br> - validateElements (value of each entry)
 */
@Getter
@Setter
@SuperBuilder
public class MapValidatorConfig extends ElementsValidatorConfig {
	
	/**
	 * new instance with default value
	 */
	public MapValidatorConfig() {
		super();
	}
	
	public MapValidatorConfig(boolean canBeNull, boolean canBeEmpty, boolean validateElements) {
		super(canBeNull, canBeEmpty, validateElements);
	}
	
}