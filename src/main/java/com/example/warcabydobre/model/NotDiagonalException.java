package com.example.warcabydobre.model;

/**
 * the exception thrown where the two field aren't on diagonal.
 * @author Jan Poreba
 *
 */

public class NotDiagonalException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotDiagonalException() {
		// TODO Auto-generated constructor stub
	}

	public NotDiagonalException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public NotDiagonalException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public NotDiagonalException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public NotDiagonalException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
