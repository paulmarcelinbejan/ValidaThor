package io.github.paulmarcelinbejan.toolbox.validathor.dfs.customvalidation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

import io.github.paulmarcelinbejan.toolbox.validathor.CollectionValidathor;
import io.github.paulmarcelinbejan.toolbox.validathor.Validathor;
import io.github.paulmarcelinbejan.toolbox.validathor.ValidathorParameterizedType;
import io.github.paulmarcelinbejan.toolbox.validathor.dfs.entities.Car;
import io.github.paulmarcelinbejan.toolbox.validathor.dfs.utils.ObjectValorizator;
import io.github.paulmarcelinbejan.toolbox.validathor.dfs.utils.ValidathorTestUtils;
import io.github.paulmarcelinbejan.toolbox.validathor.exception.ValidathorException;
import io.github.paulmarcelinbejan.toolbox.validathor.impl.object.NotNullAndValidateInnerFieldsObjectValidathor;
import io.github.paulmarcelinbejan.toolbox.validathor.impl.string.NotNullAndNotEmptyStringValidathor;

class CollectionValidatorTest {
	
	@Test
	void COLLECTION_NULL() {
		Car ferrari = ObjectValorizator.populateCar();
		ferrari.setOwners(null);
		
		List<ValidathorParameterizedType<?>> validathorsParameterizedType = List.of(new CollectionValidathor(true));
		
		ValidathorException eDFS = assertThrows(ValidathorException.class, () -> ValidathorTestUtils.validateObjectDFS(ferrari, Collections.emptyList(), validathorsParameterizedType));
		assertEquals(CollectionValidathor.class, eDFS.getCausedBy().getClass());
	}
	
	@Test
	void COLLECTION_EMPTY() {
		Car ferrari = ObjectValorizator.populateCar();
		ferrari.setOwners(new ArrayList<>());
		
		List<ValidathorParameterizedType<?>> validathorsParameterizedType = List.of(new CollectionValidathor(true));
		
		ValidathorException eDFS = assertThrows(ValidathorException.class, () -> ValidathorTestUtils.validateObjectDFS(ferrari, Collections.emptyList(), validathorsParameterizedType));
		assertEquals(CollectionValidathor.class, eDFS.getCausedBy().getClass());
	}
	
	@Test
	void VALIDATE_ELEMENTS_OF_COLLECTION() {
		Car ferrari = ObjectValorizator.populateCar();
		ferrari.getOwners().get(1).setAge(null);
		
		List<Validathor<?>> validathors = List.of(new NotNullAndNotEmptyStringValidathor());
		List<ValidathorParameterizedType<?>> validathorsParameterizedType = List.of(new CollectionValidathor(true));
		
		ValidathorException eDFS = assertThrows(ValidathorException.class, () -> ValidathorTestUtils.validateObjectDFS(ferrari, validathors, validathorsParameterizedType));
		assertEquals(NotNullAndValidateInnerFieldsObjectValidathor.class, eDFS.getCausedBy().getClass());
	}
	
	@Test
	void COLLECTION_CAN_BE_NULL() {
		Car ferrari = ObjectValorizator.populateCar();
		ferrari.setOwners(null);
		
		List<Validathor<?>> validathors = List.of(new NotNullAndNotEmptyStringValidathor());
		List<ValidathorParameterizedType<?>> validathorsParameterizedType = List.of(new CollectionCanBeNullButNotEmptyValidathor(true));

		assertDoesNotThrow(() -> ValidathorTestUtils.validateObjectDFS(ferrari, validathors, validathorsParameterizedType));
	}
	
	@SuppressWarnings("rawtypes")
	private static class CollectionCanBeNullButNotEmptyValidathor extends ValidathorParameterizedType<Collection> {

		public CollectionCanBeNullButNotEmptyValidathor(boolean toValidateParameterizedTypeElements) {
			super(Collection.class, toValidateParameterizedTypeElements);
		}

		@Override
		public boolean isValid(Collection toValidate) {
			if(toValidate == null) {
				return true;
			}
			return !toValidate.isEmpty();
		}

		@Override
		public Function<Collection, Collection<?>> parameterizedTypeElementsToValidate() {
			return (collection) -> collection;
		}

	}
	
	@Test
	void COLLECTION_CAN_BE_EMPTY() {
		Car ferrari = ObjectValorizator.populateCar();
		ferrari.setOwners(new ArrayList<>());
		
		List<Validathor<?>> validathors = List.of(new NotNullAndNotEmptyStringValidathor());
		List<ValidathorParameterizedType<?>> validathorsParameterizedType = List.of(new CollectionCanNotBeNullButCanBeEmptyValidathor(true));

		assertDoesNotThrow(() -> ValidathorTestUtils.validateObjectDFS(ferrari, validathors, validathorsParameterizedType));
	}
	
	@SuppressWarnings("rawtypes")
	private static class CollectionCanNotBeNullButCanBeEmptyValidathor extends ValidathorParameterizedType<Collection> {

		public CollectionCanNotBeNullButCanBeEmptyValidathor(boolean toValidateParameterizedTypeElements) {
			super(Collection.class, toValidateParameterizedTypeElements);
		}

