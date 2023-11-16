package io.github.paulmarcelinbejan.toolbox.validathor.bfs.inspector;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import io.github.paulmarcelinbejan.toolbox.validathor.Validathor;
import io.github.paulmarcelinbejan.toolbox.validathor.ValidathorParameterizedType;
import io.github.paulmarcelinbejan.toolbox.validathor.exception.ExceptionMessagePattern;
import io.github.paulmarcelinbejan.toolbox.validathor.exception.ValidathorException;
import io.github.paulmarcelinbejan.toolbox.validathor.info.Info;
import io.github.paulmarcelinbejan.toolbox.validathor.inspector.InspectorBase;
import io.github.paulmarcelinbejan.toolbox.validathor.registry.ValidathorRegistry;
import io.github.paulmarcelinbejan.toolbox.validathor.utils.ValidathorUtils;

/**
 * InspectorBFS uses Breadth-first search to validate the object
 */
public class InspectorBFS extends InspectorBase {
	
	public InspectorBFS(ValidathorRegistry registry) {
		super(registry);
	}
	
	/**
	 * validate currently exploring info
	 */
	public List<Info> validate(Info info) throws ValidathorException {		
		boolean skipBeforeValidation = registry.getSkipBeforeValidationProcessor().execute(info.getToValidateClass());
		
		if(skipBeforeValidation) {
			return Collections.emptyList();
		}
		
		boolean skipAfterValidation = registry.getSkipAfterValidationProcessor().execute(info.getToValidateClass());
		
		if(info.is_ToValidateClass_InstanceOf_ParameterizedType()) {
			return validateParameterizedType(info, skipAfterValidation);
		}
		
		return validateSimpleType(info, skipAfterValidation);
	}
	
	/**
	 * validateSimpleType
	 */
	@SuppressWarnings("unchecked")
	private <T> List<Info> validateSimpleType(Info info, boolean skipAfterValidation) throws ValidathorException {
		Validathor<T> validathor = (Validathor<T>) mapValidathors.get(info.getToValidateClass());
		
		if(validathor == null) {
			
			if(registry.isUseCompatibleValidathorIfSpecificNotPresent()) {
				validathor = (Validathor<T>) ValidathorUtils.getCompatibleValidathor(info, mapValidathors, cacheMapCompatibleValidathors);
			}
			
			if(validathor == null) {
				return validateWithDefaultValidathor(info, skipAfterValidation);
			}
			
		}
		
		List<String> fieldsNameToSkip = validathor.getFieldsNameToSkip();
		
		T toValidate = (T) info.getToValidateValue();
		
		boolean isValid = validathor.isValid(toValidate);
		
		if(!isValid) {
			throw new ValidathorException(validathor, ExceptionMessagePattern.VALIDATION_FAILED, validathor.getClass().getSimpleName(), info.getToValidateName(), info.getOuterObject());
		}
		
		if(skipAfterValidation || toValidate == null) {
			return Collections.emptyList();
		}
		
		return newToExplore(toValidate, ValidathorUtils.getFields(info.getToValidateClass()), fieldsNameToSkip);
	}
	
	/**
	 * validateParameterizedType
	 */
	@SuppressWarnings("unchecked")
	private <T> List<Info> validateParameterizedType(Info info, boolean skipAfterValidation) throws ValidathorException {
		ValidathorParameterizedType<T> validathorParameterizedType = (ValidathorParameterizedType<T>) mapValidathorsParameterizedType.get(info.getToValidateClass());
		
		if(validathorParameterizedType == null) {
			
			if(registry.isUseCompatibleValidathorIfSpecificNotPresent()) {
				validathorParameterizedType = (ValidathorParameterizedType<T>) ValidathorUtils.getCompatibleValidathorParameterizedType(info, mapValidathorsParameterizedType, cacheMapCompatibleValidathorsParameterizedType);
			}
			
			if(validathorParameterizedType == null) {
				return validateWithDefaultValidathor(info, skipAfterValidation);
			}
			
		}
		
		List<String> fieldsNameToSkip = validathorParameterizedType.getFieldsNameToSkip();
		
		T toValidate = (T) info.getToValidateValue();
		
		boolean isValid = validathorParameterizedType.isValid(toValidate);
		
		if(!isValid) {
			throw new ValidathorException(validathorParameterizedType, ExceptionMessagePattern.VALIDATION_FAILED, validathorParameterizedType.getClass().getSimpleName(), info.getToValidateName(), info.getOuterObject());
		}
		
		if(toValidate == null) {
			return Collections.emptyList();
		}
		
		List<Info> newToExplore = new ArrayList<>();

		if(!skipAfterValidation) {
			List<Info> fieldsToValidate = newToExplore(toValidate, ValidathorUtils.getFields(info.getToValidateClass()), fieldsNameToSkip);
			newToExplore.addAll(fieldsToValidate);
		}
		
		boolean isToValidateParameterizedTypeElements = validathorParameterizedType.isToValidateParameterizedTypeElements();
		
		if(isToValidateParameterizedTypeElements) {
			Collection<?> objectsToValidate = validathorParameterizedType.parameterizedTypeElementsToValidate().apply(toValidate);
			List<Info> elementsToValidate = newToExplore(toValidate, info.getToValidateName(), objectsToValidate);
			newToExplore.addAll(elementsToValidate);
		}
		
		return newToExplore;
	}
	
	/**
	 * validateWithDefaultValidathor
	 */
	private List<Info> validateWithDefaultValidathor(Info info, boolean skipAfterValidation) throws ValidathorException {
		boolean isValid = registry.getDefaultValidathor().isValid(info.getToValidateValue());
		
		if(!isValid) {
			throw new ValidathorException(registry.getDefaultValidathor(), ExceptionMessagePattern.VALIDATION_FAILED, registry.getDefaultValidathor().getClass().getSimpleName(), info.getToValidateName(), info.getOuterObject());
		}
		
		if(skipAfterValidation || info.getToValidateValue() == null || !registry.getDefaultValidathor().isValidateInnerFields()) {
			return Collections.emptyList();
		}
		
		return newToExplore(info.getToValidateValue(), ValidathorUtils.getFields(info.getToValidateClass()), Collections.emptyList());
	}
	
	/**
	 * newToExplore
	 */
	private List<Info> newToExplore(final Object outerObject, final List<Field> toValidateFields, List<String> fieldsNameToSkip) {
		return toValidateFields.stream()
							   .filter(field -> !fieldsNameToSkip.contains(field.getName()))
							   .map(field -> ValidathorUtils.buildInfo(outerObject, field))
							   .toList();
	}
	
	/**
	 * newToExplore
	 */
	private List<Info> newToExplore(final Object outerObject, String fieldName, final Collection<?> objectsToValidate) {
		return objectsToValidate.stream()
								.filter(Objects::nonNull)
								.map(element -> ValidathorUtils.buildInfo(outerObject, element, fieldName))
								.toList();
	}
	
}
