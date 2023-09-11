package com.paulmarcelinbejan.toolbox.validathor.registry;

import java.util.ArrayList;
import java.util.List;

import com.paulmarcelinbejan.toolbox.validathor.AbstractObjectValidathor;
import com.paulmarcelinbejan.toolbox.validathor.Validathor;
import com.paulmarcelinbejan.toolbox.validathor.ValidathorParametrizedType;
import com.paulmarcelinbejan.toolbox.validathor.processor.SkipBeforeValidationProcessor;
import com.paulmarcelinbejan.toolbox.validathor.processor.SkipAfterValidationProcessor;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ValidathorRegistry {

	private final SkipBeforeValidationProcessor skipBeforeValidationProcessor;
	private final SkipAfterValidationProcessor skipAfterValidationProcessor;
	
	/**
	 * Default validathor to be used when there isn't a specific Validathor for the class encountered.
	 */
	private final AbstractObjectValidathor defaultValidathor;
	
	/**
	 * If a class doesn't have a match with a Validathor, but there is one compatible, it is possible to use it setting this parameter to true. <br>
	 * Example 1: There is a field of type ArrayList<>, and a Validathor for this class it was not provided, instead, it is present a Validathor for Collection, so ArrayList is assignable from Collection, the Validathor for Collection can be used to validate the ArrayList.
	 * Example 2: There is a field of type Dog, and a Validathor for this class it was not provided, instead, it is present a Validathor for Animal which is the parent class of Dog, the Validathor for Animal can be used to validate the Dog.
	 */
	private final boolean useCompatibleValidathorIfSpecificNotPresent;
	
	private List<Validathor<?>> validathors = new ArrayList<>();
	private List<ValidathorParametrizedType<?>> validathorsParametrizedType = new ArrayList<>();
	
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
	
}
