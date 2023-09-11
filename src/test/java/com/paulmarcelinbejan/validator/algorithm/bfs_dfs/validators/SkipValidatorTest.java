package com.paulmarcelinbejan.validator.algorithm.bfs_dfs.validators;

import static com.paulmarcelinbejan.validator.test.utils.ObjectValorizator.populateCar;

import org.junit.jupiter.api.Test;

import com.paulmarcelinbejan.validator.config.ValidatorConfig;
import com.paulmarcelinbejan.validator.exception.ValidatorException;
import com.paulmarcelinbejan.validator.test.entities.Car;
import com.paulmarcelinbejan.validator.test.entities.companies.Headquarters;
import com.paulmarcelinbejan.validator.test.entities.companies.info.Manufacturer;
import com.paulmarcelinbejan.validator.test.utils.ValidatorTestUtils;

class SkipValidatorTest {
	
	@Test
	void SKIP_CLASS() {
		Car ferrari = populateCar();
		ferrari.getManufacturer().setHeadquarters(null);
		
		ValidatorConfig validatorConfig = ValidatorConfig.builder().getDefault().build();
		validatorConfig.getSkipValidatorConfig().getSkipClasses().add(Headquarters.class);
		
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
	void SKIP_PACKAGE() {
		Car ferrari = populateCar();
		ferrari.getManufacturer().setHeadquarters(null);
		
		ValidatorConfig validatorConfig = ValidatorConfig.builder().getDefault().build();
		validatorConfig.getSkipValidatorConfig().getSkipPackages().add("com.paulmarcelinbejan.validator.test.entities.companies.info");
		
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
	void SKIP_MULTI_PACKAGE() {
		Car ferrari = populateCar();
		ferrari.setManufacturer(new Manufacturer("Ferrari", null, new Headquarters(null)));
		
		ValidatorConfig validatorConfig = ValidatorConfig.builder().getDefault().build();
		validatorConfig.getSkipValidatorConfig().getSkipPackages().add("com.paulmarcelinbejan.validator.test.entities.companies");
		
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
