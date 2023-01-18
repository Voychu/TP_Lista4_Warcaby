package com.example.warcabydobre.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Scanner;

import com.example.warcabydobre.controller.GameController;
import com.example.warcabydobre.model.BoardModel;
import com.example.warcabydobre.model.InvalidMoveException;
import com.example.warcabydobre.model.MovementTypes;
import com.example.warcabydobre.srvhandler.InvalidCommandException;
import com.example.warcabydobre.srvhandler.ServerHandler;
import com.example.warcabydobre.utils.Config;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

public class CheckersGame extends Application implements Runnable {

	/**
	 * @return the gameType
	 */
	public int getGameType() {
		return gameType;
	}

	private static int MARGIN_X = 30;
	private static int MARGIN_Y = 30;
	private int gameType;
	// private Stage choosingGameStage;
	private Stage boardStage;
	private static final int numCols = Config.CLASSICAL_CHECKERS_BOARD_WIDTH;
	private static final int numRows = Config.CLASSICAL_CHECKERS_BOARD_HEIGHT;
	private static final int numRowsWithPieces = Config.CLASSICAL_CHECKERS_ROWS_WITH_PIECES;
	private Label textLabel;
	private TextField sendingField;
	Label turnLabel;
	private Button confirmButton;
	private Alert endingGameAlert;


	private Socket socket = null;
	private GameController controller;
	private BoardModel boardModel;
	private GraphicalPiece[][] piecesArray;
	private Group squaresGroup;
	private Group piecesGroup;
	private ServerHandler serverHandler;
	private Square[][] board = new Square[Config.CLASSICAL_CHECKERS_BOARD_WIDTH][Config.CLASSICAL_CHECKERS_BOARD_HEIGHT];

	

