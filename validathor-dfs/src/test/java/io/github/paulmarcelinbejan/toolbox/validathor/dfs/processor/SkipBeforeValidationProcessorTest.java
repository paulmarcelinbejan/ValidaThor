package io.github.paulmarcelinbejan.toolbox.validathor.dfs.processor;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import io.github.paulmarcelinbejan.toolbox.validathor.CollectionValidathor;
import io.github.paulmarcelinbejan.toolbox.validathor.ValidathorParametrizedType;
import io.github.paulmarcelinbejan.toolbox.validathor.dfs.entities.Car;
import io.github.paulmarcelinbejan.toolbox.validathor.dfs.entities.level1.Manufacturer;
import io.github.paulmarcelinbejan.toolbox.validathor.dfs.entities.level1.level2.Headquarters;
import io.github.paulmarcelinbejan.toolbox.validathor.dfs.utils.ObjectValorizator;
import io.github.paulmarcelinbejan.toolbox.validathor.dfs.utils.ValidathorTestUtils;
import io.github.paulmarcelinbejan.toolbox.validathor.processor.SkipAfterValidationProcessor;
import io.github.paulmarcelinbejan.toolbox.validathor.processor.SkipBeforeValidationProcessor;
import io.github.paulmarcelinbejan.toolbox.validathor.processor.config.SkipBeforeValidationConfig;
import lombok.AllArgsConstructor;
import lombok.Data;

class SkipBeforeValidationProcessorTest {
	
	@Test
	void SKIP_CLASS() {		
		Car ferrari = ObjectValorizator.populateCar();
		ferrari.getManufacturer().setHeadquarters(null);
		
		SkipBeforeValidationConfig skipBeforeValidationConfig = new SkipBeforeValidationConfig();
		skipBeforeValidationConfig.getBeforeValidationSkipClasses().add(Headquarters.class);
		SkipBeforeValidationProcessor skipBeforeValidationProcessor = new SkipBeforeValidationProcessor(skipBeforeValidationConfig);
		
		List<ValidathorParametrizedType<?>> validathorsParametrizedType = List.of(new CollectionValidathor(true));
		
		assertDoesNotThrow(() -> ValidathorTestUtils.validateObjectDFS(ferrari, skipBeforeValidationProcessor, new SkipAfterValidationProcessor(), Collections.emptyList(), validathorsParametrizedType));
	}
	
	@Test
	void SKIP_PACKAGE() {
		Car ferrari = ObjectValorizator.populateCar();
		ferrari.getManufacturer().setHeadquarters(null);
		
		SkipBeforeValidationConfig skipBeforeValidationConfig = new SkipBeforeValidationConfig();
		skipBeforeValidationConfig.getBeforeValidationSkipPackages().add("io.github.paulmarcelinbejan.toolbox.validathor.dfs.entities.level1.level2");
		SkipBeforeValidationProcessor skipBeforeValidationProcessor = new SkipBeforeValidationProcessor(skipBeforeValidationConfig);
		
		List<ValidathorParametrizedType<?>> validathorsParametrizedType = List.of(new CollectionValidathor(true));
		
		assertDoesNotThrow(() -> ValidathorTestUtils.validateObjectDFS(ferrari, skipBeforeValidationProcessor, new SkipAfterValidationProcessor(), Collections.emptyList(), validathorsParametrizedType));
	}
	
	@Test
	void SKIP_MULTI_PACKAGE() {
		
		Headquarters headquarters = new Headquarters(null);
		Manufacturer manufacturer = new Manufacturer("Ferrari", null, headquarters);
		
		Something something = new Something(manufacturer, headquarters);
		
		SkipBeforeValidationConfig skipBeforeValidationConfig = new SkipBeforeValidationConfig();
		skipBeforeValidationConfig.getBeforeValidationSkipPackages().add("io.github.paulmarcelinbejan.toolbox.validathor.dfs.entities.level1");
		SkipBeforeValidationProcessor skipBeforeValidationProcessor = new SkipBeforeValidationProcessor(skipBeforeValidationConfig);
		
		List<ValidathorParametrizedType<?>> validathorsParametrizedType = List.of(new CollectionValidathor(true));
		
		assertDoesNotThrow(() -> ValidathorTestUtils.validateObjectDFS(something, skipBeforeValidationProcessor, new SkipAfterValidationProcessor(), Collections.emptyList(), validathorsParametrizedType));
	}
	
	@Data
	@AllArgsConstructor
	public static class Something {
		private Manufacturer manufacturer;
		private Headquarters headquarters;
	}
	
}
