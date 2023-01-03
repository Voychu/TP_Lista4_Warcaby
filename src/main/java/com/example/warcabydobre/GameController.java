package com.example.warcabydobre;

import java.io.BufferedReader;
import java.io.PrintWriter;

//Klasa kontrolera
//wzorzec projektowy MVC


public class GameController {
	
	private PrintWriter out = null;
    private BufferedReader in = null;
	//private ClassicCheckersBoard board;
	private String message;
	private BoardModel boardModel;

	
	public GameController(BoardModel boardModel) {
		this.boardModel = boardModel;
	}
	
	

	public String getMessage() {
		return message;
	}



	public void setMessage(String message) {
		this.message = message;
	}



	/*public ClassicCheckersBoard getBoard() {
		return board;
	}*/





	/*public void setBoard(ClassicCheckersBoard board) {
		this.board = board;
	}*/



	
	/*public void receiveMessage() {
		board.setLabelText(message);
	}
	
	public void sendMessage() {
		board.getSendingText();
	}*/
	
	
	

}
