package com.paulmarcelinbejan.validator.entities;

import static com.paulmarcelinbejan.validator.enums.InfoType.PARENT;

import java.lang.reflect.Field;
import java.util.List;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class ParentInfo extends Info {
	
	public ParentInfo(Object outerObject, String outerObjectToString, String toValidateName, Class<?> toValidateClass, Object toValidate, List<Field> toValidateFields) {
		super(PARENT, outerObject, outerObjectToString, toValidateName, toValidateClass);
		this.toValidate = toValidate;
		this.toValidateFields = toValidateFields;
	}
	
	private Object toValidate;
	
	private List<Field> toValidateFields;
	
}