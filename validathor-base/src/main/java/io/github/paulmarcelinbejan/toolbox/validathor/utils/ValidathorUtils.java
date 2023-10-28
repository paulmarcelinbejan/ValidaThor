package io.github.paulmarcelinbejan.toolbox.validathor.utils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import io.github.paulmarcelinbejan.toolbox.validathor.Validathor;
import io.github.paulmarcelinbejan.toolbox.validathor.ValidathorParametrizedType;
import io.github.paulmarcelinbejan.toolbox.validathor.info.Info;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidathorUtils {
	
	public static Object getObject(final Object outerObject, final Field field) {
		field.setAccessible(true);
		try {
			return field.get(outerObject);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static List<Field> getFields(final Class<?> clazz) {
		return new ArrayList<>(Arrays.asList(clazz.getDeclaredFields()));
	}
	
	public static Class<?> getClassTypeFromParameterizedType(final Field field, final int indexParameterizedTypeArgument){
		ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
        return (Class<?>) parameterizedType.getActualTypeArguments()[indexParameterizedTypeArgument];
	}
	
	public static Info buildInfo(Object outerObject, Field field) {
		return new Info(outerObject, outerObject.toString(), field.getType(), field.getName(), getObject(outerObject, field));
	}
	
	public static Info buildInfo(Object outerObject, Object object, String fieldName) {
		return new Info(outerObject, outerObject.toString(), object.getClass(), fieldName, object);
	}
	
	public static boolean isToValidateClassPrimitive(Info info) {
		return info.getToValidateClass().isPrimitive();
	}
	
	public static boolean isParameterizedType(Class<?> clazz) {
		TypeVariable<?>[] genericTypes = clazz.getTypeParameters();
		return genericTypes.length > 0;
    }
	
	/**
	 * @return the targetClass if is present into the classList.
	 */
	public static Optional<Class<?>> getClassFromList(Class<?> targetClass, Set<Class<?>> classList) {
		return classList.parallelStream()
						.filter(clazz -> clazz.equals(targetClass))
						.findAny();
	}
	
	/**
	 * @return the most generic classes that is assignable from the targetClass.
	 */
	public static Optional<Class<?>> getMostGenericClassThatIsAssignableFrom(Class<?> targetClass, Set<Class<?>> classList) {
		
		// If the current class is the target class, return it.
		Optional<Class<?>> match = classList.parallelStream().filter(clazz -> clazz.equals(targetClass)).findAny();
        if(match.isPresent()) {
        	return match;
        }
        
        // Store assignable classes from targetClass
        List<Class<?>> assignableClasses = classList.stream().filter(clazz -> clazz != Object.class && clazz.isAssignableFrom(targetClass)).toList();

        // if there is at least one assignable class for the target class
        if(!assignableClasses.isEmpty()) {
        	
        	if(assignableClasses.size() == 1) {
        		return Optional.of(assignableClasses.get(0));
        	}
        	
        	// check which one is the most generic, otherwise return the first
        	Class<?> mostGenericClass = assignableClasses.get(0);
        	for(Class<?> clazz : assignableClasses) {
        		if(clazz.isAssignableFrom(mostGenericClass)) {
        			mostGenericClass = clazz;
        		}
        	}
        	
        	return Optional.of(mostGenericClass);
        	
        }
        
        // If the target class is not found, return an empty Optional.
        return Optional.empty();
    }
	
	public static Validathor<?> getCompatibleValidathor(Info info, Map<Class<?>, Validathor<?>> mapValidathors, Map<Class<?>, Validathor<?>> cacheMapCompatibleValidathors) {
		Validathor<?> compatibleValidathor = cacheMapCompatibleValidathors.get(info.getToValidateClass());
		
		if(compatibleValidathor != null) {
			return compatibleValidathor;
		}
		
		Optional<Class<?>> classAssignableFrom = getMostGenericClassThatIsAssignableFrom(info.getToValidateClass(), mapValidathors.keySet());
		
		if(classAssignableFrom.isPresent()) {
			compatibleValidathor = mapValidathors.get(classAssignableFrom.get());
			cacheMapCompatibleValidathors.put(info.getToValidateClass(), compatibleValidathor);
		}
		
		return compatibleValidathor;
	}
	
	public static ValidathorParametrizedType<?> getCompatibleValidathorParametrizedType(Info info, Map<Class<?>, ValidathorParametrizedType<?>> mapValidathorsParametrizedType, Map<Class<?>, ValidathorParametrizedType<?>> cacheMapCompatibleValidathorsParametrizedType) {
		ValidathorParametrizedType<?> compatibleValidathorParametrizedType = cacheMapCompatibleValidathorsParametrizedType.get(info.getToValidateClass());
		
		if(compatibleValidathorParametrizedType != null) {
			return compatibleValidathorParametrizedType;
		}
		
		Optional<Class<?>> classAssignableFrom = getMostGenericClassThatIsAssignableFrom(info.getToValidateClass(), mapValidathorsParametrizedType.keySet());
		
		if(classAssignableFrom.isPresent()) {
			compatibleValidathorParametrizedType = mapValidathorsParametrizedType.get(classAssignableFrom.get());
			cacheMapCompatibleValidathorsParametrizedType.put(info.getToValidateClass(), compatibleValidathorParametrizedType);
		}
		
		return compatibleValidathorParametrizedType;
	}
	
	public static List<Info> newToExplore(final Object outerObject, final List<Field> toValidateFields, List<String> fieldsNameToSkip) {
		return toValidateFields.stream()
							   .filter(field -> !fieldsNameToSkip.contains(field.getName()))
							   .map(field -> ValidathorUtils.buildInfo(outerObject, field))
							   .toList();
	}
	
	public static List<Info> newToExplore(final Object outerObject, String fieldName, final Collection<?> objectsToValidate) {
		return objectsToValidate.stream()
								.filter(Objects::nonNull)
								.map(element -> ValidathorUtils.buildInfo(outerObject, element, fieldName))
								.toList();
	}
	
}
