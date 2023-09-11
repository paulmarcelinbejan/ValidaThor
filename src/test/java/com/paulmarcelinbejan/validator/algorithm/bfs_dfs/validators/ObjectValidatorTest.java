package com.paulmarcelinbejan.validator.algorithm.bfs_dfs.validators;

import static com.paulmarcelinbejan.validator.test.utils.ObjectValorizator.populateCar;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.paulmarcelinbejan.validator.config.ValidatorConfig;
import com.paulmarcelinbejan.validator.config.configs.ObjectValidatorConfig;
import com.paulmarcelinbejan.validator.exception.ExceptionKeys;
import com.paulmarcelinbejan.validator.exception.ValidatorException;
import com.paulmarcelinbejan.validator.test.entities.Car;
import com.paulmarcelinbejan.validator.test.entities.companies.info.Manufacturer;
import com.paulmarcelinbejan.validator.test.utils.ValidatorTestUtils;

class ObjectValidatorTest {
	
	@Test
	void OBJECT_NULL() {
		Car ferrari = populateCar();
		ferrari.getManufacturer().setFoundation(null);
		
		ValidatorException eBFS = assertThrows(ValidatorException.class, () -> ValidatorTestUtils.validateObjectBFS(ferrari));
		assertEquals(ExceptionKeys.IS_NULL, eBFS.getKey());
		
		ValidatorException eDFS = assertThrows(ValidatorException.class, () -> ValidatorTestUtils.validateObjectDFS(ferrari));
		assertEquals(ExceptionKeys.IS_NULL, eDFS.getKey());
		
		assertEquals(eBFS.getMessage(), eDFS.getMessage());
	}
	
	@Test
	void DO_NOT_VALIDATE_INNER_FIELD_OF_OBJECT() {
		Car ferrari = populateCar();
		ferrari.setManufacturer(new Manufacturer(null, null, null));
		
		ObjectValidatorConfig objectValidatorConfig = ObjectValidatorConfig.builder()
				.canBeNull(false)
				.validateInnerFields(false)
				.build();
		
		ValidatorConfig validatorConfig = ValidatorConfig.builder()
				.getDefault()
				.objectValidatorConfig(objectValidatorConfig)
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
	void OBJECT_CAN_BE_NULL_AND_DO_NOT_VALIDATE_INNER_FIELDS() {
		Car ferrari = populateCar();
		
		ObjectValidatorConfig objectValidatorConfig = ObjectValidatorConfig.builder()
				.canBeNull(true)
				.validateInnerFields(false)
				.build();
		
		ValidatorConfig validatorConfig = ValidatorConfig.builder()
				.getDefault()
				.objectValidatorConfig(objectValidatorConfig)
				.build();
		
		try {
			ferrari.setManufacturer(new Manufacturer(null, null, null));
			ValidatorTestUtils.validateObjectBFS(ferrari, validatorConfig);
			
			ferrari.setManufacturer(null);
			ValidatorTestUtils.validateObjectBFS(ferrari, validatorConfig);
		} catch (ValidatorException eBFS) {
			throw new RuntimeException(eBFS);
		}
		
		try {
			ferrari.setManufacturer(new Manufacturer(null, null, null));
			ValidatorTestUtils.validateObjectDFS(ferrari, validatorConfig);
			
			ferrari.setManufacturer(null);
			ValidatorTestUtils.validateObjectDFS(ferrari, validatorConfig);
		} catch (ValidatorException eDFS) {
			throw new RuntimeException(eDFS);
		}
	}
	
	@Test
	void OBJECT_CAN_BE_NULL_AND_VALIDATE_INNER_FIELDS() {
		Car ferrari = populateCar();
		ferrari.setManufacturer(new Manufacturer(null, null, null));
		
		ObjectValidatorConfig objectValidatorConfig = ObjectValidatorConfig.builder()
				.canBeNull(true)
				.validateInnerFields(true)
				.build();
		
		ValidatorConfig validatorConfig = ValidatorConfig.builder()
				.getDefault()
				.objectValidatorConfig(objectValidatorConfig)
				.build();
		
		ValidatorException eBFS = assertThrows(ValidatorException.class, () -> ValidatorTestUtils.validateObjectBFS(ferrari, validatorConfig));
		assertEquals(ExceptionKeys.IS_NULL, eBFS.getKey());
		
		ValidatorException eDFS = assertThrows(ValidatorException.class, () -> ValidatorTestUtils.validateObjectDFS(ferrari, validatorConfig));
		assertEquals(ExceptionKeys.IS_NULL, eDFS.getKey());
	}
	
	@Test
	void OBJECT_CAN_NOT_BE_NULL_AND_DO_NOT_VALIDATE_INNER_FIELDS() {
		Car ferrari = populateCar();
		ferrari.setManufacturer(new Manufacturer(null, null, null));
		
		ObjectValidatorConfig objectValidatorConfig = ObjectValidatorConfig.builder()
				.canBeNull(false)
				.validateInnerFields(false)
				.build();
		
		ValidatorConfig validatorConfig = ValidatorConfig.builder()
				.getDefault()
				.objectValidatorConfig(objectValidatorConfig)
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
	void PARENT_VALIDATOR_CAN_BE_NULL_AND_VALIDATE_INNER_FIELDS() {
		Car ferrari = populateCar();
		
		ObjectValidatorConfig objectValidatorConfig = ObjectValidatorConfig.builder()
				.canBeNull(true)
				.validateInnerFields(true)
				.build();
		
		ValidatorConfig validatorConfig = ValidatorConfig.builder()
				.getDefault()
				.objectValidatorConfig(objectValidatorConfig)
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
		
		ferrari.getOwners().get(0).setName("");
		
		ValidatorException eBFS = assertThrows(ValidatorException.class, () -> ValidatorTestUtils.validateObjectBFS(ferrari, validatorConfig));
		assertEquals(ExceptionKeys.IS_EMPTY, eBFS.getKey());
		
		ValidatorException eDFS = assertThrows(ValidatorException.class, () -> ValidatorTestUtils.validateObjectDFS(ferrari, validatorConfig));
		assertEquals(ExceptionKeys.IS_EMPTY, eDFS.getKey());
		
	}
	
}
