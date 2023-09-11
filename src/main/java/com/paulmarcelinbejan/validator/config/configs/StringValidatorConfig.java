package com.paulmarcelinbejan.validator.config.configs;

import com.paulmarcelinbejan.validator.config.configs.common.NullEmptyValidatorConfig;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * StringValidatorConfig gives you the possibilities to configure the validation of a string: 
 * <br> - canBeNull 
 * <br> - canBeEmpty 
 */
@Getter
@Setter
@SuperBuilder
public class StringValidatorConfig extends NullEmptyValidatorConfig {
	
	/**
	 * new instance with default value
	 */
	public StringValidatorConfig() {
		super();
	}
	
	public StringValidatorConfig(boolean canBeNull, boolean canBeEmpty) {
		super(canBeNull, canBeEmpty);
	}
	
}
