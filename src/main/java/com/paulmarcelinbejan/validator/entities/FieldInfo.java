package com.paulmarcelinbejan.validator.entities;

import static com.paulmarcelinbejan.validator.enums.InfoType.FIELD;

import java.lang.reflect.Field;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class FieldInfo extends Info {
	
	public FieldInfo(Object outerObject, String outerObjectToString, String toValidateName, Class<?> toValidateClass, Field toValidateField) {
		super(FIELD, outerObject, outerObjectToString, toValidateName, toValidateClass);
		this.toValidateField = toValidateField;
	}
	
	private Field toValidateField;
	
}