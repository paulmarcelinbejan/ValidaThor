package com.github.paulmarcelinbejan.toolbox.validathor.info;

import com.github.paulmarcelinbejan.toolbox.validathor.utils.ValidathorUtils;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Info {
	
	public Info(Object outerObject, String outerObjectToString, 
			Class<?> toValidateClass, String toValidateName, Object toValidateValue) {

		this.outerObject = outerObject;
		this.outerObjectToString = outerObjectToString;
		this.toValidateClass = toValidateClass;
		this.toValidateName = toValidateName;
		this.toValidateValue = toValidateValue;
		
		is_ToValidateClass_InstanceOf_ParametrizedType = ValidathorUtils.isParameterizedType(toValidateClass);
	}
	
	private Object outerObject;
	private String outerObjectToString;
	
	private Class<?> toValidateClass;
	private boolean is_ToValidateClass_InstanceOf_ParametrizedType;
	private String toValidateName;
	private Object toValidateValue;
	
}
