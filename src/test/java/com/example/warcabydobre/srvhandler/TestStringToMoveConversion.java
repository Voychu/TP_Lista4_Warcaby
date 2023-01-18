package com.example.warcabydobre.srvhandler;

import static org.junit.Assert.*;

import org.junit.Test;

import com.example.warcabydobre.model.ModelMove;
import com.example.warcabydobre.srv.Commands;

public class TestStringToMoveConversion {

	@Test
	public void testConvertToMoveNonException() throws InvalidCommandException {
		int oldX = 0;
		int oldY = 0;;
		int newX = 1;
		int newY = 1;
		String moveStr = Commands.MOVE.getCommand();
		String message = moveStr + " " + Integer.toString(oldX) + " " +
				Integer.toString(oldY) + " " + Integer.toString(newX) + " " + Integer.toString(newY);
		ModelMove resultMove = MoveConverter.convertToMove(message);
		ModelMove expectedMove = new ModelMove(oldX, oldY, newX, newY);
		assertEquals(expectedMove, resultMove);
		
	}
	
	
	@Test(expected = InvalidCommandException.class)
	public void testIncorrectMessageExpectedException() throws InvalidCommandException {
		String message = "nowy ruch";
		ModelMove resultMove = MoveConverter.convertToMove(message);
		
	}
	
	
	@Test(expected = InvalidCommandException.class)
	public void testTooManyArgumentsExpectedException() throws InvalidCommandException {
		int oldX = 0;
		int oldY = 0;;
		int newX = 1;
		int newY = 1;
		int nextX = 2;
		String moveStr = Commands.MOVE.getCommand();
		String message = moveStr + " " + Integer.toString(oldX) + " " +
				Integer.toString(oldY) + " " + Integer.toString(newX) + " " + 
				Integer.toString(newY) + " " + Integer.toString(nextX);
		ModelMove resultMove = MoveConverter.convertToMove(message);
		
	}

}
