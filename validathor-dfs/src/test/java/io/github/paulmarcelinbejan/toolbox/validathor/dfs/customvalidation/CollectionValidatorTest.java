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
import io.github.paulmarcelinbejan.toolbox.validathor.StringValidathor;
import io.github.paulmarcelinbejan.toolbox.validathor.Validathor;
import io.github.paulmarcelinbejan.toolbox.validathor.ValidathorParametrizedType;
import io.github.paulmarcelinbejan.toolbox.validathor.dfs.entities.Car;
import io.github.paulmarcelinbejan.toolbox.validathor.dfs.utils.ObjectValorizator;
import io.github.paulmarcelinbejan.toolbox.validathor.dfs.utils.ValidathorTestUtils;
import io.github.paulmarcelinbejan.toolbox.validathor.exception.ValidathorException;
import io.github.paulmarcelinbejan.toolbox.validathor.impl.ObjectValidathorImpl;

class CollectionValidatorTest {
	
	@Test
	void COLLECTION_NULL() {
		Car ferrari = ObjectValorizator.populateCar();
		ferrari.setOwners(null);
		
		List<ValidathorParametrizedType<?>> validathorsParametrizedType = List.of(new CollectionValidathor(true));
		
		ValidathorException eDFS = assertThrows(ValidathorException.class, () -> ValidathorTestUtils.validateObjectDFS(ferrari, Collections.emptyList(), validathorsParametrizedType));
		assertEquals(CollectionValidathor.class, eDFS.getCausedBy().getClass());
	}
	
	@Test
	void COLLECTION_EMPTY() {
		Car ferrari = ObjectValorizator.populateCar();
		ferrari.setOwners(new ArrayList<>());
		
		List<ValidathorParametrizedType<?>> validathorsParametrizedType = List.of(new CollectionValidathor(true));
		
		ValidathorException eDFS = assertThrows(ValidathorException.class, () -> ValidathorTestUtils.validateObjectDFS(ferrari, Collections.emptyList(), validathorsParametrizedType));
		assertEquals(CollectionValidathor.class, eDFS.getCausedBy().getClass());
	}
	
	@Test
	void VALIDATE_ELEMENTS_OF_COLLECTION() {
		Car ferrari = ObjectValorizator.populateCar();
		ferrari.getOwners().get(1).setAge(null);
		
		List<Validathor<?>> validathors = List.of(new StringValidathor());
		List<ValidathorParametrizedType<?>> validathorsParametrizedType = List.of(new CollectionValidathor(true));
		
		ValidathorException eDFS = assertThrows(ValidathorException.class, () -> ValidathorTestUtils.validateObjectDFS(ferrari, validathors, validathorsParametrizedType));
		assertEquals(ObjectValidathorImpl.class, eDFS.getCausedBy().getClass());
	}
	
	@Test
	void COLLECTION_CAN_BE_NULL() {
		Car ferrari = ObjectValorizator.populateCar();
		ferrari.setOwners(null);
		
		List<Validathor<?>> validathors = List.of(new StringValidathor());
		List<ValidathorParametrizedType<?>> validathorsParametrizedType = List.of(new CollectionCanBeNullButNotEmptyValidathor(true));

		assertDoesNotThrow(() -> ValidathorTestUtils.validateObjectDFS(ferrari, validathors, validathorsParametrizedType));
	}
	
	@SuppressWarnings("rawtypes")
	private static class CollectionCanBeNullButNotEmptyValidathor extends ValidathorParametrizedType<Collection> {

		public CollectionCanBeNullButNotEmptyValidathor(boolean toValidateParametrizedTypeElements) {
			super(Collection.class, toValidateParametrizedTypeElements);
		}

		@Override
		public boolean isValid(Collection toValidate) {
			if(toValidate == null) {
				return true;
			}
			return !toValidate.isEmpty();
		}

		@Override
		public Function<Collection, Collection<?>> parametrizedTypeElementsToValidate() {
			return (collection) -> collection;
		}

	}
	
	@Test
	void COLLECTION_CAN_BE_EMPTY() {
		Car ferrari = ObjectValorizator.populateCar();
		ferrari.setOwners(new ArrayList<>());
		
		List<Validathor<?>> validathors = List.of(new StringValidathor());
		List<ValidathorParametrizedType<?>> validathorsParametrizedType = List.of(new CollectionCanNotBeNullButCanBeEmptyValidathor(true));

		assertDoesNotThrow(() -> ValidathorTestUtils.validateObjectDFS(ferrari, validathors, validathorsParametrizedType));
	}
	
	@SuppressWarnings("rawtypes")
	private static class CollectionCanNotBeNullButCanBeEmptyValidathor extends ValidathorParametrizedType<Collection> {

