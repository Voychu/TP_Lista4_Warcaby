package com.example.warcabydobre.srv;


public enum Commands {
	
	
	MOVE("move"),
	OKMV("okmv"),
	ERMV("ermv");
	
	
	private String command;
	
	
	Commands(String command){
		this.command = command;
	}
	
	public String getCommand() {
		return command;
	}
	
	
	
	

}
