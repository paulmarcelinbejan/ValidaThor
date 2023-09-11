package com.paulmarcelinbejan.validator.cases.enums;

import com.paulmarcelinbejan.validator.config.configs.common.NullEmptyValidatorConfig;

public enum NullEmptyValidatorConfigCase {

	/**
	 * CASE_1: 
	 * <br> canBeNull = true
	 * <br> canBeEmpty = true
	 */
	CASE_1,
	
	/**
	 * CASE_2: 
	 * <br> canBeNull = false
	 * <br> canBeEmpty = false
	 */
	CASE_2,
	
	/**
	 * CASE_3: 
	 * <br> canBeNull = false
	 * <br> canBeEmpty = true
	 */
	CASE_3,
	
	/**
	 * CASE_4: 
	 * <br> canBeNull = true
	 * <br> canBeEmpty = false
	 */
	CASE_4;
	
	public static <VC extends NullEmptyValidatorConfig> NullEmptyValidatorConfigCase getCaseCondition(VC validatorConfig) {
		boolean canBeNull = validatorConfig.isCanBeNull();
		boolean canBeEmpty = validatorConfig.isCanBeEmpty();
		
		/**
		 * CASE_1: 
		 * <br> canBeNull = true
		 * <br> canBeEmpty = true
		 */
		if(canBeNull && canBeEmpty) {
			return CASE_1;
		}
		/**
		 * CASE_2: 
		 * <br> canBeNull = false
		 * <br> canBeEmpty = false
		 */
		if(!canBeNull && !canBeEmpty) {
			return CASE_2;
		}
		/**
		 * CASE_3: 
		 * <br> canBeNull = false
		 * <br> canBeEmpty = true
		 */
		if(!canBeNull && canBeEmpty) {
			return CASE_3;
		}
		/**
		 * CASE_4: 
		 * <br> canBeNull = true
		 * <br> canBeEmpty = false
		 */
		return CASE_4;
	}
	
}
