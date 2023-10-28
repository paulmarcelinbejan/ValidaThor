package com.github.paulmarcelinbejan.toolbox.validathor.bfs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.github.paulmarcelinbejan.toolbox.validathor.ArrayListValidathor;
import com.github.paulmarcelinbejan.toolbox.validathor.LocalDateValidathor;
import com.github.paulmarcelinbejan.toolbox.validathor.MapValidathor;
import com.github.paulmarcelinbejan.toolbox.validathor.StringValidathor;
import com.github.paulmarcelinbejan.toolbox.validathor.Validathor;
import com.github.paulmarcelinbejan.toolbox.validathor.ValidathorParametrizedType;
import com.github.paulmarcelinbejan.toolbox.validathor.bfs.entities.Car;
import com.github.paulmarcelinbejan.toolbox.validathor.bfs.utils.ObjectValorizator;
import com.github.paulmarcelinbejan.toolbox.validathor.bfs.utils.ValidathorTestUtils;
import com.github.paulmarcelinbejan.toolbox.validathor.exception.ValidathorException;

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
		List<ValidathorParametrizedType<?>> validathorsParametrizedType = List.of(new MapValidathor(true), new ArrayListValidathor(true));
		
		ValidathorException eBFS = assertThrows(ValidathorException.class, () -> ValidathorTestUtils.validateObjectBFS(ferrari, validathors, validathorsParametrizedType));
		assertEquals(LocalDateValidathor.class, eBFS.getCausedBy().getClass());
	}
	
	@Test
	void IS_EMPTY() {
		Car ferrari = ObjectValorizator.populateCar();
		ferrari.setModel("");
		
		List<Validathor<?>> validathors = List.of(new StringValidathor());
		List<ValidathorParametrizedType<?>> validathorsParametrizedType = List.of(new MapValidathor(true), new ArrayListValidathor(true));
		
		ValidathorException eBFS = assertThrows(ValidathorException.class, () -> ValidathorTestUtils.validateObjectBFS(ferrari, validathors, validathorsParametrizedType));
		assertEquals(StringValidathor.class, eBFS.getCausedBy().getClass());
	}

}