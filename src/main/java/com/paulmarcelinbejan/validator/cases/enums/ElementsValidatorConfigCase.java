package com.paulmarcelinbejan.validator.cases.enums;

import com.paulmarcelinbejan.validator.config.configs.common.ElementsValidatorConfig;

public enum ElementsValidatorConfigCase {

	/**
	 * CASE_1: 
	 * <br> canBeNull = true
	 * <br> canBeEmpty = true
	 * <br> validateElements = true
	 */
	CASE_1,
	
	/**
	 * CASE_2: 
	 * <br> canBeNull = false
	 * <br> canBeEmpty = false
	 * <br> validateElements = false
	 */
	CASE_2,
	
	/**
	 * CASE_3: 
	 * <br> canBeNull = true
	 * <br> canBeEmpty = false
	 * <br> validateElements = false
	 */
	CASE_3,
	
	/**
	 * CASE_4: 
	 * <br> canBeNull = true
	 * <br> canBeEmpty = true
	 * <br> validateElements = false
	 */
	CASE_4,

	/**
	 * CASE_5: 
	 * <br> canBeNull = true
	 * <br> canBeEmpty = false
	 * <br> validateElements = true
	 */
	CASE_5,
	
	/**
	 * CASE_6: 
	 * <br> canBeNull = false
	 * <br> canBeEmpty = true
	 * <br> validateElements = true
	 */
	CASE_6,
	
	/**
	 * CASE_7: 
	 * <br> canBeNull = false
	 * <br> canBeEmpty = false
	 * <br> validateElements = true
	 */
	CASE_7,
	
	/**
	 * CASE_8: 
	 * <br> canBeNull = false
	 * <br> canBeEmpty = true
	 * <br> validateElements = false
	 */
	CASE_8;
	
	public static <VC extends ElementsValidatorConfig> ElementsValidatorConfigCase getCaseCondition(VC validatorConfig) {
		boolean canBeNull = validatorConfig.isCanBeNull();
		boolean canBeEmpty = validatorConfig.isCanBeEmpty();
		boolean validateElements = validatorConfig.isValidateElements();
		
		/**
		 * CASE_1: 
		 * <br> canBeNull = true
		 * <br> canBeEmpty = true
		 * <br> validateElements = true
		 */
		if(canBeNull && canBeEmpty && validateElements) {
			return CASE_1;
		}
		/**
		 * CASE_2: 
		 * <br> canBeNull = false
		 * <br> canBeEmpty = false
		 * <br> validateElements = false
		 */
		if(!canBeNull && !canBeEmpty && !validateElements) {
			return CASE_2;
		}
		/**
		 * CASE_3: 
		 * <br> canBeNull = true
		 * <br> canBeEmpty = false
		 * <br> validateElements = false
		 */
		if(canBeNull && !canBeEmpty && !validateElements) {
			return CASE_3;
		}
		/**
		 * CASE_4: 
		 * <br> canBeNull = true
		 * <br> canBeEmpty = true
		 * <br> validateElements = false
		 */
		if(canBeNull && canBeEmpty && !validateElements) {
			return CASE_4;
		}
		/**
		 * CASE_5: 
		 * <br> canBeNull = true
		 * <br> canBeEmpty = false
		 * <br> validateElements = true
		 */
		if(canBeNull && !canBeEmpty && validateElements) {
			return CASE_5;
		}
		/**
		 * CASE_6: 
		 * <br> canBeNull = false
		 * <br> canBeEmpty = true
		 * <br> validateElements = true
		 */
		if(!canBeNull && canBeEmpty && validateElements) {
			return CASE_6;
		}
		/**
		 * CASE_7: 
		 * <br> canBeNull = false
		 * <br> canBeEmpty = false
		 * <br> validateElements = true
		 */
		if(!canBeNull && !canBeEmpty && validateElements) {
			return CASE_7;
		}
		/**
		 * CASE_8: 
		 * <br> canBeNull = false
		 * <br> canBeEmpty = true
		 * <br> validateElements = false
		 */
		return CASE_8;
	}
	
}