	/*
	 * private void initchoosingGameStage() { choosingGameStage = new Stage();
	 * 
	 * choosingGameStage.setTitle(Config.APPLICATION_TITLE_TXT); VBox vBox = new
	 * VBox(); Label classicalCheckersLabel = new
	 * Label(Config.CLASSICAL_CHECKERS_LABEL_TXT); double size1 =
	 * classicalCheckersLabel.getMaxWidth(); Label secondCheckersLabel = new
	 * Label(Config.SECOND_CHECKERS_LABEL_TXT); double size2 =
	 * secondCheckersLabel.getPrefWidth(); HBox labelsHBox = new
	 * HBox(classicalCheckersLabel, secondCheckersLabel);
	 * labelsHBox.setAlignment(Pos.CENTER); labelsHBox.setSpacing(Config.GAP);
	 * Button classicalCheckersButton = new
	 * Button(Config.CLASSICAL_CHECKERS_BUTTON_TXT);
	 * classicalCheckersButton.setPrefWidth(size1); Button secondCheckersButton =
	 * new Button(Config.SECOND_CHECKERS_BUTTON_TXT);
	 * secondCheckersButton.setPrefWidth(size2); HBox buttonsHBox = new
	 * HBox(classicalCheckersButton, secondCheckersButton);
	 * buttonsHBox.setAlignment(Pos.CENTER); buttonsHBox.setSpacing(Config.GAP);
	 * vBox.getChildren().addAll(labelsHBox, buttonsHBox);
	 * vBox.setAlignment(Pos.CENTER); vBox.setSpacing(Config.GAP);
	 * choosingGameStage.setResizable(true); Scene windowScene = new Scene(vBox,
	 * Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
	 * choosingGameStage.setScene(windowScene); choosingGameStage.show();
	 * 
	 * 
	 * 
	 * 
	 * EventHandler<ActionEvent> eventHandler_classicalcheckers = new
	 * EventHandler<ActionEvent>() { public void handle(ActionEvent event) {
	 * listenSocket(); //receiveInitFromServer(); //startThread();
	 * 
	 * initBoardStage();
	 * 
	 * 
	 * 
	 * 
	 * } }; classicalCheckersButton.setOnAction(eventHandler_classicalcheckers); }
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

		VBox vBox = new VBox();
		textLabel = new Label("Gra: test");
		HBox labelHBox = new HBox(textLabel);
		labelHBox.setAlignment(Pos.CENTER);
		labelHBox.setSpacing(Config.GAP);
		vBox.getChildren().addAll(labelHBox);
		vBox.setAlignment(Pos.CENTER);
		vBox.setSpacing(Config.GAP);

		borderPane.setRight(vBox);

		VBox lVBox = new VBox();

		sendingField = new TextField();
		HBox textFieldHBox = new HBox(sendingField);
		textFieldHBox.setAlignment(Pos.CENTER);
		textFieldHBox.setSpacing(Config.GAP);
		confirmButton = new Button("wyslij");
		HBox buttonHBox = new HBox(confirmButton);
		buttonHBox.setAlignment(Pos.CENTER);
		buttonHBox.setSpacing(Config.GAP);

		turnLabel = new Label("Status:");
		HBox messageLabelHBox = new HBox(turnLabel);
		messageLabelHBox.setAlignment(Pos.CENTER);
		messageLabelHBox.setSpacing(Config.GAP);
		lVBox.getChildren().addAll(textFieldHBox, buttonHBox, messageLabelHBox);
		lVBox.setAlignment(Pos.CENTER);
		lVBox.setSpacing(Config.GAP);

		borderPane.setLeft(lVBox);

		Scene boardScene = new Scene(borderPane, Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
		boardStage.setScene(boardScene);
		boardStage.show();
		// choosingGameStage.close();

		EventHandler<ActionEvent> sendingMessageHandler = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				// sendMessage();

			}
		};
		confirmButton.setOnAction(sendingMessageHandler);

		boardStage.setOnCloseRequest(event -> serverHandler.sendMessage("bye"));

	}



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

	public void setLabelText(String text) {
		textLabel.setText(text);
	}


	private int receiveInitFromServer() {
		try {
			int player = serverHandler.receiveInitFromServer();
			if (player == Config.PLAYER1) {
				Platform.runLater(() -> turnLabel.setText("My Turn"));

			} else {
				Platform.runLater(() -> turnLabel.setText("Opposite turn"));
				Platform.runLater(() -> confirmButton.setDisable(true));
			}
			return player;
		} catch (IOException e) {
			System.out.println("Read failed");
			System.exit(1);
			return -1;
		}
	}

	public void getSendingText() {
	}

	private void startThread() {
		Thread gTh = new Thread(this);
		gTh.start();
	}

	@Override
	public void run() {
		/*
		 * if (player==PLAYER1) { f1(); } else{ f2(); }
		 */
		// Mozna zrobic w jednej metodzie. Zostawiam
		// dla potrzeb prezentacji
		int player = controller.getPlayer();
		f(player);
	}

	// Jedna metoda dla kazdego Playera
	void f(int iPlayer) {
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
				if (showing == Config.ACTIVE) {
					controller.makeEnemyMove();
					System.out.println(boardModel);
					showing = Config.NONACTIVE;
					controller.setShowing(showing);
				}
				notifyAll();
			}
		}
	}

	private void initMVC(int player) {
		this.boardModel = new BoardModel(player);
		System.out.println(player);
		String boardString = boardModel.toString();
		System.out.println(boardString);
		controller = new GameController(this.boardModel, piecesArray, board, serverHandler, turnLabel, piecesGroup);
		controller.setPlayer(player);
		controller.setActualPlayer(Config.PLAYER1);
	}

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

	@Override
	public void start(Stage arg0) throws Exception {
		initServerHandler();
		
		// TODO: wybor trybow gry z kosnoli 1, 2, 3
		// TODO: walidacja trybow
//		System.out.println("Podaj tryb: ");
//		System.out.print("-> ");
//		Scanner sc = new Scanner(System.in);
//		int i = sc.nextInt();
//		String message = Integer.toString(i);
//		serverHandler.sendMessage(message);
		initBoardStage();
		

		int player = receiveInitFromServer();

		initMVC(player);
		startThread();

	}

	public static void main(String[] args) {

		launch(args);

	}

}
