package com.kamaz.exceptions;

public class ServicesException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ServicesException(String message) {
		super(message);
	}

	public ServicesException(String message, Throwable cause) {
		super(message, cause);
	}
}