package com.paulmarcelinbejan.validator.algorithm.bfs_dfs.validators;

import static com.paulmarcelinbejan.validator.test.utils.ObjectValorizator.populateCar;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import com.paulmarcelinbejan.validator.config.ValidatorConfig;
import com.paulmarcelinbejan.validator.config.configs.MapValidatorConfig;
import com.paulmarcelinbejan.validator.exception.ExceptionKeys;
import com.paulmarcelinbejan.validator.exception.ValidatorException;
import com.paulmarcelinbejan.validator.test.entities.Car;
import com.paulmarcelinbejan.validator.test.entities.YEAR;
import com.paulmarcelinbejan.validator.test.utils.ValidatorTestUtils;

class MapValidatorTest {
	
	@Test
	void MAP_NULL() {
		Car ferrari = populateCar();
		ferrari.setCarMaintenance(null);
		
		ValidatorException eBFS = assertThrows(ValidatorException.class, () -> ValidatorTestUtils.validateObjectBFS(ferrari));
		assertEquals(ExceptionKeys.IS_NULL, eBFS.getKey());
		
		ValidatorException eDFS = assertThrows(ValidatorException.class, () -> ValidatorTestUtils.validateObjectDFS(ferrari));
		assertEquals(ExceptionKeys.IS_NULL, eDFS.getKey());
		
		assertEquals(eBFS.getMessage(), eDFS.getMessage());
		
	}
	
	@Test
	void MAP_EMPTY() {
		Car ferrari = populateCar();
		ferrari.setCarMaintenance(new HashMap<>());
		
		ValidatorException eBFS = assertThrows(ValidatorException.class, () -> ValidatorTestUtils.validateObjectBFS(ferrari));
		assertEquals(ExceptionKeys.IS_EMPTY, eBFS.getKey());
		
		ValidatorException eDFS = assertThrows(ValidatorException.class, () -> ValidatorTestUtils.validateObjectDFS(ferrari));
		assertEquals(ExceptionKeys.IS_EMPTY, eDFS.getKey());
		
		assertEquals(eBFS.getMessage(), eDFS.getMessage());
	}
	
	@Test
	void VALIDATE_ELEMENTS_OF_MAP() {
		Car ferrari = populateCar();
		ferrari.getCarMaintenance().get(YEAR._1980).setGarageService(null);
		
		ValidatorException eBFS = assertThrows(ValidatorException.class, () -> ValidatorTestUtils.validateObjectBFS(ferrari));
		assertEquals(ExceptionKeys.IS_NULL, eBFS.getKey());
		
		ValidatorException eDFS = assertThrows(ValidatorException.class, () -> ValidatorTestUtils.validateObjectDFS(ferrari));
		assertEquals(ExceptionKeys.IS_NULL, eDFS.getKey());
		
		assertEquals(eBFS.getMessage(), eDFS.getMessage());
	}
	
	@Test
	void MAP_CAN_BE_NULL() {
		Car ferrari = populateCar();
		ferrari.setCarMaintenance(null);
		
		MapValidatorConfig mapValidatorConfig = MapValidatorConfig.builder()
				.canBeEmpty(false)
				.canBeNull(true)
				.validateElements(true)
				.build();
		
		ValidatorConfig validatorConfig = ValidatorConfig.builder()
				.getDefault()
				.mapValidatorConfig(mapValidatorConfig)
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
	void MAP_CAN_BE_EMPTY() {
		Car ferrari = populateCar();
		ferrari.setCarMaintenance(new HashMap<>());
		
		MapValidatorConfig mapValidatorConfig = MapValidatorConfig.builder()
				.canBeEmpty(true)
				.canBeNull(false)
				.validateElements(true)
				.build();
		
		ValidatorConfig validatorConfig = ValidatorConfig.builder()
				.getDefault()
				.mapValidatorConfig(mapValidatorConfig)
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
	void DO_NOT_VALIDATE_ELEMENTS_OF_MAP() {
		Car ferrari = populateCar();
		ferrari.getCarMaintenance().get(YEAR._1976).setGarageService(null);
		ferrari.getCarMaintenance().put(YEAR._1981, null);
		
		MapValidatorConfig mapValidatorConfig = MapValidatorConfig.builder()
				.getDefault()
				.validateElements(false)
				.build();
		
		ValidatorConfig validatorConfig = ValidatorConfig.builder()
				.getDefault()
				.mapValidatorConfig(mapValidatorConfig)
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
		
		MapValidatorConfig mapValidatorConfig = MapValidatorConfig.builder()
				.canBeNull(true)
				.canBeEmpty(true)
				.validateElements(true)
				.build();
		
		ValidatorConfig validatorConfig = ValidatorConfig.builder()
				.getDefault()
				.mapValidatorConfig(mapValidatorConfig)
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
		ferrari.setCarMaintenance(new HashMap<>());
		
		MapValidatorConfig mapValidatorConfig = MapValidatorConfig.builder()
				.canBeNull(true)
				.canBeEmpty(false)
				.validateElements(false)
				.build();
		
		ValidatorConfig validatorConfig = ValidatorConfig.builder()
				.getDefault()
				.mapValidatorConfig(mapValidatorConfig)
				.build();
		
		ValidatorException eBFS = assertThrows(ValidatorException.class, () -> ValidatorTestUtils.validateObjectBFS(ferrari, validatorConfig));
		assertEquals(ExceptionKeys.IS_EMPTY, eBFS.getKey());
		
		ValidatorException eDFS = assertThrows(ValidatorException.class, () -> ValidatorTestUtils.validateObjectDFS(ferrari, validatorConfig));
		assertEquals(ExceptionKeys.IS_EMPTY, eDFS.getKey());
	}
	
	@Test
	void CAN_BE_NULL_AND_CAN_BE_EMPTY_AND_DO_NOT_VALIDATE_ELEMENTS() {
		Car ferrari = populateCar();
		ferrari.getCarMaintenance().get(YEAR._1980).setGarageService(null);
		
		MapValidatorConfig mapValidatorConfig = MapValidatorConfig.builder()
				.canBeNull(true)
				.canBeEmpty(true)
				.validateElements(false)
				.build();
		
		ValidatorConfig validatorConfig = ValidatorConfig.builder()
				.getDefault()
				.mapValidatorConfig(mapValidatorConfig)
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
		ferrari.getCarMaintenance().get(YEAR._1978).setGarageService("");
		
		MapValidatorConfig mapValidatorConfig = MapValidatorConfig.builder()
				.canBeNull(false)
				.canBeEmpty(true)
				.validateElements(false)
				.build();
		
		ValidatorConfig validatorConfig = ValidatorConfig.builder()
				.getDefault()
				.mapValidatorConfig(mapValidatorConfig)
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
