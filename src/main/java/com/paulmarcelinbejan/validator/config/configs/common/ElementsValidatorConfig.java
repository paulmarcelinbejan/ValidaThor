package com.paulmarcelinbejan.validator.config.configs.common;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public abstract class ElementsValidatorConfig extends NullEmptyValidatorConfig {

	private boolean validateElements;
	
	/**
	 * new instance with default value
	 */
	protected ElementsValidatorConfig() {
		super();
		validateElements = true;
	}
	
	protected ElementsValidatorConfig(boolean canBeNull, boolean canBeEmpty, boolean validateElements) {
		super(canBeNull, canBeEmpty);
		this.validateElements = validateElements;
	}
	
	@java.lang.SuppressWarnings("all")
	public static abstract class ElementsValidatorConfigBuilder<C extends ElementsValidatorConfig, B extends ElementsValidatorConfig.ElementsValidatorConfigBuilder<C, B>> extends NullEmptyValidatorConfig.NullEmptyValidatorConfigBuilder<C, B> {
		
		@Override
		public B getDefault() {
			super.getDefault();
			this.validateElements = true;
			return self();
		}
		
	}
	
}
