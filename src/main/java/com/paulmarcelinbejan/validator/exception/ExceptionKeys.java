package com.paulmarcelinbejan.validator.exception;

public enum ExceptionKeys {

	IS_NULL("VALIDATION FAILED: \n   Cause: IS NULL, \n   FieldName: _fn_, \n   OuterValue: _ov_"),
	IS_EMPTY("VALIDATION FAILED: \n   Cause: IS EMPTY, \n   FieldName: _fn_, \n   OuterValue: _ov_");
	
    public final String value;

    private ExceptionKeys(String value) {
        this.value = value;
    }
    
    public enum Placeholder {

    	FIELD_NAME("_fn_"),
    	OUTER_VALUE("_ov_");
    	
        public final String value;

        private Placeholder(String value) {
            this.value = value;
        }
    	
    }
	
}
