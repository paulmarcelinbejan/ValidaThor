package com.paulmarcelinbejan.validator.algorithm.dfs.validators;

import static com.paulmarcelinbejan.validator.cases.enums.ElementsValidatorConfigCase.CASE_1;
import static com.paulmarcelinbejan.validator.cases.enums.ElementsValidatorConfigCase.CASE_2;
import static com.paulmarcelinbejan.validator.cases.enums.ElementsValidatorConfigCase.CASE_3;
import static com.paulmarcelinbejan.validator.cases.enums.ElementsValidatorConfigCase.CASE_4;
import static com.paulmarcelinbejan.validator.cases.enums.ElementsValidatorConfigCase.CASE_5;
import static com.paulmarcelinbejan.validator.cases.enums.ElementsValidatorConfigCase.CASE_6;
import static com.paulmarcelinbejan.validator.cases.enums.ElementsValidatorConfigCase.CASE_7;
import static com.paulmarcelinbejan.validator.cases.enums.ElementsValidatorConfigCase.CASE_8;
import static com.paulmarcelinbejan.validator.utils.ValidatorUtils.getFields;
import static com.paulmarcelinbejan.validator.utils.ValidatorUtils.isNull;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;

import com.paulmarcelinbejan.validator.algorithm.dfs.validators.base.ParentValidator;
import com.paulmarcelinbejan.validator.cases.enums.ElementsValidatorConfigCase;
import com.paulmarcelinbejan.validator.config.configs.CollectionValidatorConfig;
import com.paulmarcelinbejan.validator.entities.FieldInfo;
import com.paulmarcelinbejan.validator.entities.ParentInfo;
import com.paulmarcelinbejan.validator.exception.ExceptionKeys;
import com.paulmarcelinbejan.validator.exception.ValidatorException;
import com.paulmarcelinbejan.validator.utils.ValidatorUtils;
import com.paulmarcelinbejan.validator.utils.ValidatorUtils.ConsumerWithException;

public class CollectionValidator {
	
	private static final Map<ElementsValidatorConfigCase, ConsumerWithException<FieldInfo, ValidatorException>> CASE_PROVIDER = new EnumMap<>(ElementsValidatorConfigCase.class);
	
	private final ElementsValidatorConfigCase condition;
	
	public CollectionValidator(final CollectionValidatorConfig collectionValidatorConfig) {
		
		condition = ElementsValidatorConfigCase.getCaseCondition(collectionValidatorConfig);
		
		CASE_PROVIDER.put(CASE_1, this::validateCase1);
		CASE_PROVIDER.put(CASE_2, this::validateCase2);
		CASE_PROVIDER.put(CASE_3, this::validateCase3);
		CASE_PROVIDER.put(CASE_4, this::validateCase4);
		CASE_PROVIDER.put(CASE_5, this::validateCase5);
		CASE_PROVIDER.put(CASE_6, this::validateCase6);
		CASE_PROVIDER.put(CASE_7, this::validateCase7);
		CASE_PROVIDER.put(CASE_8, this::validateCase8);
		
	}
	
	private ParentValidator parentValidator;
	
	public void validate(final FieldInfo fieldInfo, final ParentValidator parentValidator) throws ValidatorException {
		this.parentValidator = parentValidator;
		CASE_PROVIDER.get(condition).accept(fieldInfo);
	}
	
	/**
	 * CASE_1: 
	 * <br> canBeNull = true
	 * <br> canBeEmpty = true
	 * <br> validateElems = true
	 */
	private void validateCase1(final FieldInfo info) throws ValidatorException { 
		Object outerObject = info.getOuterObject();
		String outerObjectToString = info.getOuterObjectToString();
		Field toValidateField = info.getToValidateField();
		String toValidateName = info.getToValidateName();
		Collection<?> toValidate = getCollection(outerObject, toValidateField);
		Class<?> classTypeOfCollection = ValidatorUtils.getClassTypeFromParameterizedType(toValidateField, 0);
		
		if(toValidate != null && !toValidate.isEmpty()) {
			validateElements(outerObject, outerObjectToString, toValidateName, toValidate, classTypeOfCollection);
		}
		
	}
	
	/**
	 * CASE_2: 
	 * <br> canBeNull = false
	 * <br> canBeEmpty = false
	 * <br> validateElems = false
	 */
	private void validateCase2(final FieldInfo info) throws ValidatorException {
		Object outerObject = info.getOuterObject();
		String outerObjectToString = info.getOuterObjectToString();
		Field toValidateField = info.getToValidateField();
		String toValidateName = info.getToValidateName();
		Collection<?> toValidate = getCollection(outerObject, toValidateField);
		
		isNull(toValidate, toValidateName, outerObjectToString);
		isEmpty(toValidate, toValidateName, outerObjectToString);
	}
	
