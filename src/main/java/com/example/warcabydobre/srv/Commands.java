package com.example.warcabydobre.srv;



/**
 * The Enum of commands used during
 * communication between players via server.
 */
public enum Commands {
	
	
	/** Commend sending after doing move. */
	MOVE("move"),
	
	/** Command using after done incorrect move. */
	ERMV("ermv"),
	
	/** Command using on exit from application. */
	EXIT("bye");
	
	
	/** The string representing command. */
	private String command;
	
	
	/**
	 * Constructor of Command Enum
	 *
	 * @param command the command used in communication
	 */
	private Commands(String command){
		this.command = command;
	}
	
	/**
	 * Gets the command.
	 *
	 * @return the command
	 */
	public String getCommand() {
		return command;
	}
	
	
	
	

}
