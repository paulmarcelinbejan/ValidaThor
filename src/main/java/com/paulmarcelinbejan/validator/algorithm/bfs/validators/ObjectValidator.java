package com.paulmarcelinbejan.validator.algorithm.bfs.validators;

import static com.paulmarcelinbejan.validator.cases.enums.ObjectConfigCase.CASE_1;
import static com.paulmarcelinbejan.validator.cases.enums.ObjectConfigCase.CASE_2;
import static com.paulmarcelinbejan.validator.cases.enums.ObjectConfigCase.CASE_3;
import static com.paulmarcelinbejan.validator.cases.enums.ObjectConfigCase.CASE_4;
import static com.paulmarcelinbejan.validator.cases.enums.ObjectConfigCase.getCaseCondition;
import static com.paulmarcelinbejan.validator.utils.ValidatorUtils.getFields;
import static com.paulmarcelinbejan.validator.utils.ValidatorUtils.getObject;
import static com.paulmarcelinbejan.validator.utils.ValidatorUtils.isNull;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.paulmarcelinbejan.validator.cases.enums.ObjectConfigCase;
import com.paulmarcelinbejan.validator.config.configs.ObjectValidatorConfig;
import com.paulmarcelinbejan.validator.entities.FieldInfo;
import com.paulmarcelinbejan.validator.exception.ValidatorException;
import com.paulmarcelinbejan.validator.utils.ValidatorUtils;
import com.paulmarcelinbejan.validator.utils.ValidatorUtils.FunctionWithException;

public class ObjectValidator {

	private static final Map<ObjectConfigCase, FunctionWithException<FieldInfo, List<FieldInfo>, ValidatorException>> CASE_PROVIDER = new EnumMap<>(ObjectConfigCase.class);
	
	private final ObjectConfigCase condition;
	
	private final SkipValidator skipValidator;
	private final ValidateThenSkipValidator validateThenSkipValidator;
	
	public ObjectValidator(final ObjectValidatorConfig objectValidatorConfig, final SkipValidator skipValidator, final ValidateThenSkipValidator validateThenSkipValidator) {
		
		condition = getCaseCondition(objectValidatorConfig);
		
		this.skipValidator = skipValidator;
		this.validateThenSkipValidator = validateThenSkipValidator;
		
		CASE_PROVIDER.put(CASE_1, this::validateCase1);
		CASE_PROVIDER.put(CASE_2, this::validateCase2);
		CASE_PROVIDER.put(CASE_3, this::validateCase3);
		CASE_PROVIDER.put(CASE_4, this::validateCase4);
	
	}
	
	public List<FieldInfo> validate(final FieldInfo fieldInfo) throws ValidatorException {
		return CASE_PROVIDER.get(condition).apply(fieldInfo);
	}
	
	/**
	 * CASE_1: 
	 * <br> canBeNull = true
	 * <br> validateInnerFields = true
	 */
	private List<FieldInfo> validateCase1(final FieldInfo info) throws ValidatorException {
		Class<?> toValidateClass = info.getToValidateClass();
		
		if(skipValidator.execute(toValidateClass) || validateThenSkipValidator.execute(toValidateClass)) {
			return Collections.emptyList();
		}
		
		Object outerObject = info.getOuterObject();
		Field toValidateField = info.getToValidateField();
		Object toValidate = getObject(outerObject, toValidateField);
		
		if(toValidate != null) {
			return newObjectsToExplore(toValidate, getFields(toValidateClass));
		}
		
		return Collections.emptyList();
	}
	
	/**
	 * CASE_2: 
	 * <br> canBeNull = false
	 * <br> validateInnerFields = false
	 */
	private List<FieldInfo> validateCase2(final FieldInfo info) throws ValidatorException {
		Class<?> toValidateClass = info.getToValidateClass();
		
		if(skipValidator.execute(toValidateClass)) {
			return Collections.emptyList();
		}
		
		Object outerObject = info.getOuterObject();
		String outerObjectToString = info.getOuterObjectToString();
		Field toValidateField = info.getToValidateField();
		String toValidateName = info.getToValidateName();
		Object toValidate = getObject(outerObject, toValidateField);
		
		isNull(toValidate, toValidateName, outerObjectToString);
		
		return Collections.emptyList();
	}
	
	/**
	 * CASE_3: 
	 * <br> canBeNull = false
	 * <br> validateInnerFields = true
	 */
	private List<FieldInfo> validateCase3(final FieldInfo info) throws ValidatorException {
		Class<?> toValidateClass = info.getToValidateClass();
		
		if(skipValidator.execute(toValidateClass)) {
			return Collections.emptyList();
		}
		
		Object outerObject = info.getOuterObject();
		String outerObjectToString = info.getOuterObjectToString();
		Field toValidateField = info.getToValidateField();
		String toValidateName = info.getToValidateName();
		Object toValidate = getObject(outerObject, toValidateField);
		
		isNull(toValidate, toValidateName, outerObjectToString);
		
		if(validateThenSkipValidator.execute(toValidateClass)) {
			return Collections.emptyList();
		}
		
		return newObjectsToExplore(toValidate, getFields(toValidateClass));
	}
	
	/**
	 * CASE_4: 
	 * <br> canBeNull = true
	 * <br> validateInnerFields = false
	 */
	private List<FieldInfo> validateCase4(final FieldInfo info) {
		return Collections.emptyList();
	}
	
	private List<FieldInfo> newObjectsToExplore(final Object outerObject, final List<Field> toValidateFields) {
		return toValidateFields.stream()
							   .map(field -> ValidatorUtils.buildFieldInfo(outerObject, field))
							   .toList();
	}
	
}
