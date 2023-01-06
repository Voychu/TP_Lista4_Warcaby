package com.example.warcabydobre.controller;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.LinkedList;

import com.example.warcabydobre.Config;
import com.example.warcabydobre.model.BoardModel;
import com.example.warcabydobre.model.MovementTypes;
import com.example.warcabydobre.model.BoardModel.Listener;
import com.example.warcabydobre.model.BoardModel.PieceListener;
import com.example.warcabydobre.model.ModelMove;
import com.example.warcabydobre.model.PieceObject;
import com.example.warcabydobre.view.BlackSquare;
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
	
	private BlackSquare[][] board;//TODO: dziurawa tablica

	
	public GameController(BoardModel boardModel, GraphicalPiece[][] graphicalPiecesArray, BlackSquare[][] board) {
		this.boardModel = boardModel;
		this.graphicalPiecesArray = graphicalPiecesArray;
		this.board = board;
		this.rules = new ClassicCheckersRules(boardModel.getPiecesArray());//TODO Factory
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
	 
	/* 
	
	 //TODO: implementacja metody
	public void onPieceMoved(GraphicalPiece graphicalPiece, MouseEvent event) {
		//TODO: Przekonwertowac piksele -> inty
		//TODO: wywolac metode tryMove z GameRules.
		//TODO: Zaktualizowac wspolrzedne pionka na podstawie zmiennej Move.
		//TODO: wywolac onPieceMoved z modelu.
		
		int oldX = toBoardCoordinates(graphicalPiece.getOldX());
	    int oldY = toBoardCoordinates(graphicalPiece.getOldY());
		int newX = toBoardCoordinates(graphicalPiece.getLayoutX());
          int newY = toBoardCoordinates(graphicalPiece.getLayoutY());

            ModelMove result;

            if (newX < 0 || newY < 0 || newX >= Config.CLASSICAL_CHECKERS_BOARD_WIDTH || newY >= Config.CLASSICAL_CHECKERS_BOARD_HEIGHT) {
                result = new ModelMove(MovementTypes.NONE);
            } else {
            
                result = rules.tryMove(oldX, oldY, newX, newY);
            }

            //int xp = toBoardCoordinates(graphicalPiece.getOldX());
            //int yp = toBoardCoordinates(graphicalPiece.getOldY());

            switch (result.getMovementType()) {
                case NONE:
                    graphicalPiece.abortMove();
                    break;
                case FORWARD:
                    graphicalPiece.move(newX, newY);
                    board[oldX][oldY].setGraphicalPiece(null);
                    board[newX][newY].setGraphicalPiece(graphicalPiece);
                    //TODO: try-catch:
                    this.boardModel.movePieceObject(oldX, oldY, newX, newY);
                    //if(graphicalPiece.getColor() == PieceColor.WHITE && newY == 8)
                        //tworzenie damki
                    //if(graphicalPiece.getColor() == PieceColor.BLACK && newY == 0)
                        //tworzenie damki
                    break;
                case CAPTURE_FORWARD:
                    graphicalPiece.move(newX, newY);
                    board[oldX][oldY].setGraphicalPiece(null);
                    board[newX][newY].setGraphicalPiece(graphicalPiece);
                    ///GraphicalPiece otherPiece = result.getGraphicalPiece();
                    
                    //TODO: Wywolanie boardModel.deletePieceObject()
                    //if(graphicalPiece.getColor() == PieceColor.WHITE && newY == 8)
                        //tworzenie damki
                    //if(graphicalPiece.getColor() == PieceColor.BLACK && newY == 0)
                        //tworzenie damki
                    ///board[toBoardCoordinates(otherPiece.getOldX())][toBoardCoordinates(otherPiece.getOldY())].setGraphicalPiece(null);
                    ///piecesGroup.getChildren().remove(otherPiece);//W modelu listenery na usuwanie
                    //graphicalPiecesList.remove(otherPiece);
                    ///int x_cord = toBoardCoordinates(otherPiece.getOldX());
                    ///int y_cord = toBoardCoordinates(otherPiece.getOldY());
                    ///piecesArray[x_cord][y_cord] 
                    		= null;
                    System.out.println(x_cord + ", " +  y_cord);
                    break;
            }
        });

	}*/
	

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
