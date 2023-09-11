package com.paulmarcelinbejan.validator.algorithm.bfs.validators.base;

import static com.paulmarcelinbejan.validator.cases.enums.ObjectConfigCase.CASE_1;
import static com.paulmarcelinbejan.validator.cases.enums.ObjectConfigCase.CASE_2;
import static com.paulmarcelinbejan.validator.cases.enums.ObjectConfigCase.CASE_3;
import static com.paulmarcelinbejan.validator.cases.enums.ObjectConfigCase.CASE_4;
import static com.paulmarcelinbejan.validator.cases.enums.ObjectConfigCase.getCaseCondition;
import static com.paulmarcelinbejan.validator.utils.ValidatorUtils.isNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.paulmarcelinbejan.validator.algorithm.bfs.validators.SkipValidator;
import com.paulmarcelinbejan.validator.algorithm.bfs.validators.ValidateThenSkipValidator;
import com.paulmarcelinbejan.validator.cases.enums.ObjectConfigCase;
import com.paulmarcelinbejan.validator.config.configs.ObjectValidatorConfig;
import com.paulmarcelinbejan.validator.entities.FieldInfo;
import com.paulmarcelinbejan.validator.entities.Info;
import com.paulmarcelinbejan.validator.entities.ParentInfo;
import com.paulmarcelinbejan.validator.exception.ValidatorException;
import com.paulmarcelinbejan.validator.utils.ValidatorUtils;
import com.paulmarcelinbejan.validator.utils.ValidatorUtils.FunctionWithException;

public class ParentValidator {
    
	private static final Map<ObjectConfigCase, FunctionWithException<ParentInfo, List<? extends Info>, ValidatorException>> CASE_PROVIDER = new EnumMap<>(ObjectConfigCase.class);
	
	private final ObjectConfigCase condition;
	
	private final FieldValidator fieldValidator;
	private final SkipValidator skipValidator;
	private final ValidateThenSkipValidator validateThenSkipValidator;
	
	public ParentValidator(final ObjectValidatorConfig objectValidatorConfig, final FieldValidator fieldValidator, final SkipValidator skipValidator, final ValidateThenSkipValidator validateThenSkipValidator) {
		
		condition = getCaseCondition(objectValidatorConfig);
		
		this.fieldValidator = fieldValidator;
		this.skipValidator = skipValidator;
		this.validateThenSkipValidator = validateThenSkipValidator;
		
		CASE_PROVIDER.put(CASE_1, this::validateCase1);
		CASE_PROVIDER.put(CASE_2, this::validateCase2);
		CASE_PROVIDER.put(CASE_3, this::validateCase3);
		CASE_PROVIDER.put(CASE_4, this::validateCase4);
		
	}
	
	public List<? extends Info> validate(final ParentInfo parentInfo) throws ValidatorException {
		return CASE_PROVIDER.get(condition).apply(parentInfo);
	}
	
	/**
	 * CASE_1: 
	 * <br> canBeNull = true
	 * <br> validateInnerFields = true
	 */
	private List<Info> validateCase1(final ParentInfo info) throws ValidatorException {
		Class<?> toValidateClass = info.getToValidateClass();
		
		if(skipValidator.execute(toValidateClass) || validateThenSkipValidator.execute(toValidateClass)) {
			return Collections.emptyList();
		}
		
		Object toValidate = info.getToValidate();
		List<Field> toValidateFields = info.getToValidateFields();
		
		if(toValidate == null) {
			return Collections.emptyList();
		}
		
		return newObjectsToExplore(toValidate, toValidateFields);
	}
	
	/**
	 * CASE_2: 
	 * <br> canBeNull = false
	 * <br> validateInnerFields = false
	 */
	private List<Info> validateCase2(final ParentInfo info) throws ValidatorException {
		Class<?> toValidateClass = info.getToValidateClass();
		
		if(skipValidator.execute(toValidateClass)) {
			return Collections.emptyList();
		}
		
		String outerObjectToString = info.getOuterObjectToString();
		String toValidateName = info.getToValidateName();
		Object toValidate = info.getToValidate();
		
		isNull(toValidate, toValidateName, outerObjectToString);
		
		return Collections.emptyList();
	}
	
	/**
	 * CASE_3: 
	 * <br> canBeNull = false
	 * <br> validateInnerFields = true
	 */
	private List<Info> validateCase3(final ParentInfo info) throws ValidatorException {
		Class<?> toValidateClass = info.getToValidateClass();
		
		if(skipValidator.execute(toValidateClass)) {
			return Collections.emptyList();
		}
		
		String outerObjectToString = info.getOuterObjectToString();
		String toValidateName = info.getToValidateName();
		Object toValidate = info.getToValidate();
		
		isNull(toValidate, toValidateName, outerObjectToString);
		
		if(validateThenSkipValidator.execute(toValidateClass)) {
			return Collections.emptyList();
		}
		
		return newObjectsToExplore(toValidate, info.getToValidateFields());
	}
	
	/**
	 * CASE_4: 
	 * <br> canBeNull = true
	 * <br> validateInnerFields = false
	 */
	private List<Info> validateCase4(final ParentInfo info) {
		return Collections.emptyList();
	}
	
	private List<Info> newObjectsToExplore(final Object outerObject, final List<Field> fields) throws ValidatorException {
		List<Info> newObjectsToExplore = new ArrayList<>();
		
		for (final Field field : fields) {
			Class<?> fieldClass = field.getType();
			
			if(fieldClass.isPrimitive() || skipValidator.execute(fieldClass)) {
				continue;
			}
			
			FieldInfo currentlyExploringField = ValidatorUtils.buildFieldInfo(outerObject, field);
			
			List<? extends Info> toExplore = fieldValidator.validate(currentlyExploringField);

			newObjectsToExplore.addAll(toExplore);
		}
		
		return newObjectsToExplore;
	}
	
}
