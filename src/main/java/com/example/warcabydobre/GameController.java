package com.example.warcabydobre;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.LinkedList;

//Klasa kontrolera
//wzorzec projektowy MVC


public class GameController {
	
	private PrintWriter out = null;
    private BufferedReader in = null;
	//private ClassicCheckersBoard board;
	private String message;
	private BoardModel boardModel;
	//TODO GameRules
	private LinkedList<PieceObject> whitePiecesList;
	private LinkedList<PieceObject> blackPiecesList;

	
	public GameController(BoardModel boardModel, LinkedList<PieceObject> whitePiecesList, LinkedList<PieceObject> blackPiecesList ) {
		this.boardModel = boardModel;
		this.whitePiecesList = whitePiecesList;
		this.blackPiecesList = blackPiecesList;
		
	}
	
	

	public BoardModel getBoardModel() {
		return boardModel;
	}



	public void setBoardModel(BoardModel boardModel) {
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
