package io.github.paulmarcelinbejan.toolbox.validathor.bfs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import io.github.paulmarcelinbejan.toolbox.validathor.ArrayListValidathor;
import io.github.paulmarcelinbejan.toolbox.validathor.LocalDateValidathor;
import io.github.paulmarcelinbejan.toolbox.validathor.Validathor;
import io.github.paulmarcelinbejan.toolbox.validathor.ValidathorParameterizedType;
import io.github.paulmarcelinbejan.toolbox.validathor.bfs.entities.Car;
import io.github.paulmarcelinbejan.toolbox.validathor.bfs.utils.ObjectValorizator;
import io.github.paulmarcelinbejan.toolbox.validathor.bfs.utils.ValidathorTestUtils;
import io.github.paulmarcelinbejan.toolbox.validathor.exception.ValidathorException;
import io.github.paulmarcelinbejan.toolbox.validathor.impl.map.NotNullAndNotEmptyAndValidateValueMapValidathor;
import io.github.paulmarcelinbejan.toolbox.validathor.impl.string.NotNullAndNotEmptyStringValidathor;

class ValidathorBFSTest {
	
	@Test
	void ok() throws ValidathorException {
		Car car = ObjectValorizator.populateCar();
		ValidathorTestUtils.validateObjectBFS(car, Collections.emptyList(), Collections.emptyList());
	}
	
	@Test
	void IS_NULL() {
		Car ferrari = ObjectValorizator.populateCar();
		ferrari.getManufacturer().setFoundation(null);
		
		List<Validathor<?>> validathors = List.of(new LocalDateValidathor());
		List<ValidathorParameterizedType<?>> validathorsParameterizedType = List.of(new NotNullAndNotEmptyAndValidateValueMapValidathor(true), new ArrayListValidathor(true));
		
		ValidathorException eBFS = assertThrows(ValidathorException.class, () -> ValidathorTestUtils.validateObjectBFS(ferrari, validathors, validathorsParameterizedType));
		assertEquals(LocalDateValidathor.class, eBFS.getCausedBy().getClass());
	}
	
	@Test
	void IS_EMPTY() {
		Car ferrari = ObjectValorizator.populateCar();
		ferrari.setModel("");
		
		List<Validathor<?>> validathors = List.of(new NotNullAndNotEmptyStringValidathor());
		List<ValidathorParameterizedType<?>> validathorsParameterizedType = List.of(new NotNullAndNotEmptyAndValidateValueMapValidathor(true), new ArrayListValidathor(true));
		
		ValidathorException eBFS = assertThrows(ValidathorException.class, () -> ValidathorTestUtils.validateObjectBFS(ferrari, validathors, validathorsParameterizedType));
		assertEquals(NotNullAndNotEmptyStringValidathor.class, eBFS.getCausedBy().getClass());
	}

}