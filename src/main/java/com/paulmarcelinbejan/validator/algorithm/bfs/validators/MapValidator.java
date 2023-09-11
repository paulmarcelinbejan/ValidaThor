package com.paulmarcelinbejan.validator.algorithm.bfs.validators;

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
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.paulmarcelinbejan.validator.cases.enums.ElementsValidatorConfigCase;
import com.paulmarcelinbejan.validator.config.configs.MapValidatorConfig;
import com.paulmarcelinbejan.validator.entities.FieldInfo;
import com.paulmarcelinbejan.validator.entities.ParentInfo;
import com.paulmarcelinbejan.validator.exception.ExceptionKeys;
import com.paulmarcelinbejan.validator.exception.ValidatorException;
import com.paulmarcelinbejan.validator.utils.ValidatorUtils;
import com.paulmarcelinbejan.validator.utils.ValidatorUtils.FunctionWithException;

public class MapValidator {

	private static final Map<ElementsValidatorConfigCase, FunctionWithException<FieldInfo, List<ParentInfo>, ValidatorException>> CASE_PROVIDER = new EnumMap<>(ElementsValidatorConfigCase.class);
	
	private final ElementsValidatorConfigCase condition;
	
	public MapValidator(final MapValidatorConfig mapValidatorConfig) {
		
		condition = ElementsValidatorConfigCase.getCaseCondition(mapValidatorConfig);
		
		CASE_PROVIDER.put(CASE_1, this::validateCase1);
		CASE_PROVIDER.put(CASE_2, this::validateCase2);
		CASE_PROVIDER.put(CASE_3, this::validateCase3);
		CASE_PROVIDER.put(CASE_4, this::validateCase4);
		CASE_PROVIDER.put(CASE_5, this::validateCase5);
		CASE_PROVIDER.put(CASE_6, this::validateCase6);
		CASE_PROVIDER.put(CASE_7, this::validateCase7);
		CASE_PROVIDER.put(CASE_8, this::validateCase8);
		
	}
	
	public List<ParentInfo> validate(final FieldInfo fieldInfo) throws ValidatorException {
		return CASE_PROVIDER.get(condition).apply(fieldInfo);
	}
	
	/**
	 * CASE_1: 
	 * <br> canBeNull = true
	 * <br> canBeEmpty = true
	 * <br> validateElems = true
	 */
	private List<ParentInfo> validateCase1(final FieldInfo info) throws ValidatorException { 
		Object outerObject = info.getOuterObject();
		String outerObjectToString = info.getOuterObjectToString();
		Field toValidateField = info.getToValidateField();
		String toValidateName = info.getToValidateName();
		Map<?, ?> toValidate = getMap(outerObject, toValidateField);
		Class<?> classTypeOfMapValue = ValidatorUtils.getClassTypeFromParameterizedType(toValidateField, 1);
		
		if(toValidate == null || toValidate.isEmpty()) {
			return Collections.emptyList();
		}
		
		return newObjectsToExplore(outerObject, outerObjectToString, toValidateName, toValidate, classTypeOfMapValue);
	}
	
	/**
	 * CASE_2: 
	 * <br> canBeNull = false
	 * <br> canBeEmpty = false
	 * <br> validateElems = false
	 */
	private List<ParentInfo> validateCase2(final FieldInfo info) throws ValidatorException {
		Object outerObject = info.getOuterObject();
		String outerObjectToString = info.getOuterObjectToString();
		Field toValidateField = info.getToValidateField();
		String toValidateName = info.getToValidateName();
		Map<?, ?> toValidate = getMap(outerObject, toValidateField);
		
		isNull(toValidate, toValidateName, outerObjectToString);
		isEmpty(toValidate, toValidateName, outerObjectToString);
		
		return Collections.emptyList();
	}
	
	/**
	 * CASE_3: 
	 * <br> canBeNull = true
	 * <br> canBeEmpty = false
	 * <br> validateElems = false
	 */
	private List<ParentInfo> validateCase3(final FieldInfo info) throws ValidatorException {
		Object outerObject = info.getOuterObject();
		String outerObjectToString = info.getOuterObjectToString();
		Field toValidateField = info.getToValidateField();
		String toValidateName = info.getToValidateName();
		Map<?, ?> toValidate = getMap(outerObject, toValidateField);
		
		isEmptyWhenNotNull(toValidate, toValidateName, outerObjectToString);
		
		return Collections.emptyList();
	}
	
	/**
	 * CASE_4: 
	 * <br> canBeNull = true
	 * <br> canBeEmpty = true
	 * <br> validateElems = false
	 */
	private List<ParentInfo> validateCase4(final FieldInfo info) {
		return Collections.emptyList();
	}
	
