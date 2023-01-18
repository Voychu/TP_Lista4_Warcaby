package com.example.warcabydobre.srvhandler;

import static org.junit.Assert.*;

import org.junit.Test;

import com.example.warcabydobre.srv.Commands;

public class TestGetCommandType {

	@Test
	public void testGetCommandTypeOnMove() {
		String moveStr = "move 0 0 1 1";
		Commands resultType = MoveConverter.getCommandType(moveStr);
		Commands expectedType = Commands.MOVE;
		assertEquals(expectedType, resultType);
	}
	
	
	@Test
	public void testGetCommandTypeOnError() {
		String moveStr = "abc 1 2 3 4";
		Commands resultType = MoveConverter.getCommandType(moveStr);
		Commands expectedType = Commands.ERMV;
		assertEquals(expectedType, resultType);
	}

}
