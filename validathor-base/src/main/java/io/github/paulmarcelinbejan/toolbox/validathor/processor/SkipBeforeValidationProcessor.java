package io.github.paulmarcelinbejan.toolbox.validathor.processor;

import java.util.Set;
import java.util.function.Predicate;

import io.github.paulmarcelinbejan.toolbox.validathor.processor.config.SkipBeforeValidationConfig;

/**
 * Processor in charge of skipping classes or classes inside a package before the validation.
 */
public class SkipBeforeValidationProcessor extends SkipProcessor {
	
	public SkipBeforeValidationProcessor() {
		super();
	}
	
	public SkipBeforeValidationProcessor(
			Set<Class<?>> beforeValidationSkipClasses,
			Set<String> beforeValidationSkipPackages) {
		super(beforeValidationSkipClasses, beforeValidationSkipPackages);
	}
	
	public SkipBeforeValidationProcessor(final SkipBeforeValidationConfig skipBeforeValidationConfig) {
		super(skipBeforeValidationConfig.getBeforeValidationSkipClasses(), skipBeforeValidationConfig.getBeforeValidationSkipPackages());
	}
	
	@Override
	protected Predicate<Class<?>> toSkip() {
		return toValidateClass -> skipByClass(toValidateClass) || skipByPackage(toValidateClass);
	}
	
}
