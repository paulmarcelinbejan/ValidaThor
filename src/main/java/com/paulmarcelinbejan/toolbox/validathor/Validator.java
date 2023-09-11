package com.paulmarcelinbejan.toolbox.validathor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

import com.paulmarcelinbejan.toolbox.validathor.exception.TestException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

/**
 * @author Paul Marcelin Bejan <br>
 *         created on 20/06/2022
 */
@Log
@RequiredArgsConstructor
public class Validator {

	/**
	 * Key: Class name 
	 * Value: List of fields name to exclude from NotNull validation
	 */
	private Map<String, List<String>> excludeFromNotNullValidation;

	/**
	 * Key: Class name 
	 * Value: List of fields name to exclude from NotEmpty validation
	 */
	private Map<String, List<String>> excludeFromNotEmptyValidation;

	/**
	 * Validate output object through DFS (Depth-first search)
	 *
	 * @param root the object on which you want to apply validation
	 * @throws TestException
	 *
	 */
	public final void validateOutputDFS(@NonNull final Object root) throws TestException {
		final Class<?> rootClass = root.getClass();

		validateNotNullField(root, rootClass);

		validateObjectDFS(root, rootClass);

		validateStringField(root, rootClass);

		validateCollectionDFS(root, rootClass);

	}

	/**
	 * Validate output object through BFS (Breadth-first search)
	 *
	 * @param root the object on which you want to apply validation
	 * @throws TestException
	 *
	 */
	public final void validateOutputBFS(@NonNull final Object root) throws TestException {

		final Class<?> rootClass = root.getClass();

		// Object != null, string.length != 0, forEach object of collection
		// validateOutputBFS
		validateObjectBFS(root, rootClass);

		// Populate queue with the root object
		final Queue<ObjectMap> queue = new LinkedList<>();
		queue.add(new ObjectMap(root, getGettersObject(rootClass)));

		while (!queue.isEmpty()) {
			final ObjectMap current = queue.remove();

			// execute validation
			final List<ObjectMap> toExplore = loopCurrentMethods(current);

			// add the object to explore to the queue
			queue.addAll(toExplore);
		}

	}

	/**
	 * Utility method to loop over the methods of the current object <br>
	 * 
	 * @return a list of ObjectMap that must be explored
	 */
	private List<ObjectMap> loopCurrentMethods(final ObjectMap objectMap) throws TestException {
		final List<ObjectMap> toExplore = new ArrayList<>();

		// loop through the methods of the current object
		for (final Method method : objectMap.getMethods()) {

			// obtain the child object invoking getter method
			final Object child = invokeMethod(objectMap.getObject(), method);

			final Class<?> childClass = child.getClass();

			// Object != null, string.length != 0, forEach object of collection
			// validateOutputBFS
			validateObjectBFS(child, childClass);

			// add the child and his gettersObject to the toExplore list
			toExplore.add(new ObjectMap(child, getGettersObject(childClass)));
		}

		return toExplore;
	}

	/**
	 * Complete validation which includes:
	 * <p>
	 * - Validation for object field <br>
	 * - Validation for string field <br>
	 * - Validation for collection field <br>
	 */
	private final void validateObjectBFS(final Object output, final Class<?> outputClass) throws TestException {
		validateNotNullField(output, outputClass);
		validateStringField(output, outputClass);
		validateCollectionBFS(output, outputClass);
	}

	/**
	 * Validation for object field,
	 * <p>
	 * 
	 * @throws TestException when a null value is find.
	 */
	private final void validateNotNullField(final Object object, final Class<?> objectClass) throws TestException {
		final List<Method> gettersObjectsNotString = getGettersObjectNotString(objectClass);

		for (final Method method : gettersObjectsNotString) {
			final Object value = invokeMethod(object, method);

			if (value == null) {
				throw new TestException("VALIDATION FAILED: " + objectClass.getSimpleName() + "." + method.getName()
						+ "() returned null.");
			}
		}
	}

	/**
	 * Validation for string field
	 */
	private final void validateStringField(final Object object, final Class<?> objectClass) throws TestException {
		final List<Method> gettersString = getGettersString(objectClass);

		for (final Method method : gettersString) {

			final Object value = invokeMethod(object, method);

			// exclude field from not null string validation
			if (excludeFromNotNullValidation.containsKey(objectClass.getSimpleName())) {
				List<String> fields = excludeFromNotNullValidation.get(objectClass.getSimpleName());

				if (fields.contains(fieldNameFromGetterName(method.getName()))) {
					log.info("\n\n\nEXCLUDED FIELDS FROM NOT NULL VALIDATION: " + fields + " OF " + objectClass.getSimpleName() + " Class.\n\n");
					continue;
				}
			}

			// exclude field from not empty string validation
			if (excludeFromNotEmptyValidation.containsKey(objectClass.getSimpleName())) {
				List<String> fields = excludeFromNotEmptyValidation.get(objectClass.getSimpleName());

				if (fields.contains(fieldNameFromGetterName(method.getName()))) {
					log.info("\n\n\nEXCLUDED FIELDS FROM NOT EMPTY VALIDATION: " + fields + " OF " + objectClass.getSimpleName() + " Class.\n\n");
					notNullValidation(objectClass, method, value);
					continue;
				}
			}

			// not null validation
			notNullValidation(objectClass, method, value);

			// not empty string validation
			notEmptyStringValidation(objectClass, method, value);

		}
	}