	/**
	 * CASE_3: 
	 * <br> canBeNull = true
	 * <br> canBeEmpty = false
	 * <br> validateElems = false
	 */
	private void validateCase3(final FieldInfo info) throws ValidatorException {
		Object outerObject = info.getOuterObject();
		String outerObjectToString = info.getOuterObjectToString();
		Field toValidateField = info.getToValidateField();
		String toValidateName = info.getToValidateName();
		Collection<?> toValidate = getCollection(outerObject, toValidateField);
		
		isEmptyWhenNotNull(toValidate, toValidateName, outerObjectToString);
	}
	
	/**
	 * CASE_4: 
	 * <br> canBeNull = true
	 * <br> canBeEmpty = true
	 * <br> validateElems = false
	 */
	private void validateCase4(final FieldInfo info) { }
	
	/**
	 * CASE_5: 
	 * <br> canBeNull = true
	 * <br> canBeEmpty = false
	 * <br> validateElems = true
	 */
	private void validateCase5(final FieldInfo info) throws ValidatorException {
		Object outerObject = info.getOuterObject();
		String outerObjectToString = info.getOuterObjectToString();
		Field toValidateField = info.getToValidateField();
		String toValidateName = info.getToValidateName();
		Collection<?> toValidate = getCollection(outerObject, toValidateField);
		Class<?> classTypeOfCollection = ValidatorUtils.getClassTypeFromParameterizedType(toValidateField, 0);
		
		if(toValidate != null) {
			isEmpty(toValidate, toValidateName, outerObjectToString);
			
			validateElements(outerObject, outerObjectToString, toValidateName, toValidate, classTypeOfCollection);
		}
		
	}

	/**
	 * CASE_6: 
	 * <br> canBeNull = false
	 * <br> canBeEmpty = true
	 * <br> validateElems = true
	 */
	private void validateCase6(final FieldInfo info) throws ValidatorException {
		Object outerObject = info.getOuterObject();
		String outerObjectToString = info.getOuterObjectToString();
		Field toValidateField = info.getToValidateField();
		String toValidateName = info.getToValidateName();
		Collection<?> toValidate = getCollection(outerObject, toValidateField);
		Class<?> classTypeOfCollection = ValidatorUtils.getClassTypeFromParameterizedType(toValidateField, 0);
		
		isNull(toValidate, toValidateName, outerObjectToString);
		
		validateElements(outerObject, outerObjectToString, toValidateName, toValidate, classTypeOfCollection);
	}

	/**
	 * CASE_7: 
	 * <br> canBeNull = false
	 * <br> canBeEmpty = false
	 * <br> validateElems = true
	 */
	private void validateCase7(final FieldInfo info) throws ValidatorException {
		Object outerObject = info.getOuterObject();
		String outerObjectToString = info.getOuterObjectToString();
		Field toValidateField = info.getToValidateField();
		String toValidateName = info.getToValidateName();
		Collection<?> toValidate = getCollection(outerObject, toValidateField);
		Class<?> classTypeOfCollection = ValidatorUtils.getClassTypeFromParameterizedType(toValidateField, 0);
		
		isNull(toValidate, toValidateName, outerObjectToString);
		isEmpty(toValidate, toValidateName, outerObjectToString);
		
		validateElements(outerObject, outerObjectToString, toValidateName, toValidate, classTypeOfCollection);
	}

	/**
	 * CASE_8: 
	 * <br> canBeNull = false
	 * <br> canBeEmpty = true
	 * <br> validateElems = false
	 */
	private void validateCase8(final FieldInfo info) throws ValidatorException {
		Object outerObject = info.getOuterObject();
		String outerObjectToString = info.getOuterObjectToString();
		Field toValidateField = info.getToValidateField();
		String toValidateName = info.getToValidateName();
		Collection<?> toValidate = getCollection(outerObject, toValidateField);
		
		isNull(toValidate, toValidateName, outerObjectToString);
	}
	
	private Collection<?> getCollection(final Object outerObject, final Field field) throws ValidatorException {
		return (Collection<?>) ValidatorUtils.getObject(outerObject, field);
	}
	
	private void isEmpty(final Collection<?> toValidate, final String toValidateName, final String outerObjectToString) throws ValidatorException {
		if(toValidate.isEmpty()) {
			throw new ValidatorException(ExceptionKeys.IS_EMPTY, toValidateName, outerObjectToString);
		}
	}
	
	private void isEmptyWhenNotNull(final Collection<?> toValidate, final String toValidateName, final String outerObjectToString) throws ValidatorException {
		if(toValidate != null) {
			isEmpty(toValidate, toValidateName, outerObjectToString);
		}
	}
	
	private void validateElements(final Object outerObject, final String outerObjectToString, final String toValidateName, final Collection<?> collection, final Class<?> classTypeOfCollection) throws ValidatorException {
		for(Object elem : collection) {
			ParentInfo newToExplore = new ParentInfo(outerObject, outerObjectToString, toValidateName, classTypeOfCollection, elem, getFields(classTypeOfCollection));
			parentValidator.validate(newToExplore);
		}
	}
	
}