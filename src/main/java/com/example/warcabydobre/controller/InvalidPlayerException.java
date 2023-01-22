package com.example.warcabydobre.controller;

public class InvalidPlayerException extends Exception {

	/**
	 * Exception thrown when player number is incorrect
	 */
	private static final long serialVersionUID = 1L;

	public InvalidPlayerException() {
		// TODO Auto-generated constructor stub
	}

	public InvalidPlayerException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public InvalidPlayerException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public InvalidPlayerException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public InvalidPlayerException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
