package com.paulmarcelinbejan.validator.config.configs.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class NullEmptyValidatorConfig {

	protected boolean canBeNull;
	protected boolean canBeEmpty;
	
	/**
	 * new instance with default value
	 */
	protected NullEmptyValidatorConfig() {
		canBeNull = false;
		canBeEmpty = false;
	}
	
	@java.lang.SuppressWarnings("all")
	public static abstract class NullEmptyValidatorConfigBuilder<C extends NullEmptyValidatorConfig, B extends NullEmptyValidatorConfig.NullEmptyValidatorConfigBuilder<C, B>> {
        
		public B getDefault() {
			this.canBeNull = false;
			this.canBeEmpty = false;
			return self();
		}
		
	}
	
}
