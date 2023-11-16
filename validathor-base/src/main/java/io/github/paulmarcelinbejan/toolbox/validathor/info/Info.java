package io.github.paulmarcelinbejan.toolbox.validathor.info;

import io.github.paulmarcelinbejan.toolbox.validathor.utils.ValidathorUtils;
import lombok.Getter;
import lombok.ToString;

/**
 * Info holder
 */
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
		
		is_ToValidateClass_InstanceOf_ParameterizedType = ValidathorUtils.isParameterizedType(toValidateClass);
	}
	
	/**
	 * the outer object of the toValidateValue
	 */
	private Object outerObject;
	
	/**
	 * outerObject.toString()
	 */
	private String outerObjectToString;
	
	/**
	 * the class to be validated
	 */
	private Class<?> toValidateClass;
	
	/**
	 * true if toValidateClass is an instanceOf ParameterizedType
	 */
	private boolean is_ToValidateClass_InstanceOf_ParameterizedType;
	
	/**
	 * the name of the field to be validated
	 */
	private String toValidateName;
	
	/**
	 * the value of the field to be validated
	 */
	private Object toValidateValue;
	
}
