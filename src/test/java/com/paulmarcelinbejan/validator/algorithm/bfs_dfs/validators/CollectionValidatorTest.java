package com.paulmarcelinbejan.validator.algorithm.bfs_dfs.validators;

import static com.paulmarcelinbejan.validator.test.utils.ObjectValorizator.populateCar;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.paulmarcelinbejan.validator.config.ValidatorConfig;
import com.paulmarcelinbejan.validator.config.configs.CollectionValidatorConfig;
import com.paulmarcelinbejan.validator.exception.ExceptionKeys;
import com.paulmarcelinbejan.validator.exception.ValidatorException;
import com.paulmarcelinbejan.validator.test.entities.Car;
import com.paulmarcelinbejan.validator.test.utils.ValidatorTestUtils;

class CollectionValidatorTest {
	
	@Test
	void COLLECTION_NULL() {
		Car ferrari = populateCar();
		ferrari.setOwners(null);
		
		ValidatorException eBFS = assertThrows(ValidatorException.class, () -> ValidatorTestUtils.validateObjectBFS(ferrari));
		assertEquals(ExceptionKeys.IS_NULL, eBFS.getKey());
		
		ValidatorException eDFS = assertThrows(ValidatorException.class, () -> ValidatorTestUtils.validateObjectDFS(ferrari));
		assertEquals(ExceptionKeys.IS_NULL, eDFS.getKey());
		
		assertEquals(eBFS.getMessage(), eDFS.getMessage());
	}
	
	@Test
	void COLLECTION_EMPTY() {
		Car ferrari = populateCar();
		ferrari.setOwners(new ArrayList<>());
		
		ValidatorException eBFS = assertThrows(ValidatorException.class, () -> ValidatorTestUtils.validateObjectBFS(ferrari));
		assertEquals(ExceptionKeys.IS_EMPTY, eBFS.getKey());
		
		ValidatorException eDFS = assertThrows(ValidatorException.class, () -> ValidatorTestUtils.validateObjectDFS(ferrari));
		assertEquals(ExceptionKeys.IS_EMPTY, eDFS.getKey());
		
		assertEquals(eBFS.getMessage(), eDFS.getMessage());
	}
	
	@Test
	void VALIDATE_ELEMENTS_OF_COLLECTION() {
		Car ferrari = populateCar();
		ferrari.getOwners().get(1).setAge(null);
		
		ValidatorException eBFS = assertThrows(ValidatorException.class, () -> ValidatorTestUtils.validateObjectBFS(ferrari));
		assertEquals(ExceptionKeys.IS_NULL, eBFS.getKey());
		
		ValidatorException eDFS = assertThrows(ValidatorException.class, () -> ValidatorTestUtils.validateObjectDFS(ferrari));
		assertEquals(ExceptionKeys.IS_NULL, eDFS.getKey());
		
		assertEquals(eBFS.getMessage(), eDFS.getMessage());
	}
	
	@Test
	void COLLECTION_CAN_BE_NULL() {
		Car ferrari = populateCar();
		ferrari.setOwners(null);
		
		CollectionValidatorConfig collectionValidatorConfig = CollectionValidatorConfig.builder()
				.canBeEmpty(false)
				.canBeNull(true)
				.validateElements(true)
				.build();
		
		ValidatorConfig validatorConfig = ValidatorConfig.builder()
				.getDefault()
				.collectionValidatorConfig(collectionValidatorConfig)
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
	void COLLECTION_CAN_BE_EMPTY() {
		Car ferrari = populateCar();
		ferrari.setOwners(new ArrayList<>());
		
		CollectionValidatorConfig collectionValidatorConfig = CollectionValidatorConfig.builder()
				.canBeEmpty(true)
				.canBeNull(false)
				.validateElements(true)
				.build();
		
		ValidatorConfig validatorConfig = ValidatorConfig.builder()
				.getDefault()
				.collectionValidatorConfig(collectionValidatorConfig)
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
	void DO_NOT_VALIDATE_ELEMENTS_OF_COLLECTION() {
		Car ferrari = populateCar();
		ferrari.getOwners().get(0).setAge(null);
		ferrari.getOwners().add(null);
		
		CollectionValidatorConfig collectionValidatorConfig = CollectionValidatorConfig.builder()
				.getDefault()
				.validateElements(false)
				.build();
		
		ValidatorConfig validatorConfig = ValidatorConfig.builder()
				.getDefault()
				.collectionValidatorConfig(collectionValidatorConfig)
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
	void CAN_BE_NULL_AND_CAN_BE_EMPTY_AND_VALIDATE_ELEMENTS() {
		Car ferrari = populateCar();
		
		CollectionValidatorConfig collectionValidatorConfig = CollectionValidatorConfig.builder()
				.canBeNull(true)
				.canBeEmpty(true)
				.validateElements(true)
				.build();
		
		ValidatorConfig validatorConfig = ValidatorConfig.builder()
				.getDefault()
				.collectionValidatorConfig(collectionValidatorConfig)
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
	void CAN_BE_NULL_AND_CAN_NOT_BE_EMPTY_AND_DO_NOT_VALIDATE_ELEMENTS() {
		Car ferrari = populateCar();
		ferrari.setOwners(new ArrayList<>());
		
		CollectionValidatorConfig collectionValidatorConfig = CollectionValidatorConfig.builder()
				.canBeNull(true)
				.canBeEmpty(false)
				.validateElements(false)
				.build();
		
		ValidatorConfig validatorConfig = ValidatorConfig.builder()
				.getDefault()
				.collectionValidatorConfig(collectionValidatorConfig)
				.build();
		
		ValidatorException eBFS = assertThrows(ValidatorException.class, () -> ValidatorTestUtils.validateObjectBFS(ferrari, validatorConfig));
		assertEquals(ExceptionKeys.IS_EMPTY, eBFS.getKey());
		
		ValidatorException eDFS = assertThrows(ValidatorException.class, () -> ValidatorTestUtils.validateObjectDFS(ferrari, validatorConfig));
		assertEquals(ExceptionKeys.IS_EMPTY, eDFS.getKey());
	}
	
	@Test
	void CAN_BE_NULL_AND_CAN_BE_EMPTY_AND_DO_NOT_VALIDATE_ELEMENTS() {
		Car ferrari = populateCar();
		ferrari.getOwners().get(1).setSurname("");
		
		CollectionValidatorConfig collectionValidatorConfig = CollectionValidatorConfig.builder()
				.canBeNull(true)
				.canBeEmpty(true)
				.validateElements(false)
				.build();
		
		ValidatorConfig validatorConfig = ValidatorConfig.builder()
				.getDefault()
				.collectionValidatorConfig(collectionValidatorConfig)
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
	void CAN_NOT_BE_NULL_AND_CAN_BE_EMPTY_AND_DO_NOT_VALIDATE_ELEMENTS() {
		Car ferrari = populateCar();
		ferrari.getOwners().get(0).setAge(null);
		
		CollectionValidatorConfig collectionValidatorConfig = CollectionValidatorConfig.builder()
				.canBeNull(false)
				.canBeEmpty(true)
				.validateElements(false)
				.build();
		
		ValidatorConfig validatorConfig = ValidatorConfig.builder()
				.getDefault()
				.collectionValidatorConfig(collectionValidatorConfig)
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

