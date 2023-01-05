package com.example.warcabydobre.controller;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.LinkedList;

import com.example.warcabydobre.Config;
import com.example.warcabydobre.model.BoardModel;
import com.example.warcabydobre.model.MovementTypes;
import com.example.warcabydobre.model.BoardModel.Listener;
import com.example.warcabydobre.model.BoardModel.PieceListener;
import com.example.warcabydobre.model.PieceObject;
import com.example.warcabydobre.view.GraphicalPiece;
import com.example.warcabydobre.view.Move;

import javafx.scene.input.MouseEvent;

//Klasa kontroleraa
//wzorzec projektowy MVC


public class GameController {
	
	private PrintWriter out = null;
    private BufferedReader in = null;
	//private ClassicCheckersBoard board;
	private String message;
	private BoardModel boardModel;
	//TODO GameRules
	private GameRules rules;
	private GraphicalPiece[][] graphicalPiecesArray; 

	
	public GameController(BoardModel boardModel, GraphicalPiece[][] graphicalPiecesArray) {
		this.boardModel = boardModel;
		this.graphicalPiecesArray = graphicalPiecesArray;
		this.rules = new ClassicCheckersRules();//TODO Factory
		for(int i = 0 ; i<Config.CLASSICAL_CHECKERS_BOARD_WIDTH;i++) {
			for(int j = 0 ; j<Config.CLASSICAL_CHECKERS_BOARD_HEIGHT;j++) {
				GraphicalPiece graphicalPiece = graphicalPiecesArray[i][j];
				if(graphicalPiece != null) { //this.boardModel.
					Listener pieceListener= this.boardModel.new PieceListener(graphicalPiece);
					boardModel.addListener(pieceListener);
				}
			}
		}
		
		
		
	}
	
	
	 private int toBoardCoordinates(double pixel)
     {
         return (int)(pixel + Config.SQUARE_CLASSIC_WIDTH / 2) / (int)(Config.SQUARE_CLASSIC_WIDTH);
     }
	
	 //TODO: implementacja metody
	public void onPieceMoved(MouseEvent event) {
		//TODO: Przekonwertowac piksele -> inty
		//TODO: wywolac metode tryMove z GameRules.
		//TODO: Zaktualizowac wspolrzedne pionka na podstawie zmiennej Move.
		//TODO: wywolac onPieceMoved z modelu.
		/*
		 if(board[newX][newY].isOccupied() || (newX + newY) % 2 == 0)
         {
         return new Move(MovementTypes.NONE);
         }
     int xp = toBoardCoordinates(graphicalPiece.getOldX());
     int yp = toBoardCoordinates(graphicalPiece.getOldY());
     System.out.println(xp + ", " + yp);

     if(Math.abs(newX - xp) == 1 && newY - yp == graphicalPiece.getColor().movementDirection)
         return new Move(MovementTypes.FORWARD);
     else if (Math.abs(newX - xp) == 2 && newY - yp == graphicalPiece.getColor().movementDirection* 2)
         {
             int x1 = xp + (newX - xp)/2;
             int y1 = yp + (newY - yp)/2;

             if (board[x1][y1].isOccupied() && board[x1][y1].getGraphicalPiece().getColor() != graphicalPiece.getColor())
                 {
                 return new Move(MovementTypes.CAPTURE_FORWARD, board[x1][y1].getGraphicalPiece());
                 }
         }
     return new Move(MovementTypes.NONE);
     
     
     
     //TODO: Na koncu metody onPieceMoved dac: movePieceObject
		*/
	}
	

	public BoardModel getBoardModel() {
		return boardModel;
	}



	/*public void setBoardModel(BoardModel boardModel) {
		this.boardModel = boardModel;
		
	}*/



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
