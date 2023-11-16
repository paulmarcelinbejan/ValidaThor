package io.github.paulmarcelinbejan.toolbox.validathor.dfs.inspector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import io.github.paulmarcelinbejan.toolbox.validathor.Validathor;
import io.github.paulmarcelinbejan.toolbox.validathor.ValidathorParameterizedType;
import io.github.paulmarcelinbejan.toolbox.validathor.exception.ExceptionMessagePattern;
import io.github.paulmarcelinbejan.toolbox.validathor.exception.ValidathorException;
import io.github.paulmarcelinbejan.toolbox.validathor.info.Info;
import io.github.paulmarcelinbejan.toolbox.validathor.inspector.InspectorBase;
import io.github.paulmarcelinbejan.toolbox.validathor.registry.ValidathorRegistry;
import io.github.paulmarcelinbejan.toolbox.validathor.utils.ValidathorUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * InspectorDFS uses Depth-first search to validate the object
 */
public class InspectorDFS extends InspectorBase {
	
	public InspectorDFS(ValidathorRegistry registry) {
		super(registry);
	}
	
	/**
	 * collectAllValidationException
	 */
	@Setter
	protected boolean collectAllValidationException;
	
	/**
	 * exceptions
	 */
	@Getter
	private List<ValidathorException> exceptions = new ArrayList<>();
	
	/**
	 * validate currently exploring info
	 */
	public void validate(Info info) throws ValidathorException {		
		boolean skipBeforeValidation = registry.getSkipBeforeValidationProcessor().execute(info.getToValidateClass());
		
		if(skipBeforeValidation) {
			return;
		}
		
		boolean skipAfterValidation = registry.getSkipAfterValidationProcessor().execute(info.getToValidateClass());
		
		try {
			if(info.is_ToValidateClass_InstanceOf_ParameterizedType()) {
				validateParameterizedType(info, skipAfterValidation);
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
	
	/**
	 * validateSimpleType
	 */
	@SuppressWarnings("unchecked")
	private <T> void validateSimpleType(Info info, boolean skipAfterValidation) throws ValidathorException {
		Validathor<T> validathor = (Validathor<T>) mapValidathors.get(info.getToValidateClass());
		
		if(validathor == null) {
			
			if(registry.isUseCompatibleValidathorIfSpecificNotPresent()) {
				validathor = (Validathor<T>) ValidathorUtils.getCompatibleValidathor(info, mapValidathors, cacheMapCompatibleValidathors);
			}
			
			if(validathor == null) {
				validateWithDefaultValidathor(info, skipAfterValidation);
				return;
			}
			
		}
		
		List<String> fieldsNameToSkip = validathor.getFieldsNameToSkip();
		
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
	
	/**
	 * validateParameterizedType
	 */
	@SuppressWarnings("unchecked")
	private <T> void validateParameterizedType(Info info, boolean skipAfterValidation) throws ValidathorException {
		ValidathorParameterizedType<T> validathorParameterizedType = (ValidathorParameterizedType<T>) mapValidathorsParameterizedType.get(info.getToValidateClass());
		
		if(validathorParameterizedType == null) {
			
			if(registry.isUseCompatibleValidathorIfSpecificNotPresent()) {
				validathorParameterizedType = (ValidathorParameterizedType<T>) ValidathorUtils.getCompatibleValidathorParameterizedType(info, mapValidathorsParameterizedType, cacheMapCompatibleValidathorsParameterizedType);
			}
			
			if(validathorParameterizedType == null) {
				validateWithDefaultValidathor(info, skipAfterValidation);
				return;
			}
			
		}
		
		List<String> fieldsNameToSkip = validathorParameterizedType.getFieldsNameToSkip();
		
		T toValidate = (T) info.getToValidateValue();
		
		boolean isValid = validathorParameterizedType.isValid(toValidate);
		
		if(!isValid) {
			throw new ValidathorException(validathorParameterizedType, ExceptionMessagePattern.VALIDATION_FAILED, validathorParameterizedType.getClass().getSimpleName(), info.getToValidateName(), info.getOuterObject());
		}
		
		if(toValidate == null) {
			return;
		}
		
		List<Info> newToExplore = new ArrayList<>();
		
		if(!skipAfterValidation) {
			List<Info> fieldsToValidate = ValidathorUtils.newToExplore(toValidate, ValidathorUtils.getFields(info.getToValidateClass()), fieldsNameToSkip);
			newToExplore.addAll(fieldsToValidate);
		}
		
		boolean isToValidateParameterizedTypeElements = validathorParameterizedType.isToValidateParameterizedTypeElements();
		
		if(isToValidateParameterizedTypeElements) {
			Collection<?> objectsToValidate = validathorParameterizedType.parameterizedTypeElementsToValidate().apply(toValidate);
			List<Info> elementsToValidate = ValidathorUtils.newToExplore(toValidate, info.getToValidateName(), objectsToValidate);
			newToExplore.addAll(elementsToValidate);
		}
		
		for(Info infoToExplore : newToExplore) {
			validate(infoToExplore);
		}
	}
	
	/**
	 * validateWithDefaultValidathor
	 */
	private void validateWithDefaultValidathor(Info info, boolean skipAfterValidation) throws ValidathorException {
		boolean isValid = registry.getDefaultValidathor().isValid(info.getToValidateValue());
		
		if(!isValid) {
			throw new ValidathorException(registry.getDefaultValidathor(), ExceptionMessagePattern.VALIDATION_FAILED, registry.getDefaultValidathor().getClass().getSimpleName(), info.getToValidateName(), info.getOuterObject());
		}
		
		if(skipAfterValidation || info.getToValidateValue() == null || !registry.getDefaultValidathor().isValidateInnerFields()) {
			return;
		}
		
		List<Info> newToExplore = ValidathorUtils.newToExplore(info.getToValidateValue(), ValidathorUtils.getFields(info.getToValidateClass()), Collections.emptyList());
		
		for(Info infoToExplore : newToExplore) {
			validate(infoToExplore);
		}
	}
	
}