	private void notEmptyStringValidation(final Class<?> objectClass, final Method method, final Object value) throws TestException {
		if (((String) value).isEmpty()) {
			throw new TestException("VALIDATION FAILED: " + objectClass.getSimpleName() + "." + method.getName() + "() returned empty String.");
		}
	}

	private void notNullValidation(final Class<?> objectClass, final Method method, final Object value) throws TestException {
		if (value == null) {
			throw new TestException("VALIDATION FAILED: " + objectClass.getSimpleName() + "." + method.getName() + "() returned null.");
		}
	}

	/**
	 * Validation for collection field, <br>
	 * recursively call to validateOutputBFS
	 */
	private final void validateCollectionBFS(final Object object, final Class<?> objectClass) throws TestException {
		final List<Method> gettersCollection = getGettersCollection(objectClass);

		for (final Method method : gettersCollection) {
			final Object list = invokeMethod(object, method);

			for (final Object obj : (Collection<?>) list) {
				validateOutputBFS(obj);
			}
		}
	}

	/**
	 * Validation for Object field (not String, not Collection) <br>
	 * recursively call to validateOutputDFS
	 */
	private final void validateObjectDFS(final Object object, final Class<?> objectClass) throws TestException {
		final List<Method> gettersObject = getGettersObject(objectClass);

		for (final Method method : gettersObject) {
			final Object value = invokeMethod(object, method);
			validateOutputDFS(value);
		}
	}

	/**
	 * Validation for collection field, <br>
	 * recursively call to validateOutputDFS
	 */
	private final void validateCollectionDFS(final Object object, final Class<?> objectClass) throws TestException {
		final List<Method> gettersCollection = getGettersCollection(objectClass);

		for (final Method method : gettersCollection) {
			final Object list = invokeMethod(object, method);

			for (final Object obj : (Collection<?>) list) {
				validateOutputDFS(obj);
			}
		}
	}

	/**
	 * return getters method for every type of Object (String excluded)
	 */
	private final List<Method> getGettersObjectNotString(final Class<?> outputClass) {
		return Arrays.stream(outputClass.getMethods()).filter(m -> !m.getName().equals("getClass"))
				.filter(m -> m.getName().startsWith("get")).filter(m -> m.getParameterCount() == 0)
				.filter(m -> !m.getReturnType().isPrimitive()).filter(m -> m.getReturnType() != String.class)
				.collect(Collectors.toList());
	}

	/**
	 * return getters method for Object (not String, not Collection)
	 */
	private final List<Method> getGettersObject(final Class<?> clazz) {
		return Arrays.stream(clazz.getMethods()).filter(m -> !m.getName().equals("getClass"))
				.filter(m -> m.getName().startsWith("get")).filter(m -> m.getParameterCount() == 0)
				.filter(m -> !m.getReturnType().isPrimitive()).filter(m -> m.getReturnType() != String.class)
				.filter(m -> !Collection.class.isAssignableFrom(m.getReturnType())).collect(Collectors.toList());
	}

	/**
	 * return getters method for String
	 */
	private List<Method> getGettersString(final Class<?> clazz) {
		return Arrays.stream(clazz.getMethods()).filter(m -> !m.getName().equals("getClass"))
				.filter(m -> m.getName().startsWith("get")).filter(m -> m.getParameterCount() == 0)
				.filter(m -> m.getReturnType() == String.class).collect(Collectors.toList());
	}

	/**
	 * return getters method for Collection
	 */
	private List<Method> getGettersCollection(final Class<?> clazz) {
		return Arrays.stream(clazz.getMethods()).filter(m -> !m.getName().equals("getClass"))
				.filter(m -> m.getName().startsWith("get")).filter(m -> m.getParameterCount() == 0)
				.filter(m -> Collection.class.isAssignableFrom(m.getReturnType())).collect(Collectors.toList());
	}

	/**
	 * Utility method to invoke a method on a object
	 */
	private Object invokeMethod(final Object object, final Method method) throws TestException {
		Object value;
		try {
			value = method.invoke(object);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new TestException(e);
		}
		return value;
	}

	/**
	 * Utility method to transform the name of getter into name of field <br>
	 * Example: getName -> name
	 */
	private String fieldNameFromGetterName(final String getterName) {
		// remove get
		String fieldName = getterName.substring(3);

		// first letter to lower case
		char[] c = fieldName.toCharArray();
		c[0] = Character.toLowerCase(c[0]);
		fieldName = new String(c);

		return fieldName;
	}

	@Data
	@AllArgsConstructor
	private static class ObjectMap {

		String className;
		Object object;
		List<Method> methods;

		public ObjectMap(final Object object, final List<Method> methods) {
			this.object = object;
			this.methods = methods;
		}

	}

}
