package com.paulmarcelinbejan.validator.config;

import com.paulmarcelinbejan.validator.config.configs.CollectionValidatorConfig;
import com.paulmarcelinbejan.validator.config.configs.MapValidatorConfig;
import com.paulmarcelinbejan.validator.config.configs.ObjectValidatorConfig;
import com.paulmarcelinbejan.validator.config.configs.SkipValidatorConfig;
import com.paulmarcelinbejan.validator.config.configs.StringValidatorConfig;
import com.paulmarcelinbejan.validator.config.configs.ValidateThenSkipValidatorConfig;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * 
 */
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
public class ValidatorConfig {
	
	@NonNull
	private SkipValidatorConfig skipValidatorConfig;
	
	@NonNull
	private ValidateThenSkipValidatorConfig validateThenSkipValidatorConfig;
	
	@NonNull
	private StringValidatorConfig stringValidatorConfig;
	
	@NonNull
	private CollectionValidatorConfig collectionValidatorConfig;
	
	@NonNull
	private MapValidatorConfig mapValidatorConfig;
	
	@NonNull
	private ObjectValidatorConfig objectValidatorConfig;
	
	/**
	 * new instance with default value
	 */
	public ValidatorConfig() {
		skipValidatorConfig = SkipValidatorConfig.builder().getDefault().build();
		validateThenSkipValidatorConfig = ValidateThenSkipValidatorConfig.builder().getDefault().build();
		stringValidatorConfig = StringValidatorConfig.builder().getDefault().build();
		collectionValidatorConfig = CollectionValidatorConfig.builder().getDefault().build();
		mapValidatorConfig = MapValidatorConfig.builder().getDefault().build();
		objectValidatorConfig = ObjectValidatorConfig.builder().getDefault().build();
	}
	
	@java.lang.SuppressWarnings("all")
	public static abstract class ValidatorConfigBuilder<C extends ValidatorConfig, B extends ValidatorConfig.ValidatorConfigBuilder<C, B>> {

		public B getDefault() {
			this.skipValidatorConfig = SkipValidatorConfig.builder().getDefault().build();
			this.validateThenSkipValidatorConfig = ValidateThenSkipValidatorConfig.builder().getDefault().build();
			this.stringValidatorConfig = StringValidatorConfig.builder().getDefault().build();
			this.collectionValidatorConfig = CollectionValidatorConfig.builder().getDefault().build();
			this.mapValidatorConfig = MapValidatorConfig.builder().getDefault().build();
			this.objectValidatorConfig = ObjectValidatorConfig.builder().getDefault().build();
			return self();
		}
		
	}
	
}
