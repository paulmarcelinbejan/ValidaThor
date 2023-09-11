package com.paulmarcelinbejan.validator.utils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.paulmarcelinbejan.validator.entities.FieldInfo;
import com.paulmarcelinbejan.validator.entities.Info;
import com.paulmarcelinbejan.validator.exception.ExceptionKeys;
import com.paulmarcelinbejan.validator.exception.ValidatorException;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidatorUtils {
	
	@FunctionalInterface
	public interface ConsumerWithException<P1, E extends Exception> {
		void accept(P1 p1) throws E;
	}
	
	@FunctionalInterface
	public interface FunctionWithException<P1, R, E extends Exception> {
		R apply(P1 p1) throws E;
	}
	
	public static void isNull(Object toValidate, String fieldName, String outerObjectStringValue) throws ValidatorException {
		if(toValidate == null) {
			throw new ValidatorException(ExceptionKeys.IS_NULL, fieldName, outerObjectStringValue);
		}
	}
	
	public static FieldInfo buildFieldInfo(Object outerObject, Field field) {
		return new FieldInfo(outerObject, outerObject.toString(), field.getName(), field.getType(), field);
	}
	
	public static List<Field> getFields(final Class<?> clazz) {
		return new ArrayList<>(Arrays.asList(clazz.getDeclaredFields()));
	}
	
	public static Object getObject(final Object outerObject, final Field field) throws ValidatorException {
		field.setAccessible(true);
		try {
			return field.get(outerObject);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new ValidatorException(e);
		}
	}
	
	public static Class<?> getClassTypeFromParameterizedType(final Field field, final int indexParameterizedTypeArgument){
		ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
        return (Class<?>) parameterizedType.getActualTypeArguments()[indexParameterizedTypeArgument];
	}
	
	public static boolean isContainedInSet(Set<String> packages, String clazzPackageName) {
		return packages.stream()
					   .anyMatch(clazzPackageName::startsWith);
	}
	
	public static boolean isToValidateClassPrimitive(Info info) {
		return info.getToValidateClass().isPrimitive();
	}
	
}