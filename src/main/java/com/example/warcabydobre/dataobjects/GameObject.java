package com.example.warcabydobre.dataobjects;

import java.time.LocalDateTime;

/**
 * The Class representing one checkers
 * game.
 */
public class GameObject {
	
	/**
	 * Id of the game
	 */
	private int gameId;
	
	/** The game type. */
	private int gameType;
	
	/**
	 * Time of starting the game
	 */
	private LocalDateTime dateTime;
	
	
	
	
	/**
	 * Constructor of GameObject
	 *
	 * @param gameType the game type
	 */
	public GameObject(int gameType) {
		this.gameType = gameType;
		dateTime = LocalDateTime.now();
		System.out.println(dateTime);
	}

	
	/**
	 * @return the gameId
	 */
	public int getGameId() {
		return gameId;
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
	 * @return the dateTime
	 */
	public LocalDateTime getDateTime() {
		return dateTime;
	}

	
	
	/**
	 * @param gameId the gameId to set
	 */
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	/**
	 * Sets the game type.
	 *
	 * @param gameType the gameType to set
	 */
	public void setGameType(int gameType) {
		this.gameType = gameType;
	}


	/**
	 * @param dateTime the dateTime to set
	 */
	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
	

	




	




	




	

	

}
