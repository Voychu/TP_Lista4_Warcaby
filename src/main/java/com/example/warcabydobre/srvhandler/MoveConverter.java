package com.example.warcabydobre.srvhandler;

import com.example.warcabydobre.model.ModelMove;
import com.example.warcabydobre.model.MovementTypes;
import com.example.warcabydobre.srv.Commands;

public class MoveConverter {
	
	private static final String separator =" ";

	
	//TODO: move xp yp xk yk split po spacji
	public static ModelMove convertToMove(String message) throws InvalidCommandException{
		String[] parameters = message.split(separator);
		if(!parameters[0].equals(Commands.MOVE.getCommand())) {
			return null;
		}
		if(parameters.length != 5) {
			return null;
		}
		
		try {
			int oldX = Integer.parseInt(parameters[1]);
			int oldY = Integer.parseInt(parameters[1]);
			int newX = Integer.parseInt(parameters[1]);
			int newY = Integer.parseInt(parameters[1]);
			ModelMove move = new ModelMove(oldX, oldY, newX, newY);
			return move;
		}
		catch(NumberFormatException ex) {
			throw new InvalidCommandException(ex.getMessage());
		}
		
		
	}
	
	public static String convertMoveToString(ModelMove move) {
		MovementTypes type = move.getMovementType();
		if(type != MovementTypes.NONE) {
			return Commands.MOVE.getCommand() + move.getOldX() + " " + move.getOldY() +" "+
					move.getNewX() + " " + move.getNewY();
		}
		else return null;
		
	}

}
