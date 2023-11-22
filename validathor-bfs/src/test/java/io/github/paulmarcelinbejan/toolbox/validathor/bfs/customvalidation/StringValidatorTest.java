package io.github.paulmarcelinbejan.toolbox.validathor.bfs.customvalidation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import io.github.paulmarcelinbejan.toolbox.validathor.CollectionValidathor;
import io.github.paulmarcelinbejan.toolbox.validathor.Validathor;
import io.github.paulmarcelinbejan.toolbox.validathor.ValidathorParameterizedType;
import io.github.paulmarcelinbejan.toolbox.validathor.bfs.entities.Car;
import io.github.paulmarcelinbejan.toolbox.validathor.bfs.entities.YEAR;
import io.github.paulmarcelinbejan.toolbox.validathor.bfs.utils.ObjectValorizator;
import io.github.paulmarcelinbejan.toolbox.validathor.bfs.utils.ValidathorTestUtils;
import io.github.paulmarcelinbejan.toolbox.validathor.exception.ValidathorException;
import io.github.paulmarcelinbejan.toolbox.validathor.impl.map.NotNullAndNotEmptyAndValidateValueMapValidathor;
import io.github.paulmarcelinbejan.toolbox.validathor.impl.string.NotEmptyStringValidathor;
import io.github.paulmarcelinbejan.toolbox.validathor.impl.string.NotNullAndNotEmptyStringValidathor;
import io.github.paulmarcelinbejan.toolbox.validathor.impl.string.NotNullStringValidathor;

class StringValidatorTest {
	
	@Test
	void STRING_NULL() {
		Car ferrari = ObjectValorizator.populateCar();
		ferrari.getOwners().get(0).setSurname(null);
		
		List<Validathor<?>> validathors = List.of(new NotNullAndNotEmptyStringValidathor());
		List<ValidathorParameterizedType<?>> validathorsParameterizedType = List.of(new CollectionValidathor(true), new NotNullAndNotEmptyAndValidateValueMapValidathor(true));
		
		ValidathorException eBFS = assertThrows(ValidathorException.class, () -> ValidathorTestUtils.validateObjectBFS(ferrari, validathors, validathorsParameterizedType));
		assertEquals(NotNullAndNotEmptyStringValidathor.class, eBFS.getCausedBy().getClass());
	}
	
	@Test
	void STRING_EMPTY() {
		Car ferrari = ObjectValorizator.populateCar();
		ferrari.getCarMaintenance().get(YEAR._1978).setGarageService("");
		
		List<Validathor<?>> validathors = List.of(new NotNullAndNotEmptyStringValidathor());
		List<ValidathorParameterizedType<?>> validathorsParameterizedType = List.of(new NotNullAndNotEmptyAndValidateValueMapValidathor(true));
		
		ValidathorException eBFS = assertThrows(ValidathorException.class, () -> ValidathorTestUtils.validateObjectBFS(ferrari, validathors, validathorsParameterizedType));
		assertEquals(NotNullAndNotEmptyStringValidathor.class, eBFS.getCausedBy().getClass());
	}
	
	@Test
	void STRING_CAN_BE_NULL() {
		Car ferrari = ObjectValorizator.populateCar();
		ferrari.getManufacturer().getHeadquarters().setCity(null);
		
		List<Validathor<?>> validathors = List.of(new NotEmptyStringValidathor());
		
		assertDoesNotThrow(() -> ValidathorTestUtils.validateObjectBFS(ferrari, validathors, Collections.emptyList()));
	}
	
	@Test
	void STRING_CAN_BE_EMPTY() {
		Car ferrari = ObjectValorizator.populateCar();
		ferrari.getManufacturer().getHeadquarters().setCity("");

		List<Validathor<?>> validathors = List.of(new NotNullStringValidathor());
		
		assertDoesNotThrow(() -> ValidathorTestUtils.validateObjectBFS(ferrari, validathors, Collections.emptyList()));
	}
	
	@Test
	void STRING_CAN_BE_NULL_AND_CAN_BE_EMPTY() {
		Car ferrari = ObjectValorizator.populateCar();
		ferrari.setModel(null);
		ferrari.getManufacturer().getHeadquarters().setCity("");

		List<Validathor<?>> validathors = List.of(new StringCanBeNullAndCanBeEmptyValidathor());
		
		assertDoesNotThrow(() -> ValidathorTestUtils.validateObjectBFS(ferrari, validathors, Collections.emptyList()));
	}
	
	public class StringCanBeNullAndCanBeEmptyValidathor extends Validathor<String> {

		public StringCanBeNullAndCanBeEmptyValidathor() {
			super(String.class);
		}

		@Override
		public boolean isValid(String toValidate) {
			return true;
		}
		
	}
	
}
