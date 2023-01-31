package com.example.warcabydobre.bot;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Optional;

import com.example.warcabydobre.bot.controller.BotController;
import com.example.warcabydobre.bot.model.GameData;
import com.example.warcabydobre.controller.GameController;
import com.example.warcabydobre.controller.GameState;
import com.example.warcabydobre.model.BoardModel;
import com.example.warcabydobre.model.InvalidMoveException;
import com.example.warcabydobre.srvhandler.ServerHandler;
import com.example.warcabydobre.utils.Config;


public class CheckersGameBot implements Runnable{

	/** Flag representing gameType. */
	private int gameId;
	
	
	
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

	


	/** The socket of player playing
	 * from this windows. */
	private Socket socket = null;
	
	/** The controller of player
	 * playing from this window. */
	private BotController controller;
	
	/** The model of the game of player
	 * playing from this windows. */
	private GameData gameData;
	
	
	/** The proxy to the server. */
	private ServerHandler serverHandler;
	
	
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
		//controller.insertNewGameIntoDataBase(iPlayer);
		while (true) {
			synchronized (this) {
				int actualPlayer = controller.getActualPlayer();
				if(controller.getGameState() == GameState.WIN) {
					System.out.println("Bot wygral");
					serverHandler.sendMessage("bye");
                	System.exit(0);
				}
				if(controller.getGameState() == GameState.LOST) {
					System.out.println("Bot przegral");
					serverHandler.sendMessage("bye");
                	System.exit(0);
				}
				if (actualPlayer == iPlayer) {
					controller.drawMove();
					try {
						wait(10);
					} catch (InterruptedException e) {
					}
				}
				int showing = controller.getShowing();
				if(controller.getGameState() == GameState.WIN) {
					System.out.println("Bot wygral");
					serverHandler.sendMessage("bye");
                	System.exit(0);
				}
				if (showing == Config.ACTIVE) {
					controller.makeEnemyMove();
					System.out.println(gameData);
					showing = Config.NONACTIVE;
					controller.setShowing(showing);
					if(controller.getGameState() == GameState.LOST) {
						System.out.println("Bot przegral");
						serverHandler.sendMessage("bye");
                    	System.exit(0);
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
		this.gameData = new GameData(player);
		System.out.println(player);
		String boardString = gameData.toString();
		System.out.println(boardString);
		controller = new BotController(this.gameData, serverHandler, 
				this.getGameType());
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
	}

}
