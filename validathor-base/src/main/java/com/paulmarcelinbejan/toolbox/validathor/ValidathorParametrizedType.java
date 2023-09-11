package com.paulmarcelinbejan.toolbox.validathor;

import java.util.Collection;
import java.util.function.Function;

import lombok.Getter;

/**
 * Example:
 * To validate a Map I will create MapValidathor<Map<?,?>>, the isValid implementation can be for example to check that the maps are not empty.
 * 
 * <pre>{@code
 * 
 * return !map.isEmpty();
 * 
 * }</pre>
 * 
 * What if you want to validate also the values of each entry ? You can set toValidateParametrizedTypeElements to true and then implement the Function that will return 
 * the elements of the map to be validated. It can decide to validate only the Values of the map, or you can validate also the Keys.
 * 
 * <pre>{@code
 * 
 * //function to return values
 * map -> map.values();
 * 
 * //function to return keys and values
 * (map) -> {
 *       List<Object> toValidate = new ArrayList<>();
 *       // Add keys
 *       toValidate.addAll(map.keySet());
 *       // Add values
 *       toValidate.addAll(map.values());
 *       
 *       return toValidate;
 * };
 *
 * }</pre>
 * 
 */
@Getter
public abstract class ValidathorParametrizedType<T> extends Validathor<T> {
	
	protected final boolean toValidateParametrizedTypeElements;
	
	protected ValidathorParametrizedType(Class<T> typeParameterClass, boolean toValidateParametrizedTypeElements) {
		super(typeParameterClass);
		this.toValidateParametrizedTypeElements = toValidateParametrizedTypeElements;
	}
	
	/**
	 * @return a Function that takes the parametrized object and return the elements to be validated.
	 * Must return an empty collection if there aren't elements to be validated.
	 */
	//TODO return of the Function can be a list of a Class that contains a boolean and a Collection<?>, 
	//the boolean field left is element can be null, or better a List<XYZ>
	public abstract Function<T, Collection<?>> parametrizedTypeElementsToValidate();
	 
}
