package com.example.warcabydobre.srvhandler;

import static org.junit.Assert.*;

import org.junit.Test;

import com.example.warcabydobre.model.InvalidMoveException;
import com.example.warcabydobre.model.ModelMove;
import com.example.warcabydobre.model.MovementTypes;
import com.example.warcabydobre.srv.Commands;

public class TestMoveToStringConversion {

	@Test
	public void testConvertMoveToStringNonException() throws InvalidMoveException {
		int oldX = 0;
		int oldY = 0;
		int newX = 1;
		int newY = 1;
		String moveStr = Commands.MOVE.getCommand();
		String expectedMessage = moveStr + " " + Integer.toString(oldX) + " " +
				Integer.toString(oldY) + " " + Integer.toString(newX) + " " + Integer.toString(newY);
		ModelMove move = new ModelMove(oldX, oldY, newX, newY);
		String resultMessage = MoveConverter.convertMoveToString(move);
		assertEquals(expectedMessage, resultMessage);
	}
	
	
	@Test(expected = InvalidMoveException.class)
	public void testConvertMoveToStringExpectedException() throws InvalidMoveException {
		ModelMove move = new ModelMove(MovementTypes.NONE);
		String resultMessage = MoveConverter.convertMoveToString(move);
	}

}
