package com.example.warcabydobre.srv;


public enum Commands {
	
	
	MOVE("move"),
	OKMV("okmv"),
	ERMV("ermv"),
	EXIT("bye");
	
	
	private String command;
	
	
	private Commands(String command){
		this.command = command;
	}
	
	public String getCommand() {
		return command;
	}
	
	
	
	

}
