package com.example.warcabydobre.srvhandler;

public class InvalidCommandException extends Exception {

	/**
	 * Exception thrown when opposite player
	 * send invalid command.
	 */
	private static final long serialVersionUID = 1L;

	public InvalidCommandException() {
		// TODO Auto-generated constructor stub
	}

	public InvalidCommandException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public InvalidCommandException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public InvalidCommandException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public InvalidCommandException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
