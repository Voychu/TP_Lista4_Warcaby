package com.example.warcabydobre.dbdata;

/**
 * The Class representing one checkers
 * game.
 */
public class GameObject {
	
	
	/** The game ID. */
	private int gameID;
	
	/** The game type. */
	private int gameType;
	
	
	/**
	 * Constructor of GameObject
	 *
	 * @param gameID the game ID
	 * @param gameType the game type
	 */
	public GameObject(int gameID, int gameType) {
		this.gameID = gameID;
		this.gameType = gameType;
	}


	/**
	 * Gets the game ID.
	 *
	 * @return the gameID
	 */
	public int getGameID() {
		return gameID;
	}


	/**
	 * Gets the game type.
	 *
	 * @return the gameType
	 */
	public int getGameType() {
		return gameType;
	}


	/**
	 * Sets the game ID.
	 *
	 * @param gameID the gameID to set
	 */
	public void setGameID(int gameID) {
		this.gameID = gameID;
	}


	/**
	 * Sets the game type.
	 *
	 * @param gameType the gameType to set
	 */
	public void setGameType(int gameType) {
		this.gameType = gameType;
	}
	

	

}
