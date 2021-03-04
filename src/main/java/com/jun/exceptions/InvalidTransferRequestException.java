package com.jun.exceptions;

public class InvalidTransferRequestException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4980991853787823465L;

	public InvalidTransferRequestException() {
	}

	public InvalidTransferRequestException(String message) {
		super(message);
	}

	public InvalidTransferRequestException(Throwable cause) {
		super(cause);
	}

	public InvalidTransferRequestException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidTransferRequestException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
