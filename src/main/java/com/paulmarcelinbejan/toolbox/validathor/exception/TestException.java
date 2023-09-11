package com.paulmarcelinbejan.toolbox.validathor.exception;

public class TestException extends Exception {

	private static final long serialVersionUID = -2805444174170684484L;

	public TestException(String message) {
		super(message);
	}

	public TestException(String message, Throwable cause) {
		super(message, cause);
	}

	public TestException(Throwable cause) {
		super(cause);
	}

}