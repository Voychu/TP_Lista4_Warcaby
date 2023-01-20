package com.example.warcabydobre.controller;



import com.example.warcabydobre.model.BoardModel;
import com.example.warcabydobre.model.MovementTypes;
import com.example.warcabydobre.model.BoardModel.Listener;
import com.example.warcabydobre.model.InvalidMoveException;
import com.example.warcabydobre.model.ModelMove;
import com.example.warcabydobre.srvhandler.InvalidCommandException;
import com.example.warcabydobre.srvhandler.MoveConverter;
import com.example.warcabydobre.srvhandler.ServerHandler;
import com.example.warcabydobre.utils.Config;
import com.example.warcabydobre.view.GraphicalPiece;
import com.example.warcabydobre.view.GraphicalQueenPiece;
import com.example.warcabydobre.view.PieceColor;
import com.example.warcabydobre.view.Square;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;




/**
 * Class of controller. MVC design pattern. 
 * It is responsible for
 * controlling application. It delegates performing 
 * actions on the basis of player's actions.
 * @author Jan Poreba
 */
public class GameController {

	
	/** The message sending from one player
	 * to the other one through the server. */
	private String message;
	
	/** Model storing data of the game */
	private BoardModel boardModel;
	
	
	/** It is responsible for checking moves
	 * based on the rules of chosen game type. */
	private GameRules rules;
	
	/** Array of the graphicalPieces from
	 * the board */
	private GraphicalPiece[][] graphicalPiecesArray;
	
	/** Proxy to the server */
	private ServerHandler serverHandler;

	
	/** It is current piece that
	 * has moved. */
	private GraphicalPiece pieceHelper = null;
	
	/** Label displaying whether is
	 * myTurn or oppositeTurn */
	private Label turnLabel;

	/** Flag storing information which 
	 * player has now his turn */
	private int actualPlayer = Config.PLAYER1;

	/** Flag storing information which player' actions
	 * is the controller managing*/
	private int player;

	/** Flag to managing blocking sending messages 
	 * by other player in short period
	 * when my message is coming to him. */
	private int showing = Config.ACTIVE;
	
	/**
	 * Type of the checkers game
	 */
	String gameType;

	/**
	 * Constructor of GameController
	 *
	 * @param boardModel model storing data of the game
	 * @param graphicalPiecesArray array of the graphicalPieces 
	 * from the board
	 * @param serverHandler proxy to the server
	 * @param turnLabel Label displaying whether is
	 * myTurn or oppositeTurn 
	 * @param piecesGroup group of the graphicalPieces 
	 * on the board
	 */
	public GameController(BoardModel boardModel, GraphicalPiece[][] graphicalPiecesArray, Square[][] board,
			ServerHandler serverHandler, Label turnLabel, Group piecesGroup, String gameType) {
		this.boardModel = boardModel;
		this.graphicalPiecesArray = graphicalPiecesArray;
		this.gameType = gameType;
		GameRulesFactory gameRulesFactory = new GameRulesFactory();
		this.rules = gameRulesFactory.getGameRules(gameType, boardModel);
		for (int j = 0; j < Config.CLASSICAL_CHECKERS_BOARD_WIDTH; j++) {
			for (int i = 0; i < Config.CLASSICAL_CHECKERS_BOARD_HEIGHT; i++) {
				GraphicalPiece graphicalPiece = graphicalPiecesArray[i][j];
				if (graphicalPiece != null) { 
					Listener pieceListener = boardModel.new PieceListener(graphicalPiece,
							boardModel.getPiecesArray()[i][j], board, graphicalPiecesArray);
					boardModel.addListener(pieceListener);
				}
			}
		}
		this.serverHandler = serverHandler;
		this.turnLabel = turnLabel;
	}
	
	/**
	 * Gets the board model.
	 *
	 * @return the board model
	 */
	public BoardModel getBoardModel() {
		return boardModel;
	}


	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Gets the actual player.
	 *
	 * @return the actualPlayer
	 */
	public int getActualPlayer() {
		return actualPlayer;
	}
	
	/**
	 * Gets the showing.
	 *
	 * @return the showing
	 */
	public int getShowing() {
		return showing;
	}
	
