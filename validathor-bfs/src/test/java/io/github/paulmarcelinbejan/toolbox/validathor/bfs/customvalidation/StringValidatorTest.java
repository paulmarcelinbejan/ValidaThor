package io.github.paulmarcelinbejan.toolbox.validathor.bfs.customvalidation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import io.github.paulmarcelinbejan.toolbox.validathor.CollectionValidathor;
import io.github.paulmarcelinbejan.toolbox.validathor.MapValidathor;
import io.github.paulmarcelinbejan.toolbox.validathor.StringValidathor;
import io.github.paulmarcelinbejan.toolbox.validathor.Validathor;
import io.github.paulmarcelinbejan.toolbox.validathor.ValidathorParameterizedType;
import io.github.paulmarcelinbejan.toolbox.validathor.bfs.entities.Car;
import io.github.paulmarcelinbejan.toolbox.validathor.bfs.entities.YEAR;
import io.github.paulmarcelinbejan.toolbox.validathor.bfs.utils.ObjectValorizator;
import io.github.paulmarcelinbejan.toolbox.validathor.bfs.utils.ValidathorTestUtils;
import io.github.paulmarcelinbejan.toolbox.validathor.exception.ValidathorException;

class StringValidatorTest {
	
	@Test
	void STRING_NULL() {
		Car ferrari = ObjectValorizator.populateCar();
		ferrari.getOwners().get(0).setSurname(null);
		
		List<Validathor<?>> validathors = List.of(new StringValidathor());
		List<ValidathorParameterizedType<?>> validathorsParameterizedType = List.of(new CollectionValidathor(true), new MapValidathor(true));
		
		ValidathorException eBFS = assertThrows(ValidathorException.class, () -> ValidathorTestUtils.validateObjectBFS(ferrari, validathors, validathorsParameterizedType));
		assertEquals(StringValidathor.class, eBFS.getCausedBy().getClass());
	}
	
	@Test
	void STRING_EMPTY() {
		Car ferrari = ObjectValorizator.populateCar();
		ferrari.getCarMaintenance().get(YEAR._1978).setGarageService("");
		
		List<Validathor<?>> validathors = List.of(new StringValidathor());
		List<ValidathorParameterizedType<?>> validathorsParameterizedType = List.of(new MapValidathor(true));
		
		ValidathorException eBFS = assertThrows(ValidathorException.class, () -> ValidathorTestUtils.validateObjectBFS(ferrari, validathors, validathorsParameterizedType));
		assertEquals(StringValidathor.class, eBFS.getCausedBy().getClass());
	}
	
	@Test
	void STRING_CAN_BE_NULL() {
		Car ferrari = ObjectValorizator.populateCar();
		ferrari.getManufacturer().getHeadquarters().setCity(null);
		
		List<Validathor<?>> validathors = List.of(new StringCanBeNullButNotEmptyValidathor());
		
		assertDoesNotThrow(() -> ValidathorTestUtils.validateObjectBFS(ferrari, validathors, Collections.emptyList()));
	}
	
	public class StringCanBeNullButNotEmptyValidathor extends Validathor<String> {

		public StringCanBeNullButNotEmptyValidathor() {
			super(String.class);
		}

		@Override
		public boolean isValid(String toValidate) {
			if(toValidate == null) {
				return true;
			}
			return !toValidate.isEmpty();
		}
		
	}
	
	@Test
	void STRING_CAN_BE_EMPTY() {
		Car ferrari = ObjectValorizator.populateCar();
		ferrari.getManufacturer().getHeadquarters().setCity("");

		List<Validathor<?>> validathors = List.of(new StringCanNotBeNullButCanBeEmptyValidathor());
		
		assertDoesNotThrow(() -> ValidathorTestUtils.validateObjectBFS(ferrari, validathors, Collections.emptyList()));
	}
	
	public class StringCanNotBeNullButCanBeEmptyValidathor extends Validathor<String> {

		public StringCanNotBeNullButCanBeEmptyValidathor() {
			super(String.class);
		}

		@Override
		public boolean isValid(String toValidate) {
			return toValidate != null;
		}
		
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