		public CollectionCanNotBeNullButCanBeEmptyValidathor(boolean toValidateParametrizedTypeElements) {
			super(Collection.class, toValidateParametrizedTypeElements);
		}

		@Override
		public boolean isValid(Collection toValidate) {
			return toValidate != null;
		}

		@Override
		public Function<Collection, Collection<?>> parametrizedTypeElementsToValidate() {
			return (collection) -> collection;
		}

	}
	
	@Test
	void DO_NOT_VALIDATE_ELEMENTS_OF_COLLECTION() {
		Car ferrari = ObjectValorizator.populateCar();
		ferrari.getOwners().get(0).setAge(null);
		ferrari.getOwners().add(null);
		
		List<Validathor<?>> validathors = List.of(new ShortNotNullValidathor());
		List<ValidathorParametrizedType<?>> validathorsParametrizedType = List.of(new CollectionCanNotBeNullButCanBeEmptyValidathor(false));

		assertDoesNotThrow(() -> ValidathorTestUtils.validateObjectDFS(ferrari, validathors, validathorsParametrizedType));
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
		List<ValidathorParametrizedType<?>> validathorsParametrizedType = List.of(new CollectionCanBeNullAndCanBeEmptyValidathor(true));

		ValidathorException eDFS = assertThrows(ValidathorException.class, () -> ValidathorTestUtils.validateObjectDFS(ferrari, validathors, validathorsParametrizedType));
		assertEquals(ShortNotNullValidathor.class, eDFS.getCausedBy().getClass());
		
		ferrari.setOwners(Collections.emptyList());
		
		assertDoesNotThrow(() -> ValidathorTestUtils.validateObjectDFS(ferrari, validathors, validathorsParametrizedType));
		
		ferrari.setOwners(null);
		
		assertDoesNotThrow(() -> ValidathorTestUtils.validateObjectDFS(ferrari, validathors, validathorsParametrizedType));
		
	}
	
	@SuppressWarnings("rawtypes")
	private static class CollectionCanBeNullAndCanBeEmptyValidathor extends ValidathorParametrizedType<Collection> {

		public CollectionCanBeNullAndCanBeEmptyValidathor(boolean toValidateParametrizedTypeElements) {
			super(Collection.class, toValidateParametrizedTypeElements);
		}

		@Override
		public boolean isValid(Collection toValidate) {
			return true;
		}

		@Override
		public Function<Collection, Collection<?>> parametrizedTypeElementsToValidate() {
			return (collection) -> collection;
		}

	}
	
	@Test
	void CAN_BE_NULL_AND_CAN_NOT_BE_EMPTY_AND_DO_NOT_VALIDATE_ELEMENTS() {
		Car ferrari = ObjectValorizator.populateCar();
		ferrari.setOwners(new ArrayList<>());
		
		List<Validathor<?>> validathors = List.of(new ShortNotNullValidathor());
		List<ValidathorParametrizedType<?>> validathorsParametrizedType = List.of(new CollectionCanBeNullButNotEmptyValidathor(false));

		ValidathorException eDFS = assertThrows(ValidathorException.class, () -> ValidathorTestUtils.validateObjectDFS(ferrari, validathors, validathorsParametrizedType));
		assertEquals(CollectionCanBeNullButNotEmptyValidathor.class, eDFS.getCausedBy().getClass());
	}
	
	@Test
	void CAN_BE_NULL_AND_CAN_BE_EMPTY_AND_DO_NOT_VALIDATE_ELEMENTS() {
		Car ferrari = ObjectValorizator.populateCar();
		ferrari.getOwners().get(1).setSurname("");
		
		List<Validathor<?>> validathors = List.of(new StringValidathor());
		List<ValidathorParametrizedType<?>> validathorsParametrizedType = List.of(new CollectionCanBeNullAndCanBeEmptyValidathor(false));

		assertDoesNotThrow(() -> ValidathorTestUtils.validateObjectDFS(ferrari, validathors, validathorsParametrizedType));
	}
	
	@Test
	void CAN_NOT_BE_NULL_AND_CAN_BE_EMPTY_AND_DO_NOT_VALIDATE_ELEMENTS() {
		Car ferrari = ObjectValorizator.populateCar();
		ferrari.getOwners().get(0).setAge(null);
		
		List<Validathor<?>> validathors = List.of(new StringValidathor());
		List<ValidathorParametrizedType<?>> validathorsParametrizedType = List.of(new CollectionCanNotBeNullButCanBeEmptyValidathor(false));

		assertDoesNotThrow(() -> ValidathorTestUtils.validateObjectDFS(ferrari, validathors, validathorsParametrizedType));
	}
	
}

