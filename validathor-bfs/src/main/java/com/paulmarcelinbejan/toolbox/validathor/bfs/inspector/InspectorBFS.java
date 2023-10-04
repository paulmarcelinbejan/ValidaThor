package com.paulmarcelinbejan.toolbox.validathor.bfs.inspector;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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

public class InspectorBFS {
	
	public InspectorBFS(ValidathorRegistry registry) {
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
	
	
	public List<Info> validate(Info info) throws ValidathorException {		
		boolean skipBeforeValidation = skipBeforeValidationProcessor.execute(info.getToValidateClass());
		
		if(skipBeforeValidation) {
			return Collections.emptyList();
		}
		
		boolean skipAfterValidation = skipAfterValidationProcessor.execute(info.getToValidateClass());
		
		if(info.is_ToValidateClass_InstanceOf_ParametrizedType()) {
			return validateParametrizedType(info, skipAfterValidation);
		}
		
		return validateSimpleType(info, skipAfterValidation);
	}
	
	@SuppressWarnings("unchecked")
	private <T> List<Info> validateSimpleType(Info info, boolean skipAfterValidation) throws ValidathorException {
		Validathor<T> validathor = (Validathor<T>) mapValidathors.get(info.getToValidateClass());
		
		if(validathor == null) {
			
			if(useCompatibleValidathorIfSpecificNotPresent) {
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
	
	@SuppressWarnings("unchecked")
	private <T> List<Info> validateParametrizedType(Info info, boolean skipAfterValidation) throws ValidathorException {
		ValidathorParametrizedType<T> validathorParametrizedType = (ValidathorParametrizedType<T>) mapValidathorsParametrizedType.get(info.getToValidateClass());
		
		if(validathorParametrizedType == null) {
			
			if(useCompatibleValidathorIfSpecificNotPresent) {
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
	
	private List<Info> validateWithDefaultValidathor(Info info, boolean skipAfterValidation) throws ValidathorException {
		boolean isValid = defaultValidathor.isValid(info.getToValidateValue());
		
		if(!isValid) {
			throw new ValidathorException(defaultValidathor, ExceptionMessagePattern.VALIDATION_FAILED, defaultValidathor.getClass().getSimpleName(), info.getToValidateName(), info.getOuterObject());
		}
		
		if(skipAfterValidation || info.getToValidateValue() == null || !defaultValidathor.isValidateInnerFields()) {
			return Collections.emptyList();
		}
		
		return newToExplore(info.getToValidateValue(), ValidathorUtils.getFields(info.getToValidateClass()), Collections.emptyList());
	}
	
	private List<Info> newToExplore(final Object outerObject, final List<Field> toValidateFields, List<String> fieldsNameToSkip) {
		return toValidateFields.stream()
							   .filter(field -> !fieldsNameToSkip.contains(field.getName()))
							   .map(field -> ValidathorUtils.buildInfo(outerObject, field))
							   .toList();
	}
	
	private List<Info> newToExplore(final Object outerObject, String fieldName, final Collection<?> objectsToValidate) {
		return objectsToValidate.stream()
								.filter(Objects::nonNull)
								.map(element -> ValidathorUtils.buildInfo(outerObject, element, fieldName))
								.toList();
	}
	
}
