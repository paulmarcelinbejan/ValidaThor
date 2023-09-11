package com.paulmarcelinbejan.toolbox.validathor.exception;

public enum ExceptionMessagePattern {

	VALIDATION_FAILED("\nVALIDATION FAILED: \n   Caused by: {0}, \n   FieldName: {1}, \n   OuterValue: {2}", 3);
	
    public final String value;
    
    public final Integer parameters;

    private ExceptionMessagePattern(String value, Integer parameters) {
        this.value = value;
        this.parameters = parameters;
    }
	
}
