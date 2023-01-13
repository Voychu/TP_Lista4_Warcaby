package com.example.warcabydobre.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Optional;

import com.example.warcabydobre.controller.GameController;
import com.example.warcabydobre.model.BoardModel;
import com.example.warcabydobre.model.InvalidMoveException;
import com.example.warcabydobre.model.MovementTypes;
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

	private static int MARGIN_X = 30;
	private static int MARGIN_Y = 30;
	// private Stage choosingGameStage;
	private Stage boardStage;
	private static final int numCols = Config.CLASSICAL_CHECKERS_BOARD_WIDTH;
	private static final int numRows = Config.CLASSICAL_CHECKERS_BOARD_HEIGHT;
	private static final int numRowsWithPieces = Config.CLASSICAL_CHECKERS_ROWS_WITH_PIECES;
	private Label textLabel;
	private TextField sendingField;
	Label messageLabel;
	private Button confirmButton;
	private Alert endingGameAlert;

	private static int actualPlayer = Config.PLAYER1;

	private static int showing = Config.ACTIVE;

	Socket socket = null;
	PrintWriter out = null;
	BufferedReader in = null;
	private GameController controller;
	private BoardModel boardModel;
	/* private Square[][] squaresArray; */
	// private LinkedList<GraphicalPiece> graphicalPiecesList;
	private GraphicalPiece[][] piecesArray;// TODO: edytowanie tablicy przy przeuswaniu pionkow
	private Group squaresGroup;
	private Group piecesGroup;
	private ServerHandler serverHandler;
	private Square[][] board = new Square[Config.CLASSICAL_CHECKERS_BOARD_WIDTH][Config.CLASSICAL_CHECKERS_BOARD_HEIGHT];

	/*
	 * Połaczenie z socketem
	 */
	public void listenSocket() {
		try {
			socket = new Socket("localhost", 4444);
			// Inicjalizacja wysylania do serwera
			out = new PrintWriter(socket.getOutputStream(), true);
			// Inicjalizacja odbierania z serwera
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (UnknownHostException e) {
			System.out.println("Unknown host: localhost");
			System.exit(1);
		} catch (IOException e) {
			System.out.println("No I/O");
			System.exit(1);
		}
	}

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
		// ClassicCheckersBoard ccb = new ClassicCheckersBoard(in, out);
		// Scene windowScene = ccb.getBoardScene();
		BorderPane borderPane = new BorderPane();
		squaresGroup = new Group();
		piecesGroup = new Group();
		Pane pane = new Pane();
		pane.setPrefSize(Config.CLASSICAL_CHECKERS_BOARD_WIDTH * Config.SQUARE_CLASSIC_WIDTH,
				Config.CLASSICAL_CHECKERS_BOARD_HEIGHT * Config.SQUARE_CLASSIC_HEIGHT);
		pane.getChildren().addAll(squaresGroup, piecesGroup);

		/*
		 * squaresArray = new Square[numCols][numRows]; whitePiecesList = new
		 * LinkedList<>(); blackPiecesList = new LinkedList<>();
		 */
		// graphicalPiecesList = new LinkedList<>();
		piecesArray = new GraphicalPiece[Config.CLASSICAL_CHECKERS_BOARD_WIDTH][Config.CLASSICAL_CHECKERS_BOARD_HEIGHT];

		double helpw = Config.SQUARE_CLASSIC_WIDTH;
		double helph = Config.SQUARE_CLASSIC_HEIGHT;
		// boolean white = true;
		// int a = 0;
		// int b = 0;
		// y = j * helph
		// x = i * helw
		for (int j = 0; j < numRows; j++) {
			for (int i = 0; i < numCols; i++) {
				double x = i * helpw;
				double y = j * helph;
				if ((i + j) % 2 == 0) {
					WhiteSquare wSquare = new WhiteSquare(Config.SQUARE_CLASSIC_WIDTH, Config.SQUARE_CLASSIC_HEIGHT);
					// squaresArray[a][b] = wSquare;
					board[i][j] = wSquare;
					squaresGroup.getChildren().addAll(wSquare);
					wSquare.relocate(x, y);
					// a+=1;
				} else {
					BlackSquare bSquare = new BlackSquare(Config.SQUARE_CLASSIC_WIDTH, Config.SQUARE_CLASSIC_HEIGHT);
					board[i][j] = bSquare;
					squaresGroup.getChildren().addAll(bSquare);
					bSquare.relocate(x, y);
					// a+=1;
				}
				// white=!white;
			}
			// white=!white;t
			// a=0;
			// b+=1;
		}
		// y = j * helph
		// x = i * helw
		GraphicalPiece wPiece;
		GraphicalPiece bPiece;
		// double offset = (Config.SQUARE_CLASSIC_WIDTH - 2*Config.PIECE_RADIUS)/2;
		for (int j = 0; j < numRowsWithPieces; j++) {
			for (int i = 0; i < numCols; i++) {
				if ((i + j) % 2 == 1) {
					// BlackPiece bPiece = new BlackPiece(Config.PIECE_RADIUS);
					bPiece = makeGraphicalPiece(PieceColor.BLACK, i, j);
					// blackPiecesList.add(bPiece);
					// graphicalPiecesList.add(bPiece);
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

					// WhitePiece wPiece = new WhitePiece(Config.PIECE_RADIUS);
					wPiece = makeGraphicalPiece(PieceColor.WHITE, i, j);
					// whitePiecesList.add(wPiece);
					// graphicalPiecesList.add(wPiece);
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

		messageLabel = new Label("Status:");
		HBox messageLabelHBox = new HBox(messageLabel);
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
				//sendMessage();

			}
		};
		confirmButton.setOnAction(sendingMessageHandler);

		boardStage.setOnCloseRequest(event -> out.println("bye"));

	}

	/*private Move tryMove(GraphicalPiece graphicalPiece, int newX, int newY) {
		if (board[newX][newY].isOccupied() || (newX + newY) % 2 == 0) {
			return new Move(MovementTypes.NONE);
		}
		int xp = toBoardCoordinates(graphicalPiece.getOldX());
		int yp = toBoardCoordinates(graphicalPiece.getOldY());
		System.out.println(xp + ", " + yp);

		if (Math.abs(newX - xp) == 1 && newY - yp == graphicalPiece.getColor().getMovementDirection())
			return new Move(MovementTypes.FORWARD);
		else if (Math.abs(newX - xp) == 2 && newY - yp == graphicalPiece.getColor().getMovementDirection() * 2) {
			int x1 = xp + (newX - xp) / 2;
			int y1 = yp + (newY - yp) / 2;

			if (board[x1][y1].isOccupied()
					&& board[x1][y1].getGraphicalPiece().getColor() != graphicalPiece.getColor()) {
				return new Move(MovementTypes.CAPTURE_FORWARD, board[x1][y1].getGraphicalPiece());
			}
		}
		return new Move(MovementTypes.NONE);

	}*/

	private int toBoardCoordinates(double pixel) {
		return (int) (pixel + Config.SQUARE_CLASSIC_WIDTH / 2) / (int) (Config.SQUARE_CLASSIC_WIDTH);
	}

	private GraphicalPiece makeGraphicalPiece(PieceColor Color, int x, int y) {
		GraphicalPiece graphicalPiece = new GraphicalPiece(Color, x, y, piecesGroup);
		

        graphicalPiece.setOnMouseReleased(e -> {
        	try {
        		//controller.onPieceMoved(graphicalPiece,e);
        		controller.onPieceMovedOld(graphicalPiece,e);
			} catch (InvalidMoveException ex) {
				System.out.println(ex.getMessage());
			}
        	
		});

		return graphicalPiece;
	}

	public void setLabelText(String text) {
		textLabel.setText(text);
	}

	/*
	 * public String sendMessage() { return sendingField.getText(); }
	 */

	/*public void sendMessage() {
		String message = sendingField.getText();
		out.println(message);
		// messageLabel.setText("OppositeTurn");
		Platform.runLater(() -> messageLabel.setText("OppositeTurn"));
		// send.setEnabled(false);
		// sendingField.setText("");
		Platform.runLater(() -> sendingField.setText(""));

		Platform.runLater(() -> textLabel.setText(""));

		Platform.runLater(() -> confirmButton.setDisable(true));

		if (message.equals("bye")) {
			try {

				Platform.runLater(() -> System.exit(0));
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// input.requestFocus();
		showing = ACTIVE;
		actualPlayer = player;
	}*/

//	public void receiveMessage() {
//		try {
//			// Odbieranie z serwera
//			String str = in.readLine();
//
//			if (str.equals("bye")) {
//				/*
//				 * endingGameAlert.setTitle("Uwaga");
//				 * endingGameAlert.setHeaderText("Rozlaczono z serwerem.");
//				 * endingGameAlert.setContentText("Czy chcesz zamknac okno?");
//				 * endingGameAlert.initModality(Modality.APPLICATION_MODAL);
//				 * 
//				 * Optional<ButtonType> clickedButton = endingGameAlert.showAndWait();
//				 * if(clickedButton.get() == ButtonType.OK){ System.exit(0); }
//				 */
//				// Platform.runLater(()->System.exit(0));
//				System.exit(0);
//				socket.close();
//			}
//			// textLabel.setText(str);
//			Platform.runLater(() -> textLabel.setText(str));
//			// messageLabel.setText("My turn");
//			Platform.runLater(() -> messageLabel.setText("My turn"));
//			// send.setEnabled(true);
//			// sendingField.setText("");
//			Platform.runLater(() -> sendingField.setText(""));
//
//			Platform.runLater(() -> confirmButton.setDisable(false));
//			// input.requestFocus();
//		} catch (IOException e) {
//			System.out.println("Read failed");
//			System.exit(1);
//		}
//	}

	private int receiveInitFromServer() {
		try {
			int player = serverHandler.receiveInitFromServer();
			if (player == Config.PLAYER1) {
				// messageLabel.setText("My Turn");
				Platform.runLater(() -> messageLabel.setText("My Turn"));

			} else {
				// messageLabel.setText("Opposite turn");
				Platform.runLater(() -> messageLabel.setText("Opposite turn"));
				Platform.runLater(() -> confirmButton.setDisable(true));
				// sendingField.setEnabled(false);
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
				if (actualPlayer == iPlayer) {
					try {
						wait(10);
					} catch (InterruptedException e) {
					}
				}
				if (showing == Config.ACTIVE) {
					
					
					controller.makeEnemyMove();
					//String message = serverHandler.receiveMessage();
					showing = Config.NONACTIVE;
				}
				notifyAll();
			}
		}
	}

	private void initMVC(int player) {
		this.boardModel = new BoardModel(player);
		System.out.println(player);
		controller = new GameController(this.boardModel, piecesArray, board, serverHandler);
		controller.setPlayer(player);
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
		// tworzenie kontrolera
		initBoardStage();
		//listenSocket();
		initServerHandler();
		
		int player = receiveInitFromServer();
		
		initMVC(player);
		startThread();

	}

	public static void main(String[] args) {

		launch(args);

	}

}
