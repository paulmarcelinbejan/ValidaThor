package com.paulmarcelinbejan.validator.algorithm.bfs_dfs.validators;

import static com.paulmarcelinbejan.validator.test.utils.ObjectValorizator.populateCar;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.paulmarcelinbejan.validator.config.ValidatorConfig;
import com.paulmarcelinbejan.validator.config.configs.StringValidatorConfig;
import com.paulmarcelinbejan.validator.exception.ExceptionKeys;
import com.paulmarcelinbejan.validator.exception.ValidatorException;
import com.paulmarcelinbejan.validator.test.entities.Car;
import com.paulmarcelinbejan.validator.test.entities.YEAR;
import com.paulmarcelinbejan.validator.test.utils.ObjectValorizator;
import com.paulmarcelinbejan.validator.test.utils.ValidatorTestUtils;

class StringValidatorTest {
	
	@Test
	void STRING_NULL() {
		Car ferrari = populateCar();
		ferrari.getOwners().get(1).setSurname(null);
		
		ValidatorException eBFS = assertThrows(ValidatorException.class, () -> ValidatorTestUtils.validateObjectBFS(ferrari));
		assertEquals(ExceptionKeys.IS_NULL, eBFS.getKey());
		
		ValidatorException eDFS = assertThrows(ValidatorException.class, () -> ValidatorTestUtils.validateObjectDFS(ferrari));
		assertEquals(ExceptionKeys.IS_NULL, eDFS.getKey());
		
		assertEquals(eBFS.getMessage(), eDFS.getMessage());
	}
	
	@Test
	void STRING_EMPTY() {
		Car ferrari = populateCar();
		ferrari.getCarMaintenance().get(YEAR._1978).setGarageService("");
		
		ValidatorException eBFS = assertThrows(ValidatorException.class, () -> ValidatorTestUtils.validateObjectBFS(ferrari));
		assertEquals(ExceptionKeys.IS_EMPTY, eBFS.getKey());
		
		ValidatorException eDFS = assertThrows(ValidatorException.class, () -> ValidatorTestUtils.validateObjectDFS(ferrari));
		assertEquals(ExceptionKeys.IS_EMPTY, eDFS.getKey());
		
		assertEquals(eBFS.getMessage(), eDFS.getMessage());
	}
	
	@Test
	void STRING_CAN_BE_NULL() {
		Car ferrari = ObjectValorizator.populateCarNullString();
		
		StringValidatorConfig stringValidatorConfig = StringValidatorConfig.builder()
				.canBeEmpty(false)
				.canBeNull(true)
				.build();
		
		ValidatorConfig validatorConfig = ValidatorConfig.builder()
				.getDefault()
				.stringValidatorConfig(stringValidatorConfig)
				.build();
		
		try {
			ValidatorTestUtils.validateObjectBFS(ferrari, validatorConfig);
		} catch (ValidatorException eBFS) {
			throw new RuntimeException(eBFS);
		}
		
		try {
			ValidatorTestUtils.validateObjectDFS(ferrari, validatorConfig);
		} catch (ValidatorException eDFS) {
			throw new RuntimeException(eDFS);
		}
	}
	
	@Test
	void STRING_CAN_BE_EMPTY() {
		Car ferrari = ObjectValorizator.populateCarEmptyString();
		
		StringValidatorConfig stringValidatorConfig = StringValidatorConfig.builder()
				.canBeEmpty(true)
				.canBeNull(false)
				.build();
		
		ValidatorConfig validatorConfig = ValidatorConfig.builder()
				.getDefault()
				.stringValidatorConfig(stringValidatorConfig)
				.build();
		
		try {
			ValidatorTestUtils.validateObjectBFS(ferrari, validatorConfig);
		} catch (ValidatorException eBFS) {
			throw new RuntimeException(eBFS);
		}
		
		try {
			ValidatorTestUtils.validateObjectDFS(ferrari, validatorConfig);
		} catch (ValidatorException eDFS) {
			throw new RuntimeException(eDFS);
		}
	}
	
	@Test
	void STRING_CAN_BE_NULL_AND_CAN_BE_EMPTY() {
		Car ferrari = ObjectValorizator.populateCarEmptyString();
		ferrari.setModel(null);
		
		StringValidatorConfig stringValidatorConfig = StringValidatorConfig.builder()
				.canBeNull(true)
				.canBeEmpty(true)
				.build();
		
		ValidatorConfig validatorConfig = ValidatorConfig.builder()
				.getDefault()
				.stringValidatorConfig(stringValidatorConfig)
				.build();
		
		try {
			ValidatorTestUtils.validateObjectBFS(ferrari, validatorConfig);
		} catch (ValidatorException eBFS) {
			throw new RuntimeException(eBFS);
		}
		
		try {
			ValidatorTestUtils.validateObjectDFS(ferrari, validatorConfig);
		} catch (ValidatorException eDFS) {
			throw new RuntimeException(eDFS);
		}
	}
	
}
