package com.example.warcabydobre.bot.controller;

import java.util.LinkedList;
import java.util.Random;

import com.example.warcabydobre.bot.model.GameData;
import com.example.warcabydobre.controller.GameRules;
import com.example.warcabydobre.controller.GameRulesFactory;
import com.example.warcabydobre.controller.GameState;
import com.example.warcabydobre.controller.InvalidPlayerException;
import com.example.warcabydobre.db.BusinessLogic;
import com.example.warcabydobre.db.BusinessLogicIF;
import com.example.warcabydobre.model.InvalidMoveException;
import com.example.warcabydobre.model.ModelMove;
import com.example.warcabydobre.model.MovementTypes;
import com.example.warcabydobre.model.PieceObject;
import com.example.warcabydobre.srvhandler.InvalidCommandException;
import com.example.warcabydobre.srvhandler.MoveConverter;
import com.example.warcabydobre.srvhandler.ServerHandler;
import com.example.warcabydobre.utils.Config;
import com.example.warcabydobre.view.GraphicalPiece;
import com.example.warcabydobre.view.PieceColor;

/**
 * Class of controller. MVC design pattern. 
 * It is responsible for
 * controlling bot. It delegates performing 
 * actions on the basis of drawn move.
 * @author Jan Poreba
 */
public class BotController {
	/**
	 * The Constant numCols number of columns on the board
	 */
	private final int numCols = Config.CLASSICAL_CHECKERS_BOARD_WIDTH;
	/**
	 * The Constant numRows number of rows on the board
	 */
	private final int numRows = Config.CLASSICAL_CHECKERS_BOARD_HEIGHT;
	/** The message sending from one player
	 * to the other one through the server. */
	private String message;
	
	/** Model storing data of the game */
	private GameData gameData;
	
	
	/** It is responsible for checking moves
	 * based on the rules of chosen game type. */
	private GameRules rules;
	
	
	/** Proxy to the server */
	private ServerHandler serverHandler;

	

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
	private String gameType;
	
	/**
	 * Object of business logic,
	 * according to instructions from application
	 * it calls methods
	 * performing operations 
	 * on data base.
	 */
	private BusinessLogicIF businessLogic;
	
	/**
	 * flag storing information if
	 * player can continue game or lose
	 * or win
	 */
	private GameState gameState;

	/**
	 * Constructor of BotController
	 *
	 * @param gameData model storing data of the game
	 * @param graphicalPiecesArray array of the graphicalPieces 
	 * from the board
	 * @param serverHandler proxy to the server
	 * @param turnLabel Label displaying whether is
	 * myTurn or oppositeTurn 
	 * @param piecesGroup group of the graphicalPieces 
	 * on the board
	 */
	public BotController(GameData gameData, ServerHandler serverHandler, String gameType) {
		this.gameData = gameData;
		this.gameType = gameType;
		GameRulesFactory gameRulesFactory = new GameRulesFactory();
		this.rules = gameRulesFactory.getGameRules(gameType, gameData);
		this.serverHandler = serverHandler;
		this.businessLogic = new BusinessLogic();
		this.gameState = GameState.CONTINUE_GAME;
		
	}
	
