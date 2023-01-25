package com.example.warcabydobre.srvhandler;

import com.example.warcabydobre.model.InvalidMoveException;
import com.example.warcabydobre.model.ModelMove;
import com.example.warcabydobre.model.MovementTypes;
import com.example.warcabydobre.srv.Commands;

/**
 * Static class responsible for 
 * conversion from move to string 
 * and in reverse
 * @author sosog
 *
 */
public class MoveConverter {
	
	/**
	 * Separator in messages
	 */
	private static final String separator =" ";

	/**
	 * Method responsible for converting message to move
	 * @param message passed message
	 * @return move described by the message
	 * @throws InvalidCommandException exception thrown
	 * when message was incorrect
	 */
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
	
	/**
	 * Methods returning type of the message
	 * @param message passed message
	 * @return type of the message
	 */
	public static Commands getCommandType(String message) {
		String[] parameters = message.split(separator);
		if(parameters[0].equals("move")) {
			return Commands.MOVE;
		}
		else {
			return Commands.ERMV;
		}
	}
	
	/**
	 * Method responsible for converting move to
	 * String
	 * @param move passed move
	 * @return string describing the move
	 * @throws InvalidMoveException exception thrown 
	 * when the move was incorrect
	 */
	public static String convertMoveToString(ModelMove move) throws InvalidMoveException{
		MovementTypes type = move.getMovementType();
		if(type != MovementTypes.NONE) {
			return Commands.MOVE.getCommand() + " " + move.getOldX() + " " + move.getOldY() +" "+
					move.getNewX() + " " + move.getNewY();
		}
		else throw new InvalidMoveException("Nieprawidlowy ruch");
		
	}

}
