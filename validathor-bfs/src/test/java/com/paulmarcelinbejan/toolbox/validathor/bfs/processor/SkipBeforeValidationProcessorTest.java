package com.paulmarcelinbejan.toolbox.validathor.bfs.processor;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.paulmarcelinbejan.toolbox.validathor.CollectionValidathor;
import com.paulmarcelinbejan.toolbox.validathor.ValidathorParametrizedType;
import com.paulmarcelinbejan.toolbox.validathor.bfs.entities.Car;
import com.paulmarcelinbejan.toolbox.validathor.bfs.entities.level1.Manufacturer;
import com.paulmarcelinbejan.toolbox.validathor.bfs.entities.level1.level2.Headquarters;
import com.paulmarcelinbejan.toolbox.validathor.bfs.utils.ObjectValorizator;
import com.paulmarcelinbejan.toolbox.validathor.bfs.utils.ValidathorTestUtils;
import com.paulmarcelinbejan.toolbox.validathor.processor.SkipBeforeValidationProcessor;
import com.paulmarcelinbejan.toolbox.validathor.processor.SkipAfterValidationProcessor;
import com.paulmarcelinbejan.toolbox.validathor.processor.config.SkipBeforeValidationConfig;

import lombok.AllArgsConstructor;
import lombok.Data;

class SkipBeforeValidationProcessorTest {
	
	@Test
	void SKIP_CLASS() {		
		Car ferrari = ObjectValorizator.populateCar();
		ferrari.getManufacturer().setHeadquarters(null);
		
		SkipBeforeValidationConfig skipBeforeValidationConfig = new SkipBeforeValidationConfig();
		skipBeforeValidationConfig.getSkipClasses().add(Headquarters.class);
		SkipBeforeValidationProcessor skipBeforeValidationProcessor = new SkipBeforeValidationProcessor(skipBeforeValidationConfig);
		
		List<ValidathorParametrizedType<?>> validathorsParametrizedType = List.of(new CollectionValidathor(true));
		
		assertDoesNotThrow(() -> ValidathorTestUtils.validateObjectBFS(ferrari, skipBeforeValidationProcessor, new SkipAfterValidationProcessor(), Collections.emptyList(), validathorsParametrizedType));
	}
	
	@Test
	void SKIP_PACKAGE() {
		Car ferrari = ObjectValorizator.populateCar();
		ferrari.getManufacturer().setHeadquarters(null);
		
		SkipBeforeValidationConfig skipBeforeValidationConfig = new SkipBeforeValidationConfig();
		skipBeforeValidationConfig.getSkipPackages().add("com.paulmarcelinbejan.toolbox.validathor.bfs.entities.level1.level2");
		SkipBeforeValidationProcessor skipBeforeValidationProcessor = new SkipBeforeValidationProcessor(skipBeforeValidationConfig);
		
		List<ValidathorParametrizedType<?>> validathorsParametrizedType = List.of(new CollectionValidathor(true));
		
		assertDoesNotThrow(() -> ValidathorTestUtils.validateObjectBFS(ferrari, skipBeforeValidationProcessor, new SkipAfterValidationProcessor(), Collections.emptyList(), validathorsParametrizedType));
	}
	
	@Test
	void SKIP_MULTI_PACKAGE() {
		
		Headquarters headquarters = new Headquarters(null);
		Manufacturer manufacturer = new Manufacturer("Ferrari", null, headquarters);
		
		Something something = new Something(manufacturer, headquarters);
		
		SkipBeforeValidationConfig skipBeforeValidationConfig = new SkipBeforeValidationConfig();
		skipBeforeValidationConfig.getSkipPackages().add("com.paulmarcelinbejan.toolbox.validathor.bfs.entities.level1");
		SkipBeforeValidationProcessor skipBeforeValidationProcessor = new SkipBeforeValidationProcessor(skipBeforeValidationConfig);
		
		List<ValidathorParametrizedType<?>> validathorsParametrizedType = List.of(new CollectionValidathor(true));
		
		assertDoesNotThrow(() -> ValidathorTestUtils.validateObjectBFS(something, skipBeforeValidationProcessor, new SkipAfterValidationProcessor(), Collections.emptyList(), validathorsParametrizedType));
	}
	
	@Data
	@AllArgsConstructor
	public static class Something {
		private Manufacturer manufacturer;
		private Headquarters headquarters;
	}
	
}
