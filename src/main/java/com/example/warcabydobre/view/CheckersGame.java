package com.example.warcabydobre.view;

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

import javafx.application.Application;
import javafx.application.Platform;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
 * The GUI class of the game.
 */
public class CheckersGame extends Application implements Runnable {

	
	/** Flag representing gameType. */
	private int gameId;
	
	
	/** The stage with the game's board. */
	private Stage boardStage;
	
	
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
	private final static int numOfTypes = 3;

	/**
	 * The array of possible types 
	 * of checkers game.
	 */
	private final static String[] gameTypesArray =
		{"classic", "second", "anti"};
	
	
	/**
	 * Gets the game type.
	 *
	 * @return String the gameType
	 */
	public String getGameType() {
		return gameTypesArray[gameId-1];
	}
	
	
	
	
	

	/**
	 * Method initializing window with the board.
	 */
	private void initBoardStage() {

		boardStage = new Stage();
		boardStage.setTitle(Config.APPLICATION_TITLE_TXT);

		boardStage.setResizable(true);
		BorderPane borderPane = new BorderPane();
		squaresGroup = new Group();
		piecesGroup = new Group();
		Pane pane = new Pane();
		pane.setPrefSize(Config.CLASSICAL_CHECKERS_BOARD_WIDTH * Config.SQUARE_CLASSIC_WIDTH,
				Config.CLASSICAL_CHECKERS_BOARD_HEIGHT * Config.SQUARE_CLASSIC_HEIGHT);
		pane.getChildren().addAll(squaresGroup, piecesGroup);

		
		piecesArray = new GraphicalPiece[Config.CLASSICAL_CHECKERS_BOARD_WIDTH][Config.CLASSICAL_CHECKERS_BOARD_HEIGHT];

		double helpw = Config.SQUARE_CLASSIC_WIDTH;
		double helph = Config.SQUARE_CLASSIC_HEIGHT;
		for (int j = 0; j < numRows; j++) {
			for (int i = 0; i < numCols; i++) {
				double x = i * helpw;
				double y = j * helph;
				if ((i + j) % 2 == 0) {
					WhiteSquare wSquare = new WhiteSquare(Config.SQUARE_CLASSIC_WIDTH, Config.SQUARE_CLASSIC_HEIGHT);
					board[i][j] = wSquare;
					squaresGroup.getChildren().addAll(wSquare);
					wSquare.relocate(x, y);
				} else {
					BlackSquare bSquare = new BlackSquare(Config.SQUARE_CLASSIC_WIDTH, Config.SQUARE_CLASSIC_HEIGHT);
					board[i][j] = bSquare;
					squaresGroup.getChildren().addAll(bSquare);
					bSquare.relocate(x, y);
				}
			}
		}
		GraphicalPiece wPiece;
		GraphicalPiece bPiece;
		for (int j = 0; j < numRowsWithPieces; j++) {
			for (int i = 0; i < numCols; i++) {
				if ((i + j) % 2 == 1) {
					bPiece = makeGraphicalPiece(PieceColor.BLACK, i, j, false);
					piecesArray[i][j] = bPiece;
					board[i][j].setGraphicalPiece(bPiece);
					piecesGroup.getChildren().addAll(bPiece);
					double x = i * helpw;
					double y = j * helph;
					bPiece.relocate(x, y);

				}
			}
		}
		for (int j = 5; j < 5 + numRowsWithPieces; j++) {
			for (int i = 0; i < numCols; i++) {
				if ((i + j) % 2 == 1) {
					wPiece = makeGraphicalPiece(PieceColor.WHITE, i, j, false);
					piecesArray[i][j] = wPiece;
					board[i][j].setGraphicalPiece(wPiece);
					piecesGroup.getChildren().addAll(wPiece);
					double x = i * helpw;
					double y = j * helph;
					wPiece.relocate(x, y);

				}
			}
		}

		borderPane.setCenter(pane);



		VBox lVBox = new VBox();


		turnLabel = new Label("Status:");
		turnLabel.setPrefWidth(100);
		HBox messageLabelHBox = new HBox(turnLabel);
		messageLabelHBox.setAlignment(Pos.CENTER);
		messageLabelHBox.setSpacing(Config.GAP);
		lVBox.getChildren().addAll(messageLabelHBox);
		lVBox.setAlignment(Pos.CENTER);
		lVBox.setSpacing(Config.GAP);

		borderPane.setLeft(lVBox);

		Scene boardScene = new Scene(borderPane, Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
		boardStage.setScene(boardScene);
		boardStage.show();
		


		boardStage.setOnCloseRequest(event -> serverHandler.sendMessage("bye"));
		
		
		endingGameAlert = new Alert(AlertType.CONFIRMATION);

	}



	/**
	 * Make initializing graphical pieces.
	 *
	 * @param color the color of the piece
	 * @param x the x coordinate of
	 * initial position of the piece
	 * @param y the y coordinate of
	 * initial position of the piece
	 * @param isQueen the flag storing
	 * information whether the piece
	 * is queen
	 * @return created graphicalPiece
	 */
	private GraphicalPiece makeGraphicalPiece(PieceColor color, int x, int y, boolean isQueen) {

		GraphicalPiece graphicalPiece;
		if (!isQueen) {
			graphicalPiece = new GraphicalPiece(color, x, y, piecesGroup);
		} else {
			graphicalPiece = new GraphicalQueenPiece(color, x, y, piecesGroup);
		}

		graphicalPiece.setOnMouseReleased(e -> {
			try {
				controller.onPieceMoved(graphicalPiece, e);
				System.out.println(boardModel);

			} catch (InvalidMoveException ex) {
				System.out.println(ex.getMessage());
			}

		});

		return graphicalPiece;
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
				Platform.runLater(() -> turnLabel.setText("My Turn"));

			} else {
				Platform.runLater(() -> turnLabel.setText("Opposite turn"));
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
				int actualPlayer = controller.getActualPlayer();
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
		System.out.println(this.getGameType());
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
	@Override
	public void start(Stage arg0) throws Exception {
		Parameters parameters = getParameters();
		String gameTypeId = parameters.getNamed().get("gameType");
		try {
			int typeId = Integer.parseInt(gameTypeId);
			System.out.println(gameTypeId);
			this.gameId = typeId;
		}
		catch(NumberFormatException ex) {
			System.out.println(ex.getMessage());
		}
		initServerHandler();
		
		
		initBoardStage();
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
		Application.launch(CheckersGame.class,
				"--gameType=" + args[0]);
	}

}
