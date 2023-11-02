package io.github.paulmarcelinbejan.toolbox.validathor.processor;

import java.util.Set;
import java.util.function.Predicate;

import io.github.paulmarcelinbejan.toolbox.validathor.enums.PackagesEnum;
import io.github.paulmarcelinbejan.toolbox.validathor.processor.config.SkipAfterValidationConfig;

/**
 * Processor in charge of skipping classes or classes inside a package after the validation.
 * 
 * The skip is intended to avoid validation of inner fields.
 * 
 * For Parametrized Type class, it will skip fields but non necessary the validation of the elements.
 * Validation of elements rely on the property toValidateParametrizedTypeElements of the specific ValidathorParametrizedType.
 */
public class SkipAfterValidationProcessor extends SkipProcessor {
	
	public SkipAfterValidationProcessor() {
		super();
		skipPackages.add(PackagesEnum.JAVA.value);
	}
	
	public SkipAfterValidationProcessor(
			Set<Class<?>> afterValidationSkipClasses,
			Set<String> afterValidationSkipPackages) {
		super(afterValidationSkipClasses, afterValidationSkipPackages);
		skipPackages.add(PackagesEnum.JAVA.value);
	}
	
	public SkipAfterValidationProcessor(final SkipAfterValidationConfig skipAfterValidationConfig) {
		super(skipAfterValidationConfig.getAfterValidationSkipClasses(), skipAfterValidationConfig.getAfterValidationSkipPackages());
		skipPackages.add(PackagesEnum.JAVA.value);
	}
	
	@Override
	protected Predicate<Class<?>> toSkip() {
		return toValidateClass -> skipEnum(toValidateClass) || skipByClass(toValidateClass) || skipByPackage(toValidateClass);
	}
	
	/**
	 * skip enum class after validation
	 */
	private boolean skipEnum(final Class<?> toValidateClass) {
		return toValidateClass.isEnum();
	}
	
}
