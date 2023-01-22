package com.example.warcabydobre.dbdata;

/**
 * The Class representing type
 * of the game.
 */
public class GameType {

	/** The game type id. */
	private int gameTypeId;
	
	/** The game type name. */
	private String gameTypeName;
	
	
	/**
	 * Instantiates a new game type.
	 *
	 * @param gameTypeId the game type id
	 * @param gameTypeName the game type name
	 */
	public GameType(int gameTypeId, String gameTypeName) {
		super();
		this.gameTypeId = gameTypeId;
		this.gameTypeName = gameTypeName;
	}


	/**
	 * Gets the game type id.
	 *
	 * @return the gameTypeId
	 */
	public int getGameTypeId() {
		return gameTypeId;
	}


	/**
	 * Gets the game type name.
	 *
	 * @return the gameTypeName
	 */
	public String getGameTypeName() {
		return gameTypeName;
	}


	/**
	 * Sets the game type id.
	 *
	 * @param gameTypeId the gameTypeId to set
	 */
	public void setGameTypeId(int gameTypeId) {
		this.gameTypeId = gameTypeId;
	}


	/**
	 * Sets the game type name.
	 *
	 * @param gameTypeName the gameTypeName to set
	 */
	public void setGameTypeName(String gameTypeName) {
		this.gameTypeName = gameTypeName;
	}
	

}
