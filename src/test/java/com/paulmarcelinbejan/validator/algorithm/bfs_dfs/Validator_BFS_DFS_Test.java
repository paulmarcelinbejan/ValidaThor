package com.paulmarcelinbejan.validator.algorithm.bfs_dfs;

import static com.paulmarcelinbejan.validator.test.utils.ObjectValorizator.populateCar;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.paulmarcelinbejan.validator.exception.ExceptionKeys;
import com.paulmarcelinbejan.validator.exception.ValidatorException;
import com.paulmarcelinbejan.validator.test.entities.Car;
import com.paulmarcelinbejan.validator.test.utils.ValidatorTestUtils;

class Validator_BFS_DFS_Test {
	
	@Test
	void ok() throws ValidatorException {
		Car car = populateCar();
		ValidatorTestUtils.validateObjectBFS(car);
		ValidatorTestUtils.validateObjectDFS(car);
	}
	
	@Test
	void IS_NULL() {
		Car ferrari = populateCar();
		ferrari.getManufacturer().setFoundation(null);
		
		ValidatorException eBFS = assertThrows(ValidatorException.class, () -> ValidatorTestUtils.validateObjectBFS(ferrari));
		assertEquals(ExceptionKeys.IS_NULL, eBFS.getKey());
		
		ValidatorException eDFS = assertThrows(ValidatorException.class, () -> ValidatorTestUtils.validateObjectDFS(ferrari));
		assertEquals(ExceptionKeys.IS_NULL, eDFS.getKey());
		
		assertEquals(eBFS.getMessage(), eDFS.getMessage());
	}
	
	@Test
	void IS_EMPTY() {
		Car ferrari = populateCar();
		ferrari.setModel("");
		
		ValidatorException eBFS = assertThrows(ValidatorException.class, () -> ValidatorTestUtils.validateObjectBFS(ferrari));
		assertEquals(ExceptionKeys.IS_EMPTY, eBFS.getKey());
		
		ValidatorException eDFS = assertThrows(ValidatorException.class, () -> ValidatorTestUtils.validateObjectDFS(ferrari));
		assertEquals(ExceptionKeys.IS_EMPTY, eDFS.getKey());
		
		assertEquals(eBFS.getMessage(), eDFS.getMessage());
	}

}