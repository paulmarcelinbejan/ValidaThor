package io.github.paulmarcelinbejan.toolbox.validathor.bfs.processor;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import io.github.paulmarcelinbejan.toolbox.validathor.CollectionValidathor;
import io.github.paulmarcelinbejan.toolbox.validathor.ValidathorParametrizedType;
import io.github.paulmarcelinbejan.toolbox.validathor.bfs.entities.Car;
import io.github.paulmarcelinbejan.toolbox.validathor.bfs.entities.level1.level2.Headquarters;
import io.github.paulmarcelinbejan.toolbox.validathor.bfs.utils.ObjectValorizator;
import io.github.paulmarcelinbejan.toolbox.validathor.bfs.utils.ValidathorTestUtils;
import io.github.paulmarcelinbejan.toolbox.validathor.exception.ValidathorException;
import io.github.paulmarcelinbejan.toolbox.validathor.impl.ObjectValidathorImpl;
import io.github.paulmarcelinbejan.toolbox.validathor.processor.SkipAfterValidationProcessor;
import io.github.paulmarcelinbejan.toolbox.validathor.processor.SkipBeforeValidationProcessor;
import io.github.paulmarcelinbejan.toolbox.validathor.processor.config.SkipAfterValidationConfig;

class SkipAfterValidationProcessorTest {
	
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
