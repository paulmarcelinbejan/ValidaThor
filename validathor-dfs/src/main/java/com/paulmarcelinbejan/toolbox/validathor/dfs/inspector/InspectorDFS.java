package com.paulmarcelinbejan.toolbox.validathor.dfs.inspector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.paulmarcelinbejan.toolbox.validathor.AbstractObjectValidathor;
import com.paulmarcelinbejan.toolbox.validathor.Validathor;
import com.paulmarcelinbejan.toolbox.validathor.ValidathorParametrizedType;
import com.paulmarcelinbejan.toolbox.validathor.exception.ExceptionMessagePattern;
import com.paulmarcelinbejan.toolbox.validathor.exception.ValidathorException;
import com.paulmarcelinbejan.toolbox.validathor.info.Info;
import com.paulmarcelinbejan.toolbox.validathor.processor.SkipAfterValidationProcessor;
import com.paulmarcelinbejan.toolbox.validathor.processor.SkipBeforeValidationProcessor;
import com.paulmarcelinbejan.toolbox.validathor.registry.ValidathorRegistry;
import com.paulmarcelinbejan.toolbox.validathor.utils.ValidathorUtils;

import lombok.Getter;
import lombok.Setter;

public class InspectorDFS {
	
	public InspectorDFS(ValidathorRegistry registry) {
		skipBeforeValidationProcessor = registry.getSkipBeforeValidationProcessor();
		skipAfterValidationProcessor = registry.getSkipAfterValidationProcessor();
		
		defaultValidathor = registry.getDefaultValidathor();
		validathors = registry.getValidathors();
		mapValidathors = validathors.stream().collect(Collectors.toMap(Validathor::getTypeParameterClass, validathor -> validathor));
		validathorsParametrizedType = registry.getValidathorsParametrizedType();
		mapValidathorsParametrizedType = validathorsParametrizedType.stream().collect(Collectors.toMap(ValidathorParametrizedType::getTypeParameterClass, validathor -> validathor));
		
		useCompatibleValidathorIfSpecificNotPresent = registry.isUseCompatibleValidathorIfSpecificNotPresent();
	}

	private final SkipBeforeValidationProcessor skipBeforeValidationProcessor;
	private final SkipAfterValidationProcessor skipAfterValidationProcessor;
	private final AbstractObjectValidathor defaultValidathor;
	
	private final List<Validathor<?>> validathors;
	private final Map<Class<?>, Validathor<?>> mapValidathors;
	private final List<ValidathorParametrizedType<?>> validathorsParametrizedType;
	private final Map<Class<?>, ValidathorParametrizedType<?>> mapValidathorsParametrizedType;
	
	private final boolean useCompatibleValidathorIfSpecificNotPresent;
	
	private Map<Class<?>, Validathor<?>> cacheMapCompatibleValidathors = new HashMap<>();
	private Map<Class<?>, ValidathorParametrizedType<?>> cacheMapCompatibleValidathorsParametrizedType = new HashMap<>();
	
	@Setter
	protected boolean collectAllValidationException;
	
	@Getter
	private List<ValidathorException> exceptions = new ArrayList<>();
	
