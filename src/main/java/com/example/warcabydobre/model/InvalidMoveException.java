package com.example.warcabydobre.model;

public class InvalidMoveException extends Exception {

	/**
	 * Exception thrown when the move was incorrect.
	 */
	private static final long serialVersionUID = 1L;

	public InvalidMoveException() {
		// TODO Auto-generated constructor stub
	}

	public InvalidMoveException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public InvalidMoveException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public InvalidMoveException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public InvalidMoveException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
