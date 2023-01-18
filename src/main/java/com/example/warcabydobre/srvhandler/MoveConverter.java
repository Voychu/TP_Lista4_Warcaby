package com.example.warcabydobre.srvhandler;

import com.example.warcabydobre.model.InvalidMoveException;
import com.example.warcabydobre.model.ModelMove;
import com.example.warcabydobre.model.MovementTypes;
import com.example.warcabydobre.srv.Commands;

public class MoveConverter {
	
	private static final String separator =" ";

	
	public static ModelMove convertToMove(String message) 
			throws InvalidCommandException {
		String[] parameters = message.split(separator);
		String moveStr = Commands.MOVE.getCommand();
		if( !(parameters[0].equalsIgnoreCase(moveStr)) ) {
			throw new InvalidCommandException("Nieprawidlowy komunikat");
		}
		if(parameters.length != 5) {
			throw new InvalidCommandException("Nieprawidlowy komunikat");
		}
		
		try {
			int oldX = Integer.parseInt(parameters[1]);
			int oldY = Integer.parseInt(parameters[2]);
			int newX = Integer.parseInt(parameters[3]);
			int newY = Integer.parseInt(parameters[4]);
			ModelMove move = new ModelMove(oldX, oldY, newX, newY);
			return move;
		}
		catch(NumberFormatException ex) {
			throw new InvalidCommandException(ex.getMessage());
		}
		
		
	}
	
	public static Commands getCommandType(String message) {
		String[] parameters = message.split(separator);
		if(parameters[0].equals("move")) {
			return Commands.MOVE;
		}
		else {
			return Commands.ERMV;
		}
	}
	
	public static String convertMoveToString(ModelMove move) {
		MovementTypes type = move.getMovementType();
		if(type != MovementTypes.NONE) {
			return Commands.MOVE.getCommand() + " " + move.getOldX() + " " + move.getOldY() +" "+
					move.getNewX() + " " + move.getNewY();
		}
		else return null;
		
	}

}