	/**
	 * Gets the player.
	 *
	 * @return the player whose actions the 
	 * controller is managing.
	 */
	public int getPlayer() {
		return player;
	}
	
	/**
	 * Gets the other player.
	 *
	 * @return the other player
	 */
	private int getOtherPlayer() {
		if (player == Config.PLAYER1) {
			return Config.PLAYER2;
		} else {
			return Config.PLAYER1;
		}
	}

	/**
	 * Sets the message.
	 *
	 * @param message the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	

	/**
	 * Sets the actual player.
	 *
	 * @param actualPlayer the actualPlayer to set
	 */
	public void setActualPlayer(int actualPlayer) {
		this.actualPlayer = actualPlayer;
	}

	/**
	 * Sets the showing.
	 *
	 * @param showing the showing to set
	 */
	public void setShowing(int showing) {
		this.showing = showing;
	}

	

	/**
	 * Sets the player.
	 *
	 * @param player the player whose actions the 
	 * controller is managing.
	 */
	public void setPlayer(int player) {
		this.player = player;
	}

	

	

	/**
	 * Static method responsible for converting
	 * pixel dimensions of graphical board
	 * to natural board coordinates based on
	 * squares size.
	 *
	 * @param pixel dimension in pixels
	 * @return int board coordinate corresponding
	 * pixel value
	 */
	public static int toBoardCoordinates(double pixel) {
		return (int) (pixel + Config.SQUARE_CLASSIC_WIDTH / 2) / (int) (Config.SQUARE_CLASSIC_WIDTH);
	}

	/**
	 * Checks if graphicalPiece has appropriate color
	 * to this player.
	 *
	 * @param graphicalPiece the graphical piece which
	 * player wants to move
	 * @return true, if graohicalPiece has appropriate color
	 */
	public boolean isAppropriateColor(GraphicalPiece graphicalPiece) {
		if (player == 1 && graphicalPiece.getColor() == PieceColor.WHITE) {
			return true;
		}
		if (player == 2 && graphicalPiece.getColor() == PieceColor.BLACK) {
			return true;
		}
		return false;
	}

