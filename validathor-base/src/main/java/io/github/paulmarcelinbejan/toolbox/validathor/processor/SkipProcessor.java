package io.github.paulmarcelinbejan.toolbox.validathor.processor;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Base Skip Processor
 */
public abstract class SkipProcessor {

	/**
	 * A set of classes to be be skipped
	 */
	protected final Set<Class<?>> skipClasses;
	
	/**
	 * A set of packages to be skipped
	 */
	protected final Set<String> skipPackages;
	
	protected SkipProcessor() {
		skipClasses = new HashSet<>();
		skipPackages = new HashSet<>();
	}
	
	protected SkipProcessor(final Set<Class<?>> skipClasses, final Set<String> skipPackages) {
		this.skipClasses = skipClasses;
		this.skipPackages = skipPackages;
	}
	
	/**
	 * execute the predicate toSkip
	 */
	public boolean execute(final Class<?> toValidateClass) {
		return toSkip().test(toValidateClass);
	}
	
	/**
	 * Predicate toSkip
	 */
	protected abstract Predicate<Class<?>> toSkip();
	
	/**
	 * evaluate if the class needs to be skipped based on class
	 */
	protected boolean skipByClass(final Class<?> toValidateClass) {
		return skipClasses.contains(toValidateClass);
	}
	
	/**
	 * evaluate if the class needs to be skipped based on package
	 */
	protected boolean skipByPackage(final Class<?> toValidateClass) {
		Package toValidatePackage = toValidateClass.getPackage();
		if(toValidatePackage == null) return false;
		String toValidatePackageName = toValidatePackage.getName();
		return skipPackages.parallelStream().anyMatch(toValidatePackageName::startsWith);
	}
	
}
