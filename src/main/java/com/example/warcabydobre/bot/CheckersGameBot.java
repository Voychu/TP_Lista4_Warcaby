package com.example.warcabydobre.bot;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Optional;

import com.example.warcabydobre.controller.GameController;
import com.example.warcabydobre.controller.GameState;
import com.example.warcabydobre.model.BoardModel;
import com.example.warcabydobre.model.InvalidMoveException;
import com.example.warcabydobre.srvhandler.ServerHandler;
import com.example.warcabydobre.utils.Config;
import com.example.warcabydobre.view.BlackSquare;
import com.example.warcabydobre.view.CheckersGame;
import com.example.warcabydobre.view.GraphicalPiece;
import com.example.warcabydobre.view.GraphicalQueenPiece;
import com.example.warcabydobre.view.PieceColor;
import com.example.warcabydobre.view.Square;
import com.example.warcabydobre.view.WhiteSquare;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.application.Application.Parameters;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CheckersGameBot implements Runnable{

	/** Flag representing gameType. */
	private int gameId;
	
	
	/** The stage with the game's board. */
	private Stage boardStage;
	
	//TODO: Extending project to other game types.
	// private Stage choosingGameStage;
	
	/** The Constant storing 
	 * the number of columns. */
	private static final int numCols 
		= Config.CLASSICAL_CHECKERS_BOARD_WIDTH;
	
	/** The Constant storing
	 * the number of rows. */
	private static final int numRows 
		= Config.CLASSICAL_CHECKERS_BOARD_HEIGHT;
	
	/** The Constant storing 
	 * the number of rows with pieces. */
	private static final int numRowsWithPieces 
		= Config.CLASSICAL_CHECKERS_ROWS_WITH_PIECES;
	
	
	
	/** The label displaying whose is turn. */
	private Label turnLabel;
	
	
	private Alert endingGameAlert;
	


	/** The socket of player playing
	 * from this windows. */
	private Socket socket = null;
	
	/** The controller of player
	 * playing from this window. */
	private GameController controller;
	
	/** The model of the game of player
	 * playing from this windows. */
	private BoardModel boardModel;
	
	/** The array of graphical pieces
	 * moving on the board. */
	private GraphicalPiece[][] piecesArray;
	
	/** The group of fields in
	 * the board. */
	private Group squaresGroup;
	
	/** The group of graphical pieces
	 * moving on the board. */
	private Group piecesGroup;
	
	/** The proxy to the server. */
	private ServerHandler serverHandler;
	
	/** The array of fields
	 * in the board. */
	private Square[][] board 
		= new Square[Config.CLASSICAL_CHECKERS_BOARD_WIDTH][Config.CLASSICAL_CHECKERS_BOARD_HEIGHT];
	
	/**
	 * The number of possible types
	 * of checkers game.
	 */
	private final static int numOfTypes = 1;

	/**
	 * The array of possible types 
	 * of checkers game.
	 */
	private final static String[] gameTypesArray =
		{"classic", "second"};
	
	
	/**
	 * Gets the game type.
	 *
	 * @return String the gameType
	 */
	public String getGameType() {
		return gameTypesArray[gameId-1];
	}
	
	

	


	/**
	 * Method determining who is the first player
	 * and who is the second player
	 * according to who has connected first
	 * with the server.
	 *
	 * @return int information if player
	 * playing from this window is first
	 * or second player.
	 */
	private int receiveInitFromServer() {
		try {
			int player = serverHandler.receiveInitFromServer();
			if (player == Config.PLAYER1) {
				System.out.println("My turn");

			} else {
				System.out.println("Opposite turn");
			}
			return player;
		} catch (IOException e) {
			System.out.println("Read failed");
			System.exit(1);
			return -1;
		}
	}

	

	/**
	 * Method starting thread of the game
	 * of player playing from this window.
	 */
	private void startThread() {
		Thread gTh = new Thread(this);
		gTh.start();
	}

	/**
	 * method responsible for running player's thread
	 */
	@Override
	public void run() {
		
		int player = controller.getPlayer();
		f(player);
	}

	/**
	 * Method delegating performing game's operation
	 * according to if it is this player's turn or
	 * opposite player's turn.
	 * If it is this player's turn
	 * it waits for his move if not
	 * it delegates performing
	 * enemy's move.
	 *
	 * @param iPlayer number of the player
	 * playing from this windows.
	 */
	void f(int iPlayer) {
		controller.insertNewGameIntoDataBase(iPlayer);
		while (true) {
			synchronized (this) {
				int actualPlayer = controller.getPlayer();
				if (actualPlayer == iPlayer) {
					try {
						wait(10);
					} catch (InterruptedException e) {
					}
				}
				int showing = controller.getShowing();
				if(controller.getGameState() == GameState.WIN) {
					System.out.println("Gracz wygral");
					Platform.runLater(() -> {
						endingGameAlert.setTitle("Koniec gry");
						endingGameAlert.setHeaderText("Koniec gry");
						endingGameAlert.setContentText("Wygrales!");
						endingGameAlert.initModality(Modality.APPLICATION_MODAL);
						endingGameAlert.setOnCloseRequest(event ->{
							serverHandler.sendMessage("bye");
	                    	System.exit(0);
						});
						Optional<ButtonType> clickedButton  = endingGameAlert.showAndWait();
	                    if(clickedButton.get() == ButtonType.OK){
	                    	serverHandler.sendMessage("bye");
	                    	System.exit(0);
	                    }
					});
				}
				if (showing == Config.ACTIVE) {
					controller.makeEnemyMove();
					System.out.println(boardModel);
					showing = Config.NONACTIVE;
					controller.setShowing(showing);
					if(controller.getGameState() == GameState.LOST) {
						System.out.println("Gracz przegral");
						Platform.runLater(() -> {
							endingGameAlert.setTitle("Koniec gry");
							endingGameAlert.setHeaderText("Koniec gry");
							endingGameAlert.setContentText("Przegrales");
							endingGameAlert.initModality(Modality.APPLICATION_MODAL);
							endingGameAlert.setOnCloseRequest(event ->{
								serverHandler.sendMessage("bye");
		                    	System.exit(0);
							});
							Optional<ButtonType> clickedButton  = endingGameAlert.showAndWait();
		                    if(clickedButton.get() == ButtonType.OK){
		                    	serverHandler.sendMessage("bye");
		                    	System.exit(0);
		                    }
						});
						
					}
				}
				notifyAll();
			}
		}
	}

	/**
	 * Method initializing model of the game
	 * and controller of player playing
	 * from this window.
	 *
	 * @param player the player playing 
	 * from this window
	 */
	private void initMVC(int player) {
		this.boardModel = new BoardModel(player);
		System.out.println(player);
		String boardString = boardModel.toString();
		System.out.println(boardString);
		controller = new GameController(this.boardModel, piecesArray, board, serverHandler, 
				turnLabel, piecesGroup, this.getGameType());
		controller.setPlayer(player);
		controller.setActualPlayer(Config.PLAYER1);
	}

	/**
	 * Method initializing proxy to the server.
	 */
	private void initServerHandler() {
		try {
			this.serverHandler = new ServerHandler(Config.LOCALHOST_ADDRESS, Config.PORT);
			this.socket = serverHandler.getSocket();
		} catch (UnknownHostException e) {
			System.out.println("Unknown host: localhost");
			System.exit(1);
		} catch (IOException e) {
			System.out.println("No I/O");
			System.exit(1);
		}

	}






	/**
	 * Method of class CheckersGame initializing 
	 * window of the application.
	 *
	 * @param arg0 the top level 
	 * container of this application
	 * @throws Exception the exception thrown
	 * when it occurs an error.
	 */
	public CheckersGameBot(int gameType) throws Exception {
//		Parameters parameters = getParameters();
//		String gameTypeId = parameters.getNamed().get("gameType");
//		String gameTypeId = args[0];
//		try {
//			int typeId = Integer.parseInt(gameTypeId);
//			System.out.println(gameTypeId);
//			this.gameId = typeId;
//		}
//		catch(NumberFormatException ex) {
//			System.out.println(ex.getMessage());
//		}
		this.gameId = gameType;
		initServerHandler();
		
		int player = receiveInitFromServer();
		initMVC(player);
		startThread();

	}

	/**
	 * The main method testing CheckersGame class.
	 *
	 * @param args the arguments with which 
	 * the program has been invoked.
	 */
	public static void main(String[] args) {
		String gameTypeId = args[0];
		try {
			int typeId = Integer.parseInt(gameTypeId);
			System.out.println(gameTypeId);
			CheckersGameBot bot = new CheckersGameBot(typeId);
		}
		catch(NumberFormatException ex) {
			System.out.println(ex.getMessage());
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		
//		Application.launch(CheckersGame.class,
//				"--gameType=" + args[0]);
		//Parameters parameters = getParameters(args[0]);
		//String gameTypeId = parameters.getNamed().get("gameType");
//		String gameTypeId = args[0];
//		try {
//			int typeId = Integer.parseInt(gameTypeId);
//			System.out.println(gameTypeId);
//			this.gameId = typeId;
//		}
//		catch(NumberFormatException ex) {
//			System.out.println(ex.getMessage());
//		}
//		initServerHandler();
//		
//		
//		initBoardStage();
//		int player = receiveInitFromServer();
//		initMVC(player);
//		startThread();
	}

}