	/**
	 * Method performing actions based on player's
	 * move. It delegates checking whether
	 * the move was correct and 
	 * aborts move or delegates operations
	 * based on player's move to the bordModel.
	 *
	 * @param graphicalPiece graphical piece which has moved
	 * @param event event which has happened on the
	 * graphical board.
	 * @throws InvalidMoveException the exception thrown
	 * when the move is incorrect.
	 */
	public void onPieceMoved(GraphicalPiece graphicalPiece, MouseEvent event) throws InvalidMoveException {
		boolean appropriateColor = isAppropriateColor(graphicalPiece);
		if ((player != actualPlayer) || (!appropriateColor)) {
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
		} else {

			modelResult = rules.tryMove(oldX, oldY, newX, newY);
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
				if (graphicalPiece.getColor() == PieceColor.WHITE && newY == 0) {
					boardModel.transformToQueen(newX, newY);
					Platform.runLater(() -> initGraphicalQueen(newX, newY));

				}

				// tworzenie damki
				if (graphicalPiece.getColor() == PieceColor.BLACK && newY == 7) {
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
			try {
				boardModel.movePieceObject(oldX, oldY, newX, newY);
				int x1 = oldX + (newX - oldX) / 2;
				int y1 = oldY + (newY - oldY) / 2;
				boardModel.deletePieceObject(x1, y1);

				// tworzenie damki
				if (graphicalPiece.getColor() == PieceColor.WHITE && newY == 0) {
					boardModel.transformToQueen(newX, newY);
					Platform.runLater(() -> initGraphicalQueen(newX, newY));

				}

				// tworzenie damki
				if (graphicalPiece.getColor() == PieceColor.BLACK && newY == 7) {
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
			break;
		case QUEEN_CAPTURE:
			try {
				boardModel.movePieceObject(oldX, oldY, newX, newY);
				int x1 = 0;
				int y1 = 0;
				if(newX>oldX && newY>oldY)
				{
					x1 = newX-1;
					y1 = newY-1;
				}
				else if(newX<oldX && newY>oldY)
				{
					x1 = newX+1;
					y1 = newY-1;
				}
				else if(newX>oldX && newY<oldY)
				{
					x1 = newX-1;
					y1 = newY+1;
				}
				else if(newX<oldX && newY<oldY)
				{
					x1 = newX+1;
					y1 = newY-1;
				}
				message = MoveConverter.convertMoveToString(modelResult);
				serverHandler.sendMessage(message);
				Platform.runLater(() -> turnLabel.setText("OppositeTurn"));
				showing = Config.ACTIVE;
				actualPlayer = getOtherPlayer();

			} catch (InvalidMoveException ex) {
				System.out.println(ex.getMessage());
			}
			break;
		default:
			break;
		}
	}

	

	/**
	 * Methods initializing moving on newly
	 * created graphicalQueen.
	 *
	 * @param newX x coordinate where
	 * the queen was created
	 * @param newY y coordinate where
	 * the queen was created
	 */
	public void initGraphicalQueen(int newX, int newY) {
		GraphicalPiece graphicalQueen = graphicalPiecesArray[newX][newY];
		graphicalQueen.setOnMouseReleased(e -> {
			Platform.runLater(() -> {
				try {
					onPieceMoved(graphicalQueen, e);
				} catch (InvalidMoveException ex) {
					System.out.println(ex.getMessage());
					graphicalQueen.abortMove();
				}
			});
			System.out.println(boardModel);
		});

	}

	
	/**
	 * Method canceling current piece's move
	 */
	public void abortMove() {
		pieceHelper.abortMove();
		pieceHelper = null;
	}

	/**
	 * Method responsible for
	 * receiving message from other player
	 * through serverHandler
	 *
	 * @return string the message from opposite player
	 */
	public String receiveMessage() {
		return serverHandler.receiveMessage();
	}

	/**
	 * Method responsible for receiving
	 * move from opposite player
	 *
	 * @return ModelMove the move of opposite player
	 */
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

	/**
	 * Method performing actions based on 
	 * opposite player's moves.
	 * It delegates operations
	 * based on enemy's move to the bordModel.
	 *
	 */
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
				if (graphicalPiece.getColor() == PieceColor.WHITE && newY == 0) {
					boardModel.transformToQueen(newX, newY);
					Platform.runLater(() -> initGraphicalQueen(newX, newY));

				}

				// tworzenie damki
				if (graphicalPiece.getColor() == PieceColor.BLACK && newY == 7) {
					boardModel.transformToQueen(newX, newY);
					Platform.runLater(() -> initGraphicalQueen(newX, newY));
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
				if (graphicalPiece.getColor() == PieceColor.WHITE && newY == 0) {
					boardModel.transformToQueen(newX, newY);
					Platform.runLater(() -> initGraphicalQueen(newX, newY));

				}

				// tworzenie damki
				if (graphicalPiece.getColor() == PieceColor.BLACK && newY == 7) {
					boardModel.transformToQueen(newX, newY);
					Platform.runLater(() -> initGraphicalQueen(newX, newY));
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
				Platform.runLater(() -> turnLabel.setText("MyTurn"));
				actualPlayer = player;
			} catch (InvalidMoveException ex) {
				System.out.println("Niaprawidlowy ruch");
			}
			break;
		case QUEEN_CAPTURE:
			try {
				boardModel.movePieceObject(oldX, oldY, newX, newY);
				int x1 = 0;
				int y1 = 0;
				if(newX>oldX && newY>oldY)
				{
				x1 = newX-1;
				y1 = newY-1;
				}
				else if(newX<oldX && newY>oldY)
				{
				x1 = newX+1;
				y1 = newY-1;
				}
				else if(newX>oldX && newY<oldY)
				{
				x1 = newX-1;
				y1 = newY+1;
				}
				else if(newX<oldX && newY<oldY)
				{
				x1 = newX+1;
				y1 = newY-1;
				}
				boardModel.deletePieceObject(x1, y1);
				Platform.runLater(() -> turnLabel.setText("MyTurn"));
				actualPlayer = player;

			} catch (InvalidMoveException ex) {
				System.out.println(ex.getMessage());
			}
			break;
		default:
			break;

		}
	}

}
