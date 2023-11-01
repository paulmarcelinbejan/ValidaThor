package io.github.paulmarcelinbejan.toolbox.validathor.registry;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.github.paulmarcelinbejan.toolbox.validathor.AbstractObjectValidathor;
import io.github.paulmarcelinbejan.toolbox.validathor.Validathor;
import io.github.paulmarcelinbejan.toolbox.validathor.ValidathorParametrizedType;
import io.github.paulmarcelinbejan.toolbox.validathor.processor.SkipAfterValidationProcessor;
import io.github.paulmarcelinbejan.toolbox.validathor.processor.SkipBeforeValidationProcessor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The ValidathorRegistry contains all the necessary configuration
 */
@Getter
public class ValidathorRegistry {

	/**
	 * skipBeforeValidationProcessor
	 */
	private final SkipBeforeValidationProcessor skipBeforeValidationProcessor;
	
	/**
	 * skipAfterValidationProcessor
	 */
	private final SkipAfterValidationProcessor skipAfterValidationProcessor;
	
	/**
	 * Default validathor to be used when there isn't a specific Validathor for the class encountered.
	 */
	private final AbstractObjectValidathor defaultValidathor;
	
	/**
	 * Validathors to be used in order to validate specific classes
	 */
	private final List<Validathor<?>> validathors;
	
	/**
	 *  Validathors to be used in order to validate specific classes of Parametrized Type
	 */
	private final List<ValidathorParametrizedType<?>> validathorsParametrizedType;
	
	/**
	 * If a class doesn't have a match with a Validathor, but there is one compatible, it is possible to use it setting this parameter to true. <br>
	 * Example 1: There is a field of type ArrayList<>, and a Validathor for this class it was not provided, instead, it is present a Validathor for Collection, so ArrayList is assignable from Collection, the Validathor for Collection can be used to validate the ArrayList.
	 * Example 2: There is a field of type Dog, and a Validathor for this class it was not provided, instead, it is present a Validathor for Animal which is the parent class of Dog, the Validathor for Animal can be used to validate the Dog.
	 */
	@Setter
	private boolean useCompatibleValidathorIfSpecificNotPresent;
	
	/**
	 * registerValidathor
	 */
	public void registerValidathor(Validathor<?> validathor) {
		validathors.add(validathor);
	}
	
	/**
	 * registerValidathors
	 */
	public void registerValidathors(List<Validathor<?>> validathors) {
		this.validathors.addAll(validathors);
	}
	
	/**
	 * registerValidathorParametrizedType
	 */
	public void registerValidathorParametrizedType(ValidathorParametrizedType<?> validathorParametrizedType) {
		validathorsParametrizedType.add(validathorParametrizedType);
	}
	
	/**
	 * registerValidathorsParametrizedType
	 */
	public void registerValidathorsParametrizedType(List<ValidathorParametrizedType<?>> validathorsParametrizedType) {
		this.validathorsParametrizedType.addAll(validathorsParametrizedType);
	}
	
	// BUILDER WITH FLUENT API
	
	/**
	 * ValidathorRegistryBuilder
	 */
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
			return new ValidathorRegistry(this);
		}
		
	}

	/**
	 * return a new builder
	 */
	public static ValidathorRegistry.ValidathorRegistryBuilder<?, ?> builder() {
		return new ValidathorRegistry.ValidathorRegistryBuilderImpl();
	}
	
	/**
	 * Constructor with Builder
	 */
	protected ValidathorRegistry(final ValidathorRegistry.ValidathorRegistryBuilder<?, ?> b) {
		this(b.skipBeforeValidationProcessor, b.skipAfterValidationProcessor, b.defaultValidathor, b.validathors, b.validathorsParametrizedType, b.useCompatibleValidathorIfSpecificNotPresent);
	}

	/**
	 * RequiredArgsConstructor
	 */
	public ValidathorRegistry(
			SkipBeforeValidationProcessor skipBeforeValidationProcessor,
			SkipAfterValidationProcessor skipAfterValidationProcessor, 
			AbstractObjectValidathor defaultValidathor) {
		this(skipBeforeValidationProcessor, skipAfterValidationProcessor, defaultValidathor, new ArrayList<>(), new ArrayList<>(), false);
	}
	
	/**
	 * AllArgsConstructor
	 */
	public ValidathorRegistry(
			SkipBeforeValidationProcessor skipBeforeValidationProcessor,
			SkipAfterValidationProcessor skipAfterValidationProcessor, 
			AbstractObjectValidathor defaultValidathor,
			List<Validathor<?>> validathors, 
			List<ValidathorParametrizedType<?>> validathorsParametrizedType,
			boolean useCompatibleValidathorIfSpecificNotPresent) {
		this.skipBeforeValidationProcessor = skipBeforeValidationProcessor;
		this.skipAfterValidationProcessor = skipAfterValidationProcessor;
		this.defaultValidathor = defaultValidathor;
		this.validathors = validathors;
		this.validathorsParametrizedType = validathorsParametrizedType;
		this.useCompatibleValidathorIfSpecificNotPresent = useCompatibleValidathorIfSpecificNotPresent;
		validateValidathorRegistry(this);
	}
	
	private static void validateValidathorRegistry(ValidathorRegistry validathorRegistry) {
		
		//validate skip processors
		
		if(validathorRegistry.getSkipBeforeValidationProcessor() == null) {
			throw new RuntimeException("skipBeforeValidationProcessor can not be null!");
		}
		if(validathorRegistry.getSkipAfterValidationProcessor() == null) {
			throw new RuntimeException("skipAfterValidationProcessor can not be null!");
		}
		
		//validate default validathor
		
		if(validathorRegistry.getDefaultValidathor() == null) {
			throw new RuntimeException("defaultValidathor can not be null!");
		}
		
		//validate validathors
		
		if(validathorRegistry.getValidathors() == null) {
			throw new RuntimeException("validathors can not be null!");
		}
		boolean validathorsContainsMoreValidathorsForSameClass = containsMoreValidathorsForSameClass(validathorRegistry.getValidathors());
		if(validathorsContainsMoreValidathorsForSameClass) {
			throw new RuntimeException("validathors cannot contains more than one validathor for the same class!");
		}
		
		//validate validathors parametrized type
		
		if(validathorRegistry.getValidathorsParametrizedType() == null) {
			throw new RuntimeException("validathorsParametrizedType can not be null!");
		}
		boolean validathorsParametrizedTypeContainsMoreValidathorsForSameClass = containsMoreValidathorsForSameClass(validathorRegistry.getValidathorsParametrizedType());
		if(validathorsParametrizedTypeContainsMoreValidathorsForSameClass) {
			throw new RuntimeException("validathorsParametrizedType cannot contains more than one validathor for the same class!");
		}
		
	}
	
	private static <V extends Validathor<?>> boolean containsMoreValidathorsForSameClass(List<V> validathors) {
		boolean containsMoreValidathorsForSameClass = validathors
				.stream()
				.collect(Collectors.groupingBy(Validathor::getTypeParameterClass))
				.values()
				.stream()
				.anyMatch(v -> v.size() > 1);
		return containsMoreValidathorsForSameClass;
	}
	
}