	/**
	 * CASE_5: 
	 * <br> canBeNull = true
	 * <br> canBeEmpty = false
	 * <br> validateElems = true
	 */
	private List<ParentInfo> validateCase5(final FieldInfo info) throws ValidatorException {
		Object outerObject = info.getOuterObject();
		String outerObjectToString = info.getOuterObjectToString();
		Field toValidateField = info.getToValidateField();
		String toValidateName = info.getToValidateName();
		Map<?, ?> toValidate = getMap(outerObject, toValidateField);
		Class<?> classTypeOfMapValue = ValidatorUtils.getClassTypeFromParameterizedType(toValidateField, 1);
		
		if(toValidate == null) {
			return Collections.emptyList();
		}
		
		isEmpty(toValidate, toValidateName, outerObjectToString);
		
		return newObjectsToExplore(outerObject, outerObjectToString, toValidateName, toValidate, classTypeOfMapValue);
	}

	/**
	 * CASE_6: 
	 * <br> canBeNull = false
	 * <br> canBeEmpty = true
	 * <br> validateElems = true
	 */
	private List<ParentInfo> validateCase6(final FieldInfo info) throws ValidatorException {
		Object outerObject = info.getOuterObject();
		String outerObjectToString = info.getOuterObjectToString();
		Field toValidateField = info.getToValidateField();
		String toValidateName = info.getToValidateName();
		Map<?, ?> toValidate = getMap(outerObject, toValidateField);
		Class<?> classTypeOfMapValue = ValidatorUtils.getClassTypeFromParameterizedType(toValidateField, 1);

		isNull(toValidate, toValidateName, outerObjectToString);
		
		return newObjectsToExplore(outerObject, outerObjectToString, toValidateName, toValidate, classTypeOfMapValue);
	}

	/**
	 * CASE_7: 
	 * <br> canBeNull = false
	 * <br> canBeEmpty = false
	 * <br> validateElems = true
	 */
	private List<ParentInfo> validateCase7(final FieldInfo info) throws ValidatorException {
		Object outerObject = info.getOuterObject();
		String outerObjectToString = info.getOuterObjectToString();
		Field toValidateField = info.getToValidateField();
		String toValidateName = info.getToValidateName();
		Map<?, ?> toValidate = getMap(outerObject, toValidateField);
		Class<?> classTypeOfMapValue = ValidatorUtils.getClassTypeFromParameterizedType(toValidateField, 1);
		
		isNull(toValidate, toValidateName, outerObjectToString);
		isEmpty(toValidate, toValidateName, outerObjectToString);
		
		return newObjectsToExplore(outerObject, outerObjectToString, toValidateName, toValidate, classTypeOfMapValue);
	}

	/**
	 * CASE_8: 
	 * <br> canBeNull = false
	 * <br> canBeEmpty = true
	 * <br> validateElems = false
	 */
	private List<ParentInfo> validateCase8(final FieldInfo info) throws ValidatorException {
		Object outerObject = info.getOuterObject();
		String outerObjectToString = info.getOuterObjectToString();
		Field toValidateField = info.getToValidateField();
		String toValidateName = info.getToValidateName();
		Map<?, ?> toValidate = getMap(outerObject, toValidateField);
		
		isNull(toValidate, toValidateName, outerObjectToString);
		
		return Collections.emptyList();
	}
	
	private Map<?,?> getMap(final Object outerObject, final Field field) throws ValidatorException {
		return (Map<?,?>) ValidatorUtils.getObject(outerObject, field);
	}
	
	private void isEmpty(final Map<?,?> toValidate, final String toValidateName, final String outerObjectToString) throws ValidatorException {
		if(toValidate.isEmpty()) {
			throw new ValidatorException(ExceptionKeys.IS_EMPTY, toValidateName, outerObjectToString);
		}
	}
	
	private void isEmptyWhenNotNull(final Map<?,?> toValidate, final String toValidateName, final String outerObjectToString) throws ValidatorException {
		if(toValidate != null && toValidate.isEmpty()) {
			throw new ValidatorException(ExceptionKeys.IS_EMPTY, toValidateName, outerObjectToString);
		}
	}
	
	private List<ParentInfo> newObjectsToExplore(final Object outerObject, final String outerObjectToString, final String toValidateName, final Map<?, ?> map, final Class<?> classTypeOfMapValue) {
		List<ParentInfo> toExplore = new ArrayList<>();
		
		map.forEach((k, v) -> {
			ParentInfo newToExplore = new ParentInfo(outerObject, outerObjectToString, toValidateName, classTypeOfMapValue, v, getFields(classTypeOfMapValue));
			toExplore.add(newToExplore);
		});
		
		return toExplore;
	}
	
}