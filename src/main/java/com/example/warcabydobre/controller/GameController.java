package com.example.warcabydobre.controller;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.LinkedList;

import com.example.warcabydobre.model.BoardModel;
import com.example.warcabydobre.model.MovementTypes;
import com.example.warcabydobre.model.BoardModel.Listener;
import com.example.warcabydobre.model.BoardModel.PieceListener;
import com.example.warcabydobre.model.InvalidMoveException;
import com.example.warcabydobre.model.ModelMove;
import com.example.warcabydobre.model.PieceObject;
import com.example.warcabydobre.srv.Commands;
import com.example.warcabydobre.srvhandler.InvalidCommandException;
import com.example.warcabydobre.srvhandler.MoveConverter;
import com.example.warcabydobre.srvhandler.ServerHandler;
import com.example.warcabydobre.utils.AbstractPiece;
import com.example.warcabydobre.utils.Config;
import com.example.warcabydobre.view.BlackSquare;
import com.example.warcabydobre.view.GraphicalPiece;
import com.example.warcabydobre.view.GraphicalQueenPiece;
import com.example.warcabydobre.view.PieceColor;
import com.example.warcabydobre.view.Square;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

//Klasa kontroleraa
//wzorzec projektowy MVC

public class GameController {

	private PrintWriter out = null;
	private BufferedReader in = null;
	// private ClassicCheckersBoard board;
	private String message;
	private BoardModel boardModel;
	private Group piecesGroup;
	private GameRules rules;
	private GraphicalPiece[][] graphicalPiecesArray;
	private ServerHandler serverHandler;

	private Square[][] board;
	private ModelMove moveHelper = null;
	private GraphicalPiece pieceHelper = null;
	private Label turnLabel;
	
	
	private int actualPlayer = Config.PLAYER1;
	
	private int player;

	private int showing = Config.ACTIVE;

	public GameController(BoardModel boardModel, GraphicalPiece[][] graphicalPiecesArray, 
			Square[][] board, ServerHandler serverHandler, Label turnLabel, 
			Group piecesGroup) {
		this.boardModel = boardModel;
		this.graphicalPiecesArray = graphicalPiecesArray;
		this.board = board;
		this.piecesGroup = piecesGroup;
		this.rules = new ClassicCheckersRules(boardModel);// TODO Factory
		for (int j = 0; j < Config.CLASSICAL_CHECKERS_BOARD_WIDTH; j++) {
			for (int i = 0; i < Config.CLASSICAL_CHECKERS_BOARD_HEIGHT; i++) {
				GraphicalPiece graphicalPiece = graphicalPiecesArray[i][j];
				if (graphicalPiece != null) { // this.boardModel.
					Listener pieceListener = boardModel.new PieceListener(graphicalPiece,
							boardModel.getPiecesArray()[i][j], board, graphicalPiecesArray);
					boardModel.addListener(pieceListener);
				}
			}
		}
		this.serverHandler = serverHandler;
		this.turnLabel = turnLabel;

	}

	public static int toBoardCoordinates(double pixel) {
		return (int) (pixel + Config.SQUARE_CLASSIC_WIDTH / 2) / (int) (Config.SQUARE_CLASSIC_WIDTH);
	}

	
	
	public boolean isAppropriateColor(GraphicalPiece graphicalPiece) {
		if(player == 1 && graphicalPiece.getColor() == PieceColor.WHITE) {
			return true;
		}
		if(player == 2 && graphicalPiece.getColor() == PieceColor.BLACK) {
			return true;
		}
		return false;
	}
	
	
	