	/**
	 * Gets the board model.
	 *
	 * @return the board model
	 */
	public GameData getgameData() {
		return gameData;
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
	 * Gets the gameState.
	 *
	 * @return state of the game
	 */
	public GameState getGameState() {
		return gameState;
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
	 * Method drawing move and sending
	 * it to other player
	 */
	public void drawMove() {
		LinkedList<PieceObject> piecesList = 
				new LinkedList<>();
		for(int y = 0; y< numRows; y++) {
			for(int x = 0; x<numCols; x++) {
				PieceObject currPiece = gameData.getPiecesArray()[x][y];
				try {
					if(gameData.isOccupied(x, y) && currPiece.getColor() == rules.getPieceColor(player)
							&& !rules.possibleMoves(currPiece).isEmpty()) {
						piecesList.add(currPiece);
					}
				} catch (InvalidPlayerException ex) {
					System.out.println(ex.getMessage());
				}
			}
		}
		Random r = new Random();
		int size = piecesList.size();
		int pieceIdx = r.nextInt(size);
		PieceObject randomPiece = piecesList.get(pieceIdx);
		System.out.println(randomPiece.getX() + " " + randomPiece.getY());
		
		LinkedList<ModelMove> posMoves = rules.possibleMoves(randomPiece);
		int moveSize = posMoves.size();
		if(moveSize == 0) {
			System.out.println("Nie mozna wykonac ruchu");
			return;
		}
		System.out.println(moveSize);
		int moveIdx = r.nextInt(moveSize);
		ModelMove modelResult = posMoves.get(moveIdx);
		
		int oldX = modelResult.getOldX();
		int oldY = modelResult.getOldY();
		int newX = modelResult.getNewX();
		int newY = modelResult.getNewY();

		switch (modelResult.getMovementType()) {
		case NONE:
			break;
		case FORWARD:
			try {
				gameData.movePieceObject(oldX, oldY, newX, newY);
				// tworzenie damki
				if (randomPiece.getColor() == PieceColor.WHITE && newY == 0) {
					gameData.transformToQueen(newX, newY);

				}

				// tworzenie damki
				if (randomPiece.getColor() == PieceColor.BLACK && newY == 7) {
					gameData.transformToQueen(newX, newY);
				}
			} catch (InvalidMoveException ex) {
				System.out.println("Niaprawidlowy ruch");
			}

			try {
				message = MoveConverter.convertMoveToString(modelResult);
			} catch (InvalidMoveException e) {
				e.printStackTrace();
			}
			serverHandler.sendMessage(message);
			showing = Config.ACTIVE;
			actualPlayer = getOtherPlayer();
			try {
				PieceColor color = rules.getPieceColor(player);
				if(rules.playerWin(color)) {
					gameState = GameState.WIN;
				}
				if(rules.playerLost(color)) {
					gameState = GameState.LOST;
				}
			} catch (InvalidPlayerException ex) {
				System.out.println(ex.getMessage());
			}
			break;
		case SINGLE_CAPTURE:
			try {
				gameData.movePieceObject(oldX, oldY, newX, newY);
				int x1 = oldX + (newX - oldX) / 2;
				int y1 = oldY + (newY - oldY) / 2;
				gameData.deletePieceObject(x1, y1);

				// tworzenie damki
				if (randomPiece.getColor() == PieceColor.WHITE && newY == 0) {
					gameData.transformToQueen(newX, newY);

				}

				// tworzenie damki
				if (randomPiece.getColor() == PieceColor.BLACK && newY == 7) {
					gameData.transformToQueen(newX, newY);
				}

				message = MoveConverter.convertMoveToString(modelResult);
				serverHandler.sendMessage(message);
				showing = Config.ACTIVE;
				actualPlayer = getOtherPlayer();
				try {
					PieceColor color = rules.getPieceColor(player);
					if(rules.playerWin(color)) {
						gameState = GameState.WIN;
					}
					if(rules.playerLost(color)) {
						gameState = GameState.LOST;
					}
				} catch (InvalidPlayerException ex) {
					System.out.println(ex.getMessage());
				}

			} catch (InvalidMoveException ex) {
				System.out.println("Niaprawidlowy ruch");
			}
			break;
		case QUEEN_DIAGONAL:
			try {
				gameData.movePieceObject(oldX, oldY, newX, newY);
				System.out.println("OK4");
				message = MoveConverter.convertMoveToString(modelResult);
				serverHandler.sendMessage(message);
				showing = Config.ACTIVE;
				actualPlayer = getOtherPlayer();
				try {
					PieceColor color = rules.getPieceColor(player);
					if(rules.playerWin(color)) {
						gameState = GameState.WIN;
					}
					if(rules.playerLost(color)) {
						gameState = GameState.LOST;
					}
				} catch (InvalidPlayerException ex) {
					System.out.println(ex.getMessage());
				}
			} catch (InvalidMoveException ex) {
				System.out.println("Niaprawidlowy ruch");
			}
			break;
		case QUEEN_CAPTURE:
			try {
				gameData.movePieceObject(oldX, oldY, newX, newY);
				gameData.deleteCapturedPiece(oldX, oldY, newX, newY);
				message = MoveConverter.convertMoveToString(modelResult);
				serverHandler.sendMessage(message);
				showing = Config.ACTIVE;
				actualPlayer = getOtherPlayer();
				try {
					PieceColor color = rules.getPieceColor(player);
					if(rules.playerWin(color)) {
						gameState = GameState.WIN;
					}
					if(rules.playerLost(color)) {
						gameState = GameState.LOST;
					}
				} catch (InvalidPlayerException ex) {
					System.out.println(ex.getMessage());
				}

			} catch (InvalidMoveException ex) {
				System.out.println(ex.getMessage());
			}
			break;
		default:
			break;
		}
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
		int oldX = move.getOldX();
		int oldY = move.getOldY();
		int newX = move.getNewX();
		int newY = move.getNewY();
		PieceObject pieceObject = gameData.getPiecesArray()[oldX][oldY];

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

		switch (modelResult.getMovementType()) {
		case NONE:
			break;
		case FORWARD:
			try {
				gameData.movePieceObject(oldX, oldY, newX, newY);
				if (pieceObject.getColor() == PieceColor.WHITE && newY == 0) {
					gameData.transformToQueen(newX, newY);

				}

				// tworzenie damki
				if (pieceObject.getColor() == PieceColor.BLACK && newY == 7) {
					gameData.transformToQueen(newX, newY);
				}
			} catch (InvalidMoveException ex) {
				System.out.println(ex.getMessage());
			}

			actualPlayer = player;
			try {
				PieceColor playerColor = rules.getPieceColor(player);
				if(rules.playerLost(playerColor)) {
					gameState = GameState.LOST;
				}
				if(rules.playerWin(playerColor)) {
					gameState = GameState.WIN;
				}
			} catch (InvalidPlayerException ex) {
				System.out.println(ex.getMessage());
			}
			break;
		case SINGLE_CAPTURE:
			try {
				gameData.movePieceObject(oldX, oldY, newX, newY);
				int x1 = oldX + (newX - oldX) / 2;
				int y1 = oldY + (newY - oldY) / 2;
				gameData.deletePieceObject(x1, y1);
				if (pieceObject.getColor() == PieceColor.WHITE && newY == 0) {
					gameData.transformToQueen(newX, newY);

				}

				// tworzenie damki
				if (pieceObject.getColor() == PieceColor.BLACK && newY == 7) {
					gameData.transformToQueen(newX, newY);
				}
				actualPlayer = player;
				try {
					PieceColor playerColor = rules.getPieceColor(player);
					if(rules.playerLost(playerColor)) {
						gameState = GameState.LOST;
					}
					if(rules.playerWin(playerColor)) {
						gameState = GameState.WIN;
					}
				} catch (InvalidPlayerException ex) {
					System.out.println(ex.getMessage());
				}

			} catch (InvalidMoveException ex) {
				System.out.println(ex.getMessage());
			}
			break;
		case QUEEN_DIAGONAL:
			try {
				gameData.movePieceObject(oldX, oldY, newX, newY);
				actualPlayer = player;
				try {
					PieceColor playerColor = rules.getPieceColor(player);
					if(rules.playerLost(playerColor)) {
						gameState = GameState.LOST;
					}
					if(rules.playerWin(playerColor)) {
						gameState = GameState.WIN;
					}
				} catch (InvalidPlayerException ex) {
					System.out.println(ex.getMessage());
				}
			} catch (InvalidMoveException ex) {
				System.out.println("Niaprawidlowy ruch");
			}
			break;
		case QUEEN_CAPTURE:
			try {
				gameData.movePieceObject(oldX, oldY, newX, newY);
				gameData.deleteCapturedPiece(oldX, oldY, newX, newY);
				actualPlayer = player;
				try {
					PieceColor playerColor = rules.getPieceColor(player);
					if(rules.playerLost(playerColor)) {
						gameState = GameState.LOST;
					}
					if(rules.playerWin(playerColor)) {
						gameState = GameState.WIN;
					}
				} catch (InvalidPlayerException ex) {
					System.out.println(ex.getMessage());
				}

			} catch (InvalidMoveException ex) {
				System.out.println(ex.getMessage());
			}
			break;
		default:
			break;

		}
	}
	
	/**
	 * Method inserting new game into data base
	 * if player is first player
	 * @param gameTypeId the typeId of the
	 * game
	 */
	public void insertNewGameIntoDataBase(int gameTypeId) {
		if(player == Config.FIRST) {
			businessLogic.insertGame(gameTypeId);
		}
	}

}
