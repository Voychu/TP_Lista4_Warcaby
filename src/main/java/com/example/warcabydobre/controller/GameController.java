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
import com.example.warcabydobre.utils.BoardStructure;
import com.example.warcabydobre.utils.Config;
import com.example.warcabydobre.view.BlackSquare;
import com.example.warcabydobre.view.GraphicalPiece;
import com.example.warcabydobre.view.Move;
import com.example.warcabydobre.view.Square;

import javafx.scene.input.MouseEvent;

//Klasa kontroleraa
//wzorzec projektowy MVC

public class GameController {

	private PrintWriter out = null;
	private BufferedReader in = null;
	// private ClassicCheckersBoard board;
	private String message;
	private BoardModel boardModel;
	//private BoardStructure<T> boardStructure;
	// TODO GameRules
	private GameRules rules;
	private GraphicalPiece[][] graphicalPiecesArray;
	private ServerHandler serverHandler;

	private Square[][] board;
	private ModelMove moveHelper = null;
	private GraphicalPiece pieceHelper = null;
	
	
	private int actualPlayer = Config.PLAYER1;
	
	private int player;

	private int showing = Config.ACTIVE;

	public GameController(BoardModel boardModel, GraphicalPiece[][] graphicalPiecesArray, 
			Square[][] board, ServerHandler serverHandler) {
		this.boardModel = boardModel;
		this.graphicalPiecesArray = graphicalPiecesArray;
		this.board = board;
		this.rules = new ClassicCheckersRules(boardModel);// TODO Factory
		for (int j = 0; j < Config.CLASSICAL_CHECKERS_BOARD_WIDTH; j++) {
			for (int i = 0; i < Config.CLASSICAL_CHECKERS_BOARD_HEIGHT; i++) {
				GraphicalPiece graphicalPiece = graphicalPiecesArray[i][j];
				if (graphicalPiece != null) { // this.boardModel.
					Listener pieceListener = boardModel.new PieceListener(graphicalPiece,
							boardModel.getPiecesArray()[i][j], board);
					boardModel.addListener(pieceListener);
				}
			}
		}
		this.serverHandler = serverHandler;

	}

	public static int toBoardCoordinates(double pixel) {
		return (int) (pixel + Config.SQUARE_CLASSIC_WIDTH / 2) / (int) (Config.SQUARE_CLASSIC_WIDTH);
	}

	/*
	 * private Move executeMove(GraphicalPiece graphicalPiece, int newX, int newY) {
	 * try { int oldX = toBoardCoordinates(graphicalPiece.getOldX()); int oldY =
	 * toBoardCoordinates(graphicalPiece.getOldY()); ModelMove modelMove =
	 * rules.tryMove(oldX, oldY, newX, newY); MovementTypes moveType =
	 * modelMove.getMovementType(); return new Move(moveType);
	 * 
	 * } catch(InvalidMoveException ex) { return new Move(MovementTypes.NONE); } }
	 */

	/*public void onPieceMoved(GraphicalPiece graphicalPiece, MouseEvent event) throws InvalidMoveException {

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
		} else {

			modelResult = rules.tryMove(oldX, oldY, newX, newY);
			// graphicalResult =
		}

		System.out.println("OK3");
		MovementTypes type = modelResult.getMovementType();
		//TODO: Fabryka trybow
		String message;
		if(type != MovementTypes.NONE) {
			message = MoveConverter.convertMoveToString(modelResult);
			serverHandler.sendMessage(message);
		}
		else {
			graphicalPiece.abortMove();
		}
		moveHelper = modelResult;
		pieceHelper = graphicalPiece;
		
		
}*/
	
	
	
	public void onPieceMovedOld(GraphicalPiece graphicalPiece, MouseEvent event) throws InvalidMoveException {
		// TODO: Przekonwertowac piksele -> inty
		// TODO: wywolac metode tryMove z GameRules.
		// TODO: Zaktualizowac wspolrzedne pionka na podstawie zmiennej Move.
		// TODO: wywolac onPieceMoved z modelu.

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
			// graphicalPiece.move(newX, newY);
//                    board[oldX][oldY].setGraphicalPiece(null);
//                    board[newX][newY].setGraphicalPiece(graphicalPiece);
			// TODO: try-catch:
			try {
				boardModel.movePieceObject(oldX, oldY, newX, newY);
			} catch (InvalidMoveException ex) {
				System.out.println("Niaprawidlowy ruch");
			}

			// if(graphicalPiece.getColor() == PieceColor.WHITE && newY == 8)
			// tworzenie damki
			// if(graphicalPiece.getColor() == PieceColor.BLACK && newY == 0)
			// tworzenie damki
			System.out.println("OK4");
			break;
		case CAPTURE_FORWARD:
//                    graphicalPiece.move(newX, newY);
			try {
				boardModel.movePieceObject(oldX, oldY, newX, newY);
				// GraphicalPiece otherPiece = graphicalResult.getGraphicalPiece();
				int x1 = oldX + (newX - oldX) / 2;
				int y1 = oldY + (newY - oldY) / 2;
				boardModel.deletePieceObject(x1, y1);

			} catch (InvalidMoveException ex) {
				System.out.println("Niaprawidlowy ruch");
			}

//                    board[oldX][oldY].setGraphicalPiece(null);
//                    board[newX][newY].setGraphicalPiece(graphicalPiece);
			// GraphicalPiece otherPiece = graphicalResult.getGraphicalPiece();

			// if(graphicalPiece.getColor() == PieceColor.WHITE && newY == 8)
			// tworzenie damki
			// if(graphicalPiece.getColor() == PieceColor.BLACK && newY == 0)
			// tworzenie damki
			/*
			 * board[toBoardCoordinates(otherPiece.getOldX())][toBoardCoordinates(otherPiece
			 * .getOldY())].setGraphicalPiece(null); //otherPiece.delete();;//W modelu
			 * listenery na usuwanie int x_cord = toBoardCoordinates(otherPiece.getOldX());
			 * int y_cord = toBoardCoordinates(otherPiece.getOldY()); try {
			 * boardModel.deletePieceObject(x_cord, y_cord); System.out.println(x_cord +
			 * ", " + y_cord); } catch (InvalidMoveException ex) {
			 * graphicalPiece.abortMove(); }
			 */
			break;
		}
	}
	
	