	public void onPieceMoved(GraphicalPiece graphicalPiece, MouseEvent event) throws InvalidMoveException {
		boolean appropriateColor = isAppropriateColor(graphicalPiece);
		if((player != actualPlayer) || (!appropriateColor)) {
			graphicalPiece.abortMove();
			return;
		}
		System.out.println("OK1");
		int oldX = toBoardCoordinates(graphicalPiece.getOldX());
		int oldY = toBoardCoordinates(graphicalPiece.getOldY());
		int newX = toBoardCoordinates(graphicalPiece.getLayoutX());
		int newY = toBoardCoordinates(graphicalPiece.getLayoutY());

		System.out.println("OK2");

		ModelMove modelResult;

		if (newX < 0 || newY < 0 || newX >= Config.CLASSICAL_CHECKERS_BOARD_WIDTH
				|| newY >= Config.CLASSICAL_CHECKERS_BOARD_HEIGHT) {
			modelResult = new ModelMove(MovementTypes.NONE);
			// graphicalResult = new Move(MovementTypes.NONE);
		} else {

			modelResult = rules.tryMove(oldX, oldY, newX, newY);
			// graphicalResult =
		}

		System.out.println("OK3");
		switch (modelResult.getMovementType()) {
		case NONE:
			graphicalPiece.abortMove();
			break;
		case FORWARD:
			try {
				boardModel.movePieceObject(oldX, oldY, newX, newY);
				// tworzenie damki
				if(graphicalPiece.getColor() == PieceColor.WHITE && newY == 0) {
					boardModel.transformToQueen(newX, newY);
					Platform.runLater(() -> initGraphicalQueen(newX, newY));
					
				}
				
				// tworzenie damki
				if(graphicalPiece.getColor() == PieceColor.BLACK && newY == 7) {
					boardModel.transformToQueen(newX, newY);
					Platform.runLater(() -> initGraphicalQueen(newX, newY));
				}
			} catch (InvalidMoveException ex) {
				System.out.println("Niaprawidlowy ruch");
			}

			
			
			System.out.println("OK4");
			message = MoveConverter.convertMoveToString(modelResult);
			serverHandler.sendMessage(message);
			Platform.runLater(() -> turnLabel.setText("OppositeTurn"));
			showing = Config.ACTIVE;
			actualPlayer = getOtherPlayer();
			
			break;
		case SINGLE_CAPTURE:
//                    graphicalPiece.move(newX, newY);
			try {
				boardModel.movePieceObject(oldX, oldY, newX, newY);
				// GraphicalPiece otherPiece = graphicalResult.getGraphicalPiece();
				int x1 = oldX + (newX - oldX) / 2;
				int y1 = oldY + (newY - oldY) / 2;
				boardModel.deletePieceObject(x1, y1);
				
				// tworzenie damki
				if(graphicalPiece.getColor() == PieceColor.WHITE && newY == 0) {
					boardModel.transformToQueen(newX, newY);
					Platform.runLater(() -> initGraphicalQueen(newX, newY));
					
					
				}
				
				// tworzenie damki
				if(graphicalPiece.getColor() == PieceColor.BLACK && newY == 7) {
					boardModel.transformToQueen(newX, newY);
					Platform.runLater(() -> initGraphicalQueen(newX, newY));
				}
				
				message = MoveConverter.convertMoveToString(modelResult);
				serverHandler.sendMessage(message);
				Platform.runLater(() -> turnLabel.setText("OppositeTurn"));
				showing = Config.ACTIVE;
				actualPlayer = getOtherPlayer();

			} catch (InvalidMoveException ex) {
				System.out.println("Niaprawidlowy ruch");
			}
			break;
		case QUEEN_DIAGONAL:
			try {
				boardModel.movePieceObject(oldX, oldY, newX, newY);
				System.out.println("OK4");
				message = MoveConverter.convertMoveToString(modelResult);
				serverHandler.sendMessage(message);
				Platform.runLater(() -> turnLabel.setText("OppositeTurn"));
				showing = Config.ACTIVE;
				actualPlayer = getOtherPlayer();
			} catch (InvalidMoveException ex) {
				System.out.println("Niaprawidlowy ruch");
			}
			//System.out.println("OK4");
			//message = MoveConverter.convertMoveToString(modelResult);
			//serverHandler.sendMessage(message);
			//Platform.runLater(() -> turnLabel.setText("OppositeTurn"));
			//showing = Config.ACTIVE;
			//actualPlayer = getOtherPlayer();
			break;
			case QUEEN_CAPTURE:
			try {
				boardModel.movePieceObject(oldX,oldY,newX,newY);
				int x1 = newX-1;
				int y1 = newY-1;
				boardModel.deletePieceObject(x1,y1);
				message = MoveConverter.convertMoveToString(modelResult);
				serverHandler.sendMessage(message);
				Platform.runLater(() -> turnLabel.setText("OppositeTurn"));
				showing = Config.ACTIVE;
				actualPlayer = getOtherPlayer();
			} catch(InvalidMoveException ex) {
				System.out.println("Nieprawidlowy ruch");
			}
			System.out.println("OK4");
			break;
		default:
			break;
		}
	}

	public boolean isCapturePossible(){

		return false;
	}
	
public GraphicalPiece makeGraphicalPiece(PieceColor color, int x, int y, boolean isQueen) {
		
		GraphicalPiece graphicalPiece;
		if(!isQueen) {
			graphicalPiece = new GraphicalPiece(color, x, y, piecesGroup);
		}
		else {
			graphicalPiece = new GraphicalQueenPiece(color, x, y, piecesGroup);
		}
		
		
        graphicalPiece.setOnMouseReleased(e -> {
        	try {
        		onPieceMoved(graphicalPiece,e);
        		System.out.println(boardModel);
        		
        		
			} catch (InvalidMoveException ex) {
				System.out.println(ex.getMessage());
			}
        	
		});

		return graphicalPiece;
}


public void initGraphicalQueen(int newX, int newY) {
	GraphicalPiece graphicalQueen = graphicalPiecesArray[newX][newY];
    graphicalQueen.setOnMouseReleased(e -> {
    	try {
    		onPieceMoved(graphicalQueen,e);
    		System.out.println(boardModel);
    		
    		
		} catch (InvalidMoveException ex) {
			System.out.println(ex.getMessage());
		}
    	
	});

}
	
	

