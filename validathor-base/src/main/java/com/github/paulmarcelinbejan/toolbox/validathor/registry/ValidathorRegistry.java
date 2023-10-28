package com.github.paulmarcelinbejan.toolbox.validathor.registry;

import java.util.ArrayList;
import java.util.List;

import com.github.paulmarcelinbejan.toolbox.validathor.AbstractObjectValidathor;
import com.github.paulmarcelinbejan.toolbox.validathor.Validathor;
import com.github.paulmarcelinbejan.toolbox.validathor.ValidathorParametrizedType;
import com.github.paulmarcelinbejan.toolbox.validathor.processor.SkipAfterValidationProcessor;
import com.github.paulmarcelinbejan.toolbox.validathor.processor.SkipBeforeValidationProcessor;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
public class ValidathorRegistry {

	private final SkipBeforeValidationProcessor skipBeforeValidationProcessor;
	private final SkipAfterValidationProcessor skipAfterValidationProcessor;
	
	/**
	 * Default validathor to be used when there isn't a specific Validathor for the class encountered.
	 */
	private final AbstractObjectValidathor defaultValidathor;
	
	private List<Validathor<?>> validathors = new ArrayList<>();
	private List<ValidathorParametrizedType<?>> validathorsParametrizedType = new ArrayList<>();
	
	/**
	 * If a class doesn't have a match with a Validathor, but there is one compatible, it is possible to use it setting this parameter to true. <br>
	 * Example 1: There is a field of type ArrayList<>, and a Validathor for this class it was not provided, instead, it is present a Validathor for Collection, so ArrayList is assignable from Collection, the Validathor for Collection can be used to validate the ArrayList.
	 * Example 2: There is a field of type Dog, and a Validathor for this class it was not provided, instead, it is present a Validathor for Animal which is the parent class of Dog, the Validathor for Animal can be used to validate the Dog.
	 */
	private final boolean useCompatibleValidathorIfSpecificNotPresent;
	
	public void registerValidathor(Validathor<?> validathor) {
		validathors.add(validathor);
	}
	
	public void registerValidathors(List<Validathor<?>> validathors) {
		this.validathors.addAll(validathors);
	}
	
	public void registerValidathorParametrizedType(ValidathorParametrizedType<?> validathorParametrizedType) {
		validathorsParametrizedType.add(validathorParametrizedType);
	}
	
	public void registerValidathorsParametrizedType(List<ValidathorParametrizedType<?>> validathorsParametrizedType) {
		this.validathorsParametrizedType.addAll(validathorsParametrizedType);
	}
	
	// BUILDER WITH FLUENT API
	
	@ToString
	public abstract static class ValidathorRegistryBuilder<C extends ValidathorRegistry, B extends ValidathorRegistry.ValidathorRegistryBuilder<C, B>> {
		
		private SkipBeforeValidationProcessor skipBeforeValidationProcessor;

		private SkipAfterValidationProcessor skipAfterValidationProcessor;

		private AbstractObjectValidathor defaultValidathor;

		private List<Validathor<?>> validathors = new ArrayList<>();
		
		private List<ValidathorParametrizedType<?>> validathorsParametrizedType = new ArrayList<>();

		private boolean useCompatibleValidathorIfSpecificNotPresent;
		
		protected abstract B self();

		public abstract C build();

		public B registerSkipBeforeValidationProcessor(final SkipBeforeValidationProcessor skipBeforeValidationProcessor) {
			this.skipBeforeValidationProcessor = skipBeforeValidationProcessor;
			return self();
		}

		public B registerSkipAfterValidationProcessor(final SkipAfterValidationProcessor skipAfterValidationProcessor) {
			this.skipAfterValidationProcessor = skipAfterValidationProcessor;
			return self();
		}

		public B registerObjectValidathor(final AbstractObjectValidathor defaultValidathor) {
			this.defaultValidathor = defaultValidathor;
			return self();
		}

		public B registerValidathor(final Validathor<?> validathor) {
			this.validathors.add(validathor);
			return self();
		}

		public B registerValidathors(final List<Validathor<?>> validathors) {
			this.validathors.addAll(validathors);
			return self();
		}

		public B registerValidathorParametrizedType(final ValidathorParametrizedType<?> validathorParametrizedType) {
			this.validathorsParametrizedType.add(validathorParametrizedType);
			return self();
		}
		
		public B registerValidathorsParametrizedType(final List<ValidathorParametrizedType<?>> validathorsParametrizedType) {
			this.validathorsParametrizedType.addAll(validathorsParametrizedType);
			return self();
		}
		
		public B useCompatibleValidathorIfSpecificNotPresent(final boolean useCompatibleValidathorIfSpecificNotPresent) {
			this.useCompatibleValidathorIfSpecificNotPresent = useCompatibleValidathorIfSpecificNotPresent;
			return self();
		}
		
	}
	
	private static final class ValidathorRegistryBuilderImpl extends ValidathorRegistry.ValidathorRegistryBuilder<ValidathorRegistry, ValidathorRegistry.ValidathorRegistryBuilderImpl> {

		private ValidathorRegistryBuilderImpl() {
		
		}

		@Override
		protected ValidathorRegistry.ValidathorRegistryBuilderImpl self() {
			return this;
		}

		@Override
		public ValidathorRegistry build() {
			if(super.skipBeforeValidationProcessor == null) {
				super.skipBeforeValidationProcessor = new SkipBeforeValidationProcessor();
			}
			if(super.skipAfterValidationProcessor == null) {
				super.skipAfterValidationProcessor = new SkipAfterValidationProcessor();
			}
			if(super.defaultValidathor == null) {
				throw new RuntimeException("ObjectValidathor not registered!");
			}
			return new ValidathorRegistry(this);
		}
		
	}

	protected ValidathorRegistry(final ValidathorRegistry.ValidathorRegistryBuilder<?, ?> b) {
		this(b.skipBeforeValidationProcessor, b.skipAfterValidationProcessor, b.defaultValidathor, b.useCompatibleValidathorIfSpecificNotPresent);
		this.validathors = b.validathors;
		this.validathorsParametrizedType = b.validathorsParametrizedType;
	}

	public static ValidathorRegistry.ValidathorRegistryBuilder<?, ?> builder() {
		return new ValidathorRegistry.ValidathorRegistryBuilderImpl();
	}
	
}
