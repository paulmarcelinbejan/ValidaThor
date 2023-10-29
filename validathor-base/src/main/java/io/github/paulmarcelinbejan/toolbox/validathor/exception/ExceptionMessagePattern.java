package io.github.paulmarcelinbejan.toolbox.validathor.exception;

/**
 * ExceptionMessagePattern
 */
public enum ExceptionMessagePattern {

	/**
	 * VALIDATION_FAILED message pattern
	 */
	VALIDATION_FAILED("\nVALIDATION FAILED: \n   Caused by: {0}, \n   FieldName: {1}, \n   OuterValue: {2}", 3);
	
	/**
	 * String value of the enum 
	 */
    public final String value;
    
    /**
     * how many parameters there are into the message
     */
    public final Integer parameters;

    private ExceptionMessagePattern(String value, Integer parameters) {
        this.value = value;
        this.parameters = parameters;
    }
	
}
