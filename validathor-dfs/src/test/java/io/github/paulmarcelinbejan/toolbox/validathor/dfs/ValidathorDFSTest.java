package io.github.paulmarcelinbejan.toolbox.validathor.dfs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import io.github.paulmarcelinbejan.toolbox.validathor.ArrayListValidathor;
import io.github.paulmarcelinbejan.toolbox.validathor.LocalDateValidathor;
import io.github.paulmarcelinbejan.toolbox.validathor.Validathor;
import io.github.paulmarcelinbejan.toolbox.validathor.ValidathorParameterizedType;
import io.github.paulmarcelinbejan.toolbox.validathor.dfs.entities.Car;
import io.github.paulmarcelinbejan.toolbox.validathor.dfs.utils.ObjectValorizator;
import io.github.paulmarcelinbejan.toolbox.validathor.dfs.utils.ValidathorTestUtils;
import io.github.paulmarcelinbejan.toolbox.validathor.exception.ValidathorException;
import io.github.paulmarcelinbejan.toolbox.validathor.impl.map.NotNullAndNotEmptyAndValidateValueMapValidathor;
import io.github.paulmarcelinbejan.toolbox.validathor.impl.string.NotNullAndNotEmptyStringValidathor;

class ValidathorDFSTest {
	
	@Test
	void ok() throws ValidathorException {
		Car car = ObjectValorizator.populateCar();
		ValidathorTestUtils.validateObjectDFS(car, Collections.emptyList(), Collections.emptyList());
	}
	
	@Test
	void IS_NULL() {
		Car ferrari = ObjectValorizator.populateCar();
		ferrari.getManufacturer().setFoundation(null);
		
		List<Validathor<?>> validathors = List.of(new LocalDateValidathor());
		List<ValidathorParameterizedType<?>> validathorsParameterizedType = List.of(new NotNullAndNotEmptyAndValidateValueMapValidathor(true), new ArrayListValidathor(true));
		
		ValidathorException eDFS = assertThrows(ValidathorException.class, () -> ValidathorTestUtils.validateObjectDFS(ferrari, validathors, validathorsParameterizedType));
		assertEquals(LocalDateValidathor.class, eDFS.getCausedBy().getClass());
	}
	
	@Test
	void IS_EMPTY() {
		Car ferrari = ObjectValorizator.populateCar();
		ferrari.setModel("");
		
		List<Validathor<?>> validathors = List.of(new NotNullAndNotEmptyStringValidathor());
		List<ValidathorParameterizedType<?>> validathorsParameterizedType = List.of(new NotNullAndNotEmptyAndValidateValueMapValidathor(true), new ArrayListValidathor(true));
		
		ValidathorException eDFS = assertThrows(ValidathorException.class, () -> ValidathorTestUtils.validateObjectDFS(ferrari, validathors, validathorsParameterizedType));
		assertEquals(NotNullAndNotEmptyStringValidathor.class, eDFS.getCausedBy().getClass());
	}

}