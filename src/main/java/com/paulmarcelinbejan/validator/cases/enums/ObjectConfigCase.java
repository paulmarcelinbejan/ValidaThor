package com.paulmarcelinbejan.validator.cases.enums;

import com.paulmarcelinbejan.validator.config.configs.ObjectValidatorConfig;

public enum ObjectConfigCase {

	/**
	 * CASE_1: 
	 * <br> canBeNull = true
	 * <br> validateInnerFields = true
	 */
	CASE_1,
	
	/**
	 * CASE_2: 
	 * <br> canBeNull = false
	 * <br> validateInnerFields = false
	 */
	CASE_2,
	
	/**
	 * CASE_3: 
	 * <br> canBeNull = false
	 * <br> validateInnerFields = true
	 */
	CASE_3,
	
	/**
	 * CASE_4: 
	 * <br> canBeNull = true
	 * <br> validateInnerFields = false
	 */
	CASE_4;
	
	public static ObjectConfigCase getCaseCondition(ObjectValidatorConfig validatorConfig) {
		boolean canBeNull = validatorConfig.isCanBeNull();
		boolean validateInnerFields = validatorConfig.isValidateInnerFields();
		
		if(canBeNull && validateInnerFields) {
			return CASE_1;
		}
		if(!canBeNull && !validateInnerFields) {
			return CASE_2;
		}
		if(!canBeNull && validateInnerFields) {
			return CASE_3;
		}
		return CASE_4;
	}
	
}
