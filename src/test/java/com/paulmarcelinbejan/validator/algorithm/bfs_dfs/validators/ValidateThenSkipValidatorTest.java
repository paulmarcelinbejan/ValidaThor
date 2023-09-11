package com.paulmarcelinbejan.validator.algorithm.bfs_dfs.validators;

import static com.paulmarcelinbejan.validator.test.utils.ObjectValorizator.populateCar;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.paulmarcelinbejan.validator.config.ValidatorConfig;
import com.paulmarcelinbejan.validator.exception.ExceptionKeys;
import com.paulmarcelinbejan.validator.exception.ValidatorException;
import com.paulmarcelinbejan.validator.test.entities.Car;
import com.paulmarcelinbejan.validator.test.entities.companies.Headquarters;
import com.paulmarcelinbejan.validator.test.utils.ValidatorTestUtils;

class ValidateThenSkipValidatorTest {
	
	@Test
	void VALIDATE_THEN_SKIP_CLASS() {
		Car ferrari = populateCar();
		ferrari.getManufacturer().setHeadquarters(null);
		
		ValidatorConfig validatorConfig = ValidatorConfig.builder().getDefault().build();
		validatorConfig.getSkipValidatorConfig().getSkipClasses().add(Headquarters.class);
		
		ValidatorException eBFS = assertThrows(ValidatorException.class, () -> ValidatorTestUtils.validateObjectBFS(ferrari));
		assertEquals(ExceptionKeys.IS_NULL, eBFS.getKey());
		
		ValidatorException eDFS = assertThrows(ValidatorException.class, () -> ValidatorTestUtils.validateObjectDFS(ferrari));
		assertEquals(ExceptionKeys.IS_NULL, eDFS.getKey());
		
		assertEquals(eBFS.getMessage(), eDFS.getMessage());
		
		ferrari.getManufacturer().setHeadquarters(new Headquarters(null));
		
		try {
			ValidatorTestUtils.validateObjectBFS(ferrari, validatorConfig);
		} catch (ValidatorException eBFS_) {
			throw new RuntimeException(eBFS_);
		}
		
		try {
			ValidatorTestUtils.validateObjectDFS(ferrari, validatorConfig);
		} catch (ValidatorException eDFS_) {
			throw new RuntimeException(eDFS_);
		}
	}
	
	@Test
	void VALIDATE_THEN_SKIP_PACKAGE() {
		Car ferrari = populateCar();
		ferrari.getManufacturer().setHeadquarters(null);
		
		ValidatorConfig validatorConfig = ValidatorConfig.builder().getDefault().build();
		validatorConfig.getSkipValidatorConfig().getSkipPackages().add("com.paulmarcelinbejan.validator.test.entities.companies.info");
		
		ValidatorException eBFS = assertThrows(ValidatorException.class, () -> ValidatorTestUtils.validateObjectBFS(ferrari));
		assertEquals(ExceptionKeys.IS_NULL, eBFS.getKey());
		
		ValidatorException eDFS = assertThrows(ValidatorException.class, () -> ValidatorTestUtils.validateObjectDFS(ferrari));
		assertEquals(ExceptionKeys.IS_NULL, eDFS.getKey());
		
		assertEquals(eBFS.getMessage(), eDFS.getMessage());
		
		ferrari.getManufacturer().setHeadquarters(new Headquarters(null));
		
		try {
			ValidatorTestUtils.validateObjectBFS(ferrari, validatorConfig);
		} catch (ValidatorException eBFS_) {
			throw new RuntimeException(eBFS_);
		}
		
		try {
			ValidatorTestUtils.validateObjectDFS(ferrari, validatorConfig);
		} catch (ValidatorException eDFS_) {
			throw new RuntimeException(eDFS_);
		}
	}
	
}