		@Override
		public boolean isValid(Collection toValidate) {
			return toValidate != null;
		}

		@Override
		public Function<Collection, Collection<?>> parameterizedTypeElementsToValidate() {
			return (collection) -> collection;
		}

	}
	
	@Test
	void DO_NOT_VALIDATE_ELEMENTS_OF_COLLECTION() {
		Car ferrari = ObjectValorizator.populateCar();
		ferrari.getOwners().get(0).setAge(null);
		ferrari.getOwners().add(null);
		
		List<Validathor<?>> validathors = List.of(new ShortNotNullValidathor());
		List<ValidathorParameterizedType<?>> validathorsParameterizedType = List.of(new CollectionCanNotBeNullButCanBeEmptyValidathor(false));

		assertDoesNotThrow(() -> ValidathorTestUtils.validateObjectDFS(ferrari, validathors, validathorsParameterizedType));
	}
	
	private static class ShortNotNullValidathor extends Validathor<Short> {

		public ShortNotNullValidathor() {
			super(Short.class);
		}

		@Override
		public boolean isValid(Short toValidate) {
			return toValidate != null;
		}

	}
	
	@Test
	void CAN_BE_NULL_AND_CAN_BE_EMPTY_AND_VALIDATE_ELEMENTS() {
		Car ferrari = ObjectValorizator.populateCar();
		ferrari.getOwners().get(0).setAge(null);
		
		List<Validathor<?>> validathors = List.of(new ShortNotNullValidathor());
		List<ValidathorParameterizedType<?>> validathorsParameterizedType = List.of(new CollectionCanBeNullAndCanBeEmptyValidathor(true));

		ValidathorException eDFS = assertThrows(ValidathorException.class, () -> ValidathorTestUtils.validateObjectDFS(ferrari, validathors, validathorsParameterizedType));
		assertEquals(ShortNotNullValidathor.class, eDFS.getCausedBy().getClass());
		
		ferrari.setOwners(Collections.emptyList());
		
		assertDoesNotThrow(() -> ValidathorTestUtils.validateObjectDFS(ferrari, validathors, validathorsParameterizedType));
		
		ferrari.setOwners(null);
		
		assertDoesNotThrow(() -> ValidathorTestUtils.validateObjectDFS(ferrari, validathors, validathorsParameterizedType));
		
	}
	
	@SuppressWarnings("rawtypes")
	private static class CollectionCanBeNullAndCanBeEmptyValidathor extends ValidathorParameterizedType<Collection> {

		public CollectionCanBeNullAndCanBeEmptyValidathor(boolean toValidateParameterizedTypeElements) {
			super(Collection.class, toValidateParameterizedTypeElements);
		}

		@Override
		public boolean isValid(Collection toValidate) {
			return true;
		}

		@Override
		public Function<Collection, Collection<?>> parameterizedTypeElementsToValidate() {
			return (collection) -> collection;
		}

	}
	
	@Test
	void CAN_BE_NULL_AND_CAN_NOT_BE_EMPTY_AND_DO_NOT_VALIDATE_ELEMENTS() {
		Car ferrari = ObjectValorizator.populateCar();
		ferrari.setOwners(new ArrayList<>());
		
		List<Validathor<?>> validathors = List.of(new ShortNotNullValidathor());
		List<ValidathorParameterizedType<?>> validathorsParameterizedType = List.of(new CollectionCanBeNullButNotEmptyValidathor(false));

		ValidathorException eDFS = assertThrows(ValidathorException.class, () -> ValidathorTestUtils.validateObjectDFS(ferrari, validathors, validathorsParameterizedType));
		assertEquals(CollectionCanBeNullButNotEmptyValidathor.class, eDFS.getCausedBy().getClass());
	}
	
	@Test
	void CAN_BE_NULL_AND_CAN_BE_EMPTY_AND_DO_NOT_VALIDATE_ELEMENTS() {
		Car ferrari = ObjectValorizator.populateCar();
		ferrari.getOwners().get(1).setSurname("");
		
		List<Validathor<?>> validathors = List.of(new NotNullAndNotEmptyStringValidathor());
		List<ValidathorParameterizedType<?>> validathorsParameterizedType = List.of(new CollectionCanBeNullAndCanBeEmptyValidathor(false));

		assertDoesNotThrow(() -> ValidathorTestUtils.validateObjectDFS(ferrari, validathors, validathorsParameterizedType));
	}
	
	@Test
	void CAN_NOT_BE_NULL_AND_CAN_BE_EMPTY_AND_DO_NOT_VALIDATE_ELEMENTS() {
		Car ferrari = ObjectValorizator.populateCar();
		ferrari.getOwners().get(0).setAge(null);
		
		List<Validathor<?>> validathors = List.of(new NotNullAndNotEmptyStringValidathor());
		List<ValidathorParameterizedType<?>> validathorsParameterizedType = List.of(new CollectionCanNotBeNullButCanBeEmptyValidathor(false));

		assertDoesNotThrow(() -> ValidathorTestUtils.validateObjectDFS(ferrari, validathors, validathorsParameterizedType));
	}
	
}

