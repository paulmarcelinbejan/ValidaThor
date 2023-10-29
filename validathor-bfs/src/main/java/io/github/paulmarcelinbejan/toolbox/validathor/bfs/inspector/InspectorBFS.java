package io.github.paulmarcelinbejan.toolbox.validathor.bfs.inspector;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import io.github.paulmarcelinbejan.toolbox.validathor.Validathor;
import io.github.paulmarcelinbejan.toolbox.validathor.ValidathorParametrizedType;
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
		
		if(info.is_ToValidateClass_InstanceOf_ParametrizedType()) {
			return validateParametrizedType(info, skipAfterValidation);
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
	 * validateParametrizedType
	 */
	@SuppressWarnings("unchecked")
	private <T> List<Info> validateParametrizedType(Info info, boolean skipAfterValidation) throws ValidathorException {
		ValidathorParametrizedType<T> validathorParametrizedType = (ValidathorParametrizedType<T>) mapValidathorsParametrizedType.get(info.getToValidateClass());
		
		if(validathorParametrizedType == null) {
			
			if(registry.isUseCompatibleValidathorIfSpecificNotPresent()) {
				validathorParametrizedType = (ValidathorParametrizedType<T>) ValidathorUtils.getCompatibleValidathorParametrizedType(info, mapValidathorsParametrizedType, cacheMapCompatibleValidathorsParametrizedType);
			}
			
			if(validathorParametrizedType == null) {
				return validateWithDefaultValidathor(info, skipAfterValidation);
			}
			
		}
		
		List<String> fieldsNameToSkip = validathorParametrizedType.getFieldsNameToSkip();
		
		T toValidate = (T) info.getToValidateValue();
		
		boolean isValid = validathorParametrizedType.isValid(toValidate);
		
		if(!isValid) {
			throw new ValidathorException(validathorParametrizedType, ExceptionMessagePattern.VALIDATION_FAILED, validathorParametrizedType.getClass().getSimpleName(), info.getToValidateName(), info.getOuterObject());
		}
		
		if(toValidate == null) {
			return Collections.emptyList();
		}
		
		List<Info> newToExplore = new ArrayList<>();

		if(!skipAfterValidation) {
			List<Info> fieldsToValidate = newToExplore(toValidate, ValidathorUtils.getFields(info.getToValidateClass()), fieldsNameToSkip);
			newToExplore.addAll(fieldsToValidate);
		}
		
		boolean isToValidateParametrizedTypeElements = validathorParametrizedType.isToValidateParametrizedTypeElements();
		
		if(isToValidateParametrizedTypeElements) {
			Collection<?> objectsToValidate = validathorParametrizedType.parametrizedTypeElementsToValidate().apply(toValidate);
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
