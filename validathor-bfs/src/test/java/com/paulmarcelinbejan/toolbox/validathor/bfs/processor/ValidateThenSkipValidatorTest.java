package com.paulmarcelinbejan.toolbox.validathor.bfs.processor;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.paulmarcelinbejan.toolbox.validathor.CollectionValidathor;
import com.paulmarcelinbejan.toolbox.validathor.ValidathorParametrizedType;
import com.paulmarcelinbejan.toolbox.validathor.bfs.entities.Car;
import com.paulmarcelinbejan.toolbox.validathor.bfs.entities.level1.level2.Headquarters;
import com.paulmarcelinbejan.toolbox.validathor.bfs.utils.ObjectValorizator;
import com.paulmarcelinbejan.toolbox.validathor.bfs.utils.ValidathorTestUtils;
import com.paulmarcelinbejan.toolbox.validathor.exception.ValidathorException;
import com.paulmarcelinbejan.toolbox.validathor.impl.ObjectValidathorImpl;
import com.paulmarcelinbejan.toolbox.validathor.processor.SkipBeforeValidationProcessor;
import com.paulmarcelinbejan.toolbox.validathor.processor.SkipAfterValidationProcessor;
import com.paulmarcelinbejan.toolbox.validathor.processor.config.SkipAfterValidationConfig;

class ValidateThenSkipValidatorTest {
	
	@Test
	void VALIDATE_THEN_SKIP_CLASS() {
		Car ferrari = ObjectValorizator.populateCar();
		ferrari.getManufacturer().setHeadquarters(null);
		
		SkipAfterValidationConfig skipAfterValidationConfig = new SkipAfterValidationConfig();
		skipAfterValidationConfig.getValidateThenSkipClasses().add(Headquarters.class);
		SkipAfterValidationProcessor skipAfterValidationProcessor = new SkipAfterValidationProcessor(skipAfterValidationConfig);
		
		List<ValidathorParametrizedType<?>> validathorsParametrizedType = List.of(new CollectionValidathor(true));
		
		ValidathorException eBFS = assertThrows(ValidathorException.class, () -> ValidathorTestUtils.validateObjectBFS(ferrari, new SkipBeforeValidationProcessor(), skipAfterValidationProcessor, Collections.emptyList(), validathorsParametrizedType));
		assertEquals(ObjectValidathorImpl.class, eBFS.getCausedBy().getClass());
		
		ferrari.getManufacturer().setHeadquarters(new Headquarters(null));
		
		assertDoesNotThrow(() -> ValidathorTestUtils.validateObjectBFS(ferrari, new SkipBeforeValidationProcessor(), skipAfterValidationProcessor, Collections.emptyList(), validathorsParametrizedType));
	}
	
	@Test
	void VALIDATE_THEN_SKIP_PACKAGE() {
		Car ferrari = ObjectValorizator.populateCar();
		ferrari.getManufacturer().setHeadquarters(null);
		
		SkipAfterValidationConfig skipAfterValidationConfig = new SkipAfterValidationConfig();
		skipAfterValidationConfig.getValidateThenSkipPackages().add("com.paulmarcelinbejan.toolbox.validathor.bfs.entities.level1.level2");
		SkipAfterValidationProcessor skipAfterValidationProcessor = new SkipAfterValidationProcessor(skipAfterValidationConfig);
		
		List<ValidathorParametrizedType<?>> validathorsParametrizedType = List.of(new CollectionValidathor(true));
		
		ValidathorException eBFS = assertThrows(ValidathorException.class, () -> ValidathorTestUtils.validateObjectBFS(ferrari, new SkipBeforeValidationProcessor(), skipAfterValidationProcessor, Collections.emptyList(), validathorsParametrizedType));
		assertEquals(ObjectValidathorImpl.class, eBFS.getCausedBy().getClass());
		
		ferrari.getManufacturer().setHeadquarters(new Headquarters(null));
		
		assertDoesNotThrow(() -> ValidathorTestUtils.validateObjectBFS(ferrari, new SkipBeforeValidationProcessor(), skipAfterValidationProcessor, Collections.emptyList(), validathorsParametrizedType));
	}
	
}
