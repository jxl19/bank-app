package com.jun.exceptions;

public class InvalidApplicationException extends Exception {

	public InvalidApplicationException() {
	}

	public InvalidApplicationException(String message) {
		super(message);
	}

	public InvalidApplicationException(Throwable cause) {
		super(cause);
	}

	public InvalidApplicationException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidApplicationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
