package com.paulmarcelinbejan.validator.entities;

import com.paulmarcelinbejan.validator.enums.InfoType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class Info {

	private InfoType type;
	
	private Object outerObject;
	private String outerObjectToString;
	
	private String toValidateName;
	private Class<?> toValidateClass;
	
}