	public void validate(Info info) throws ValidathorException {		
		boolean skipBeforeValidation = skipBeforeValidationProcessor.execute(info.getToValidateClass());
		
		if(skipBeforeValidation) {
			return;
		}
		
		boolean skipAfterValidation = skipAfterValidationProcessor.execute(info.getToValidateClass());
		
		try {
			if(info.is_ToValidateClass_InstanceOf_ParametrizedType()) {
				validateParametrizedType(info, skipAfterValidation);
			} else {
				validateSimpleType(info, skipAfterValidation);
			}
		} catch (ValidathorException e) {
			if(!collectAllValidationException) {
				throw e;
			}
			exceptions.add(e);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private <T> void validateSimpleType(Info info, boolean skipAfterValidation) throws ValidathorException {
		Validathor<T> validathor = (Validathor<T>) mapValidathors.get(info.getToValidateClass());
		
		List<String> fieldsNameToSkip = new ArrayList<>();
		
		if(validathor != null) {
			
			fieldsNameToSkip = validathor.getFieldsNameToSkip();
			
		} else {
			
			if(useCompatibleValidathorIfSpecificNotPresent) {
				validathor = (Validathor<T>) ValidathorUtils.getCompatibleValidathor(info, mapValidathors, cacheMapCompatibleValidathors);
			}
			
			if(validathor == null) {
				validateWithDefaultValidathor(info, skipAfterValidation);
				return;
			}
			
		}
		
		T toValidate = (T) info.getToValidateValue();
		
		boolean isValid = validathor.isValid(toValidate);
		
		if(!isValid) {
			throw new ValidathorException(validathor, ExceptionMessagePattern.VALIDATION_FAILED, validathor.getClass().getSimpleName(), info.getToValidateName(), info.getOuterObject());
		}
		
		if(skipAfterValidation || toValidate == null) {
			return;
		}
		
		List<Info> newToExplore = ValidathorUtils.newToExplore(toValidate, ValidathorUtils.getFields(info.getToValidateClass()), fieldsNameToSkip);
		
		for(Info infoToExplore : newToExplore) {
			validate(infoToExplore);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private <T> void validateParametrizedType(Info info, boolean skipAfterValidation) throws ValidathorException {
		ValidathorParametrizedType<T> validathorParametrizedType = (ValidathorParametrizedType<T>) mapValidathorsParametrizedType.get(info.getToValidateClass());
		
		List<String> fieldsNameToSkip = new ArrayList<>();
		
		if(validathorParametrizedType != null) {
			
			fieldsNameToSkip = validathorParametrizedType.getFieldsNameToSkip();
			
		} else {
			
			if(useCompatibleValidathorIfSpecificNotPresent) {
				validathorParametrizedType = (ValidathorParametrizedType<T>) ValidathorUtils.getCompatibleValidathorParametrizedType(info, mapValidathorsParametrizedType, cacheMapCompatibleValidathorsParametrizedType);
			}
			
			if(validathorParametrizedType == null) {
				validateWithDefaultValidathor(info, skipAfterValidation);
				return;
			}
			
		}
		
		T toValidate = (T) info.getToValidateValue();
		
		boolean isValid = validathorParametrizedType.isValid(toValidate);
		
		if(!isValid) {
			throw new ValidathorException(validathorParametrizedType, ExceptionMessagePattern.VALIDATION_FAILED, validathorParametrizedType.getClass().getSimpleName(), info.getToValidateName(), info.getOuterObject());
		}
		
		if(toValidate == null) {
			return;
		}
		
		List<Info> newToExplore = new ArrayList<>();
		
		if(!skipAfterValidation) {
			List<Info> fieldsToValidate = ValidathorUtils.newToExplore(toValidate, ValidathorUtils.getFields(info.getToValidateClass()), fieldsNameToSkip);
			newToExplore.addAll(fieldsToValidate);
		}
		
		boolean isToValidateParametrizedTypeElements = validathorParametrizedType.isToValidateParametrizedTypeElements();
		
		if(isToValidateParametrizedTypeElements) {
			Collection<?> objectsToValidate = validathorParametrizedType.parametrizedTypeElementsToValidate().apply(toValidate);
			List<Info> elementsToValidate = ValidathorUtils.newToExplore(toValidate, info.getToValidateName(), objectsToValidate);
			newToExplore.addAll(elementsToValidate);
		}
		
		for(Info infoToExplore : newToExplore) {
			validate(infoToExplore);
		}
	}
	
	private void validateWithDefaultValidathor(Info info, boolean skipAfterValidation) throws ValidathorException {
		boolean isValid = defaultValidathor.isValid(info.getToValidateValue());
		
		if(!isValid) {
			throw new ValidathorException(defaultValidathor, ExceptionMessagePattern.VALIDATION_FAILED, defaultValidathor.getClass().getSimpleName(), info.getToValidateName(), info.getOuterObject());
		}
		
		if(skipAfterValidation || info.getToValidateValue() == null || !defaultValidathor.isValidateInnerFields()) {
			return;
		}
		
		List<Info> newToExplore = ValidathorUtils.newToExplore(info.getToValidateValue(), ValidathorUtils.getFields(info.getToValidateClass()), Collections.emptyList());
		
		for(Info infoToExplore : newToExplore) {
			validate(infoToExplore);
		}
	}
	
}
