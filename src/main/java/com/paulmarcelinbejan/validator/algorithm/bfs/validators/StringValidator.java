package com.paulmarcelinbejan.validator.algorithm.bfs.validators;

import static com.paulmarcelinbejan.validator.cases.enums.NullEmptyValidatorConfigCase.CASE_1;
import static com.paulmarcelinbejan.validator.cases.enums.NullEmptyValidatorConfigCase.CASE_2;
import static com.paulmarcelinbejan.validator.cases.enums.NullEmptyValidatorConfigCase.CASE_3;
import static com.paulmarcelinbejan.validator.cases.enums.NullEmptyValidatorConfigCase.CASE_4;
import static com.paulmarcelinbejan.validator.utils.ValidatorUtils.isNull;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.paulmarcelinbejan.validator.cases.enums.NullEmptyValidatorConfigCase;
import com.paulmarcelinbejan.validator.config.configs.StringValidatorConfig;
import com.paulmarcelinbejan.validator.entities.FieldInfo;
import com.paulmarcelinbejan.validator.exception.ExceptionKeys;
import com.paulmarcelinbejan.validator.exception.ValidatorException;
import com.paulmarcelinbejan.validator.utils.ValidatorUtils;
import com.paulmarcelinbejan.validator.utils.ValidatorUtils.ConsumerWithException;

public class StringValidator {
	
	private static final Map<NullEmptyValidatorConfigCase, ConsumerWithException<FieldInfo, ValidatorException>> CASE_PROVIDER = new EnumMap<>(NullEmptyValidatorConfigCase.class);
	
	private final NullEmptyValidatorConfigCase condition;
	
	public StringValidator(final StringValidatorConfig stringValidatorConfig) {
		
		condition = NullEmptyValidatorConfigCase.getCaseCondition(stringValidatorConfig);
		
		CASE_PROVIDER.put(CASE_1, this::validateCase1);
		CASE_PROVIDER.put(CASE_2, this::validateCase2);
		CASE_PROVIDER.put(CASE_3, this::validateCase3);
		CASE_PROVIDER.put(CASE_4, this::validateCase4);
		
	}
	
	public List<FieldInfo> validate(final FieldInfo fieldInfo) throws ValidatorException {
		CASE_PROVIDER.get(condition).accept(fieldInfo);
		return Collections.emptyList();
	}

	/**
	 * CASE_1: 
	 * <br> canBeNull = true
	 * <br> canBeEmpty = true
	 */
	private void validateCase1(final FieldInfo info) { }
	
	/**
	 * CASE_2: 
	 * <br> canBeNull = false
	 * <br> canBeEmpty = false
	 */
	private void validateCase2(final FieldInfo info) throws ValidatorException {
		Object outerObject = info.getOuterObject();
		String outerObjectToString = info.getOuterObjectToString();
		Field toValidateField = info.getToValidateField();
		String toValidateName = info.getToValidateName();
		String toValidate = getString(outerObject, toValidateField);
		
		isNull(toValidate, toValidateName, outerObjectToString);
		isEmpty(toValidate, toValidateName, outerObjectToString);
	}
	
	/**
	 * CASE_3: 
	 * <br> canBeNull = false
	 * <br> canBeEmpty = true
	 */
	private void validateCase3(final FieldInfo info) throws ValidatorException {
		Object outerObject = info.getOuterObject();
		String outerObjectToString = info.getOuterObjectToString();
		Field toValidateField = info.getToValidateField();
		String toValidateName = info.getToValidateName();
		String toValidate = getString(outerObject, toValidateField);
		
		isNull(toValidate, toValidateName, outerObjectToString);
	}
	
	/**
	 * CASE_4: 
	 * <br> canBeNull = true
	 * <br> canBeEmpty = false
	 */
	private void validateCase4(final FieldInfo info) throws ValidatorException {
		Object outerObject = info.getOuterObject();
		String outerObjectToString = info.getOuterObjectToString();
		Field toValidateField = info.getToValidateField();
		String toValidateName = info.getToValidateName();
		String toValidate = getString(outerObject, toValidateField);
		
		isEmptyWhenNotNull(toValidate, toValidateName, outerObjectToString);
	}
	
	private String getString(final Object outerObject, final Field field) throws ValidatorException {
		return (String) ValidatorUtils.getObject(outerObject, field);
	}
	
	private void isEmpty(final String toValidate, final String fieldName, final String outerObjectToString) throws ValidatorException {
		if(toValidate.isEmpty()) {
			throw new ValidatorException(ExceptionKeys.IS_EMPTY, fieldName, outerObjectToString);
		}
	}
	
	private void isEmptyWhenNotNull(final String toValidate, final String fieldName, final String outerObjectToString) throws ValidatorException {
		if(toValidate != null && toValidate.isEmpty()) {
			throw new ValidatorException(ExceptionKeys.IS_EMPTY, fieldName, outerObjectToString);
		}
	}
	
}