//	public void movePieceAfterServerAnswer() {
//		MovementTypes type = moveHelper.getMovementType();
//		int oldX = moveHelper.getOldX();
//		int oldY = moveHelper.getOldY();
//		int newX = moveHelper.getNewX();
//		int newY = moveHelper.getNewY();
//		switch (type) {
//		case NONE:
//			break;
//		case FORWARD:
//
//			try {
//				//TODO:Dopiero pospr. ruchu przez serwer
//				boardModel.movePieceObject(oldX, oldY, newX, newY);
//			} catch (InvalidMoveException ex) {
//				System.out.println("Niaprawidlowy ruch");
//			}
//
//			// if(graphicalPiece.getColor() == PieceColor.WHITE && newY == 8)
//			// tworzenie damki
//			// if(graphicalPiece.getColor() == PieceColor.BLACK && newY == 0)
//			// tworzenie damki
//			System.out.println("OK4");
//			break;
//		case CAPTURE_FORWARD:
////                    graphicalPiece.move(newX, newY);
//			try {
//				//TODO:Dopiero pospr. ruchu przez serwer
//				boardModel.movePieceObject(oldX, oldY, newX, newY);
//				// GraphicalPiece otherPiece = graphicalResult.getGraphicalPiece();
//				int x1 = oldX + (newX - oldX) / 2;
//				int y1 = oldY + (newY - oldY) / 2;
//				boardModel.deletePieceObject(x1, y1);
//
//			} catch (InvalidMoveException ex) {
//				System.out.println("Niaprawidlowy ruch");
//			}
//
////                    board[oldX][oldY].setGraphicalPiece(null);
////                    board[newX][newY].setGraphicalPiece(graphicalPiece);
//			// GraphicalPiece otherPiece = graphicalResult.getGraphicalPiece();
//
//			// if(graphicalPiece.getColor() == PieceColor.WHITE && newY == 8)
//			// tworzenie damki
//			// if(graphicalPiece.getColor() == PieceColor.BLACK && newY == 0)
//			// tworzenie damki
//			/*
//			 * board[toBoardCoordinates(otherPiece.getOldX())][toBoardCoordinates(otherPiece
//			 * .getOldY())].setGraphicalPiece(null); //otherPiece.delete();;//W modelu
//			 * listenery na usuwanie int x_cord = toBoardCoordinates(otherPiece.getOldX());
//			 * int y_cord = toBoardCoordinates(otherPiece.getOldY()); try {
//			 * boardModel.deletePieceObject(x_cord, y_cord); System.out.println(x_cord +
//			 * ", " + y_cord); } catch (InvalidMoveException ex) {
//			 * graphicalPiece.abortMove(); }
//			 */
//			break;
//		}
//	}
//	
	
//	public boolean waitForServerApproval() {
//		String message = serverHandler.waitForServerApproval();
//		
//		//TODO: Czekac az przyjdzie odpowiednia odpowiedz.
//		//1. utworz petle nieskonczona i czekaj tak dlugo, az przyjdzie odpowiedz dotyczaca
//		//	ruchu, ktory zosta wykonany.
//		//2. w petli skonwertuj odpowiedz z serwra na obiekt typu ModelMove i porownaj
//		//	z obiektem moveHelper pod warunkiem, ze byla odpowiez OKMV.
//		
//		while(true) {
//			ModelMove move;
//			try {
//				move = MoveConverter.convertToMove(message);
//			} catch (InvalidCommandException ex) {
//				System.out.println(ex.getMessage());
//				continue;
//			};
//			Commands command = MoveConverter.getCommandType(message);
//			if(command == Commands.OKMV && move.equals(moveHelper)){ 
//					return true;
//			}
//			else if(command == Commands.ERMV && move.equals(moveHelper)){
//				return false;
//			}
//			
//		}
//	}
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

	public void makeEnemyMove() {
		
		
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
	
	
	public void abortMove() {
		pieceHelper.abortMove();
		pieceHelper = null;
	}
	
	

	/*
	 * public ClassicCheckersBoard getBoard() { return board; }
	 */

	/*
	 * public void setBoard(ClassicCheckersBoard board) { this.board = board; }
	 */

	/*
	 * public void receiveMessage() { board.setLabelText(message); }
	 * 
	 * public void sendMessage() { board.getSendingText(); }
	 */

}