	public BoardModel getBoardModel() {
		return boardModel;
	}

	/*
	 * public void setBoardModel(BoardModel boardModel) { this.boardModel =
	 * boardModel;
	 * 
	 * }
	 */

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * @return the actualPlayer
	 */
	public int getActualPlayer() {
		return actualPlayer;
	}

	/**
	 * @return the showing
	 */
	public int getShowing() {
		return showing;
	}

	/**
	 * @param actualPlayer the actualPlayer to set
	 */
	public void setActualPlayer(int actualPlayer) {
		this.actualPlayer = actualPlayer;
	}

	/**
	 * @param showing the showing to set
	 */
	public void setShowing(int showing) {
		this.showing = showing;
	}

	/**
	 * @return the player
	 */
	public int getPlayer() {
		return player;
	}

	/**
	 * @param player the player to set
	 */
	public void setPlayer(int player) {
		this.player = player;
	}
	
	private int getOtherPlayer() {
		if(player == Config.PLAYER1) {
			return Config.PLAYER2;
		}
		else {
			return Config.PLAYER1;
		}
	}
	
	
	public void abortMove() {
		pieceHelper.abortMove();
		pieceHelper = null;
	}
	
	
	public String receiveMessage() {
		return serverHandler.receiveMessage();
	}
	
	private ModelMove receiveMove() {
		String message = serverHandler.receiveMessage();
		ModelMove move;
		try {
			move = MoveConverter.convertToMove(message);
			return move;
		} catch (InvalidCommandException e) {
			move = new ModelMove(MovementTypes.NONE);
			return move;
		}
	}
	
	
	public void makeEnemyMove() {
		ModelMove move = this.receiveMove();
		System.out.println("OK1");
		int oldX = move.getOldX();
		int oldY = move.getOldY();
		int newX = move.getNewX();
		int newY = move.getNewY();
		GraphicalPiece graphicalPiece = graphicalPiecesArray[oldX][oldY];

		System.out.println("OK2");

		ModelMove modelResult;

		if (newX < 0 || newY < 0 || newX >= Config.CLASSICAL_CHECKERS_BOARD_WIDTH
				|| newY >= Config.CLASSICAL_CHECKERS_BOARD_HEIGHT) {
			modelResult = new ModelMove(MovementTypes.NONE);
		} else {

				try {
					modelResult = rules.tryMove(oldX, oldY, newX, newY);
				} catch (InvalidMoveException ex) {
					System.out.println(ex.getMessage());
					return;
				}
		}

		System.out.println("OK3");
		switch (modelResult.getMovementType()) {
		case NONE:
			break;
		case FORWARD:
			try {
				boardModel.movePieceObject(oldX, oldY, newX, newY);
				if(graphicalPiece.getColor() == PieceColor.WHITE && newY == 0) {
					boardModel.transformToQueen(newX, newY);
					
				}
				
				// tworzenie damki
				if(graphicalPiece.getColor() == PieceColor.BLACK && newY == 7) {
					boardModel.transformToQueen(newX, newY);
				}
			} catch (InvalidMoveException ex) {
				System.out.println(ex.getMessage());
			}

			
			System.out.println("OK4");
			Platform.runLater(() -> turnLabel.setText("MyTurn"));
			actualPlayer = player;
			break;
		case SINGLE_CAPTURE:
			try {
				boardModel.movePieceObject(oldX, oldY, newX, newY);
				int x1 = oldX + (newX - oldX) / 2;
				int y1 = oldY + (newY - oldY) / 2;
				boardModel.deletePieceObject(x1, y1);
				if(graphicalPiece.getColor() == PieceColor.WHITE && newY == 0) {
					boardModel.transformToQueen(newX, newY);
					
				}
				
				// tworzenie damki
				if(graphicalPiece.getColor() == PieceColor.BLACK && newY == 7) {
					boardModel.transformToQueen(newX, newY);
				}
				Platform.runLater(() -> turnLabel.setText("MyTurn"));
				actualPlayer = player;

			} catch (InvalidMoveException ex) {
				System.out.println(ex.getMessage());
			}
			break;
		case QUEEN_DIAGONAL:
			try {
				boardModel.movePieceObject(oldX, oldY, newX, newY);
			} catch (InvalidMoveException ex) {
				System.out.println("Niaprawidlowy ruch");
			}
			System.out.println("OK4");
			message = MoveConverter.convertMoveToString(modelResult);
			serverHandler.sendMessage(message);
			Platform.runLater(() -> turnLabel.setText("OppositeTurn"));
			showing = Config.ACTIVE;
			actualPlayer = getOtherPlayer();
			break;
			
		}
		}
	

	

}
