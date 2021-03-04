package com.jun.exceptions;

public class ApplicationNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1288702728179153325L;

	public ApplicationNotFoundException() {
	}

	public ApplicationNotFoundException(String message) {
		super(message);
	}

	public ApplicationNotFoundException(Throwable cause) {
		super(cause);
	}

	public ApplicationNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ApplicationNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
