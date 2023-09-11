package com.paulmarcelinbejan.validator.algorithm.dfs.validators;

import static com.paulmarcelinbejan.validator.cases.enums.ObjectConfigCase.CASE_1;
import static com.paulmarcelinbejan.validator.cases.enums.ObjectConfigCase.CASE_2;
import static com.paulmarcelinbejan.validator.cases.enums.ObjectConfigCase.CASE_3;
import static com.paulmarcelinbejan.validator.cases.enums.ObjectConfigCase.CASE_4;
import static com.paulmarcelinbejan.validator.cases.enums.ObjectConfigCase.getCaseCondition;
import static com.paulmarcelinbejan.validator.utils.ValidatorUtils.getFields;
import static com.paulmarcelinbejan.validator.utils.ValidatorUtils.getObject;
import static com.paulmarcelinbejan.validator.utils.ValidatorUtils.isNull;

import java.lang.reflect.Field;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.paulmarcelinbejan.validator.algorithm.dfs.validators.base.FieldValidator;
import com.paulmarcelinbejan.validator.algorithm.dfs.validators.base.ParentValidator;
import com.paulmarcelinbejan.validator.cases.enums.ObjectConfigCase;
import com.paulmarcelinbejan.validator.config.configs.ObjectValidatorConfig;
import com.paulmarcelinbejan.validator.entities.FieldInfo;
import com.paulmarcelinbejan.validator.exception.ValidatorException;
import com.paulmarcelinbejan.validator.utils.ValidatorUtils;
import com.paulmarcelinbejan.validator.utils.ValidatorUtils.ConsumerWithException;

public class ObjectValidator {

	private static final Map<ObjectConfigCase, ConsumerWithException<FieldInfo, ValidatorException>> CASE_PROVIDER = new EnumMap<>(ObjectConfigCase.class);
	
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
	
	private ParentValidator parentValidator;
	private FieldValidator fieldValidator;
	
	public void validate(final FieldInfo fieldInfo, final ParentValidator parentValidator, final FieldValidator fieldValidator) throws ValidatorException {
		this.parentValidator = parentValidator;
		this.fieldValidator = fieldValidator;
		CASE_PROVIDER.get(condition).accept(fieldInfo);
	}
	
	/**
	 * CASE_1: 
	 * <br> canBeNull = true
	 * <br> validateInnerFields = true
	 */
	private void validateCase1(final FieldInfo info) throws ValidatorException {
		Class<?> toValidateClass = info.getToValidateClass();
		
		if(skipValidator.execute(toValidateClass) || validateThenSkipValidator.execute(toValidateClass)) {
			return;
		}
		
		Object outerObject = info.getOuterObject();
		Field toValidateField = info.getToValidateField();
		Object toValidate = getObject(outerObject, toValidateField);
		
		if(toValidate != null) {
			validateObject(toValidate, getFields(toValidateClass));
		}
		
	}
	
	/**
	 * CASE_2: 
	 * <br> canBeNull = false
	 * <br> validateInnerFields = false
	 */
	private void validateCase2(final FieldInfo info) throws ValidatorException {
		Class<?> toValidateClass = info.getToValidateClass();
		
		if(skipValidator.execute(toValidateClass)) {
			return;
		}
		
		Object outerObject = info.getOuterObject();
		String outerObjectToString = info.getOuterObjectToString();
		Field toValidateField = info.getToValidateField();
		String toValidateName = info.getToValidateName();
		Object toValidate = getObject(outerObject, toValidateField);
		
		isNull(toValidate, toValidateName, outerObjectToString);
	}
	
	/**
	 * CASE_3: 
	 * <br> canBeNull = false
	 * <br> validateInnerFields = true
	 */
	private void validateCase3(final FieldInfo info) throws ValidatorException {
		Class<?> toValidateClass = info.getToValidateClass();
		
		if(skipValidator.execute(toValidateClass)) {
			return;
		}
		
		Object outerObject = info.getOuterObject();
		String outerObjectToString = info.getOuterObjectToString();
		Field toValidateField = info.getToValidateField();
		String toValidateName = info.getToValidateName();
		Object toValidate = getObject(outerObject, toValidateField);
		
		isNull(toValidate, toValidateName, outerObjectToString);
		
		if(validateThenSkipValidator.execute(toValidateClass)) {
			return;
		}
		
		validateObject(toValidate, getFields(toValidateClass));
	}
	
	/**
	 * CASE_4: 
	 * <br> canBeNull = true
	 * <br> validateInnerFields = false
	 */
	private void validateCase4(final FieldInfo info) { }
	
	private void validateObject(final Object outerObject, final List<Field> toValidateFields) throws ValidatorException {
		for(Field field : toValidateFields) {
			FieldInfo newToExplore = ValidatorUtils.buildFieldInfo(outerObject, field);
			fieldValidator.validate(newToExplore, parentValidator);
		}
	}
	
}
