package com.example.warcabydobre.dataobjects;

import com.example.warcabydobre.srv.Commands;

/**
 * The Class representing move.
 * It is needed to send informations
 * about the move to the data base.
 */
public class MoveObject {
	
	/** The game id. */
	private int gameId;
	
	/** The move id. */
	private int moveId;
	
	/** The move string. */
	private String moveString;
	
	
	/**
	 * Instantiates a new move object.
	 *
	 * @param gameId the game id
	 * @param moveId the move id
	 * @param oldX the old X
	 * @param oldY the old Y
	 * @param newX the new X
	 * @param newY the new Y
	 */
	public MoveObject(int gameId, int moveId, 
			int oldX, int oldY, int newX, int newY) {
		this.gameId = gameId;
		this.moveId = moveId;
		this.moveString = Commands.MOVE.getCommand() + " " + oldX + " " + oldY +" "+
				newX + " " + newY;
	}


	/**
	 * Gets the game id.
	 *
	 * @return the gameId
	 */
	public int getGameId() {
		return gameId;
	}


	/**
	 * Gets the move id.
	 *
	 * @return the moveId
	 */
	public int getMoveId() {
		return moveId;
	}


	/**
	 * Gets the move string.
	 *
	 * @return the moveString
	 */
	public String getMoveString() {
		return moveString;
	}


	/**
	 * Sets the game id.
	 *
	 * @param gameId the gameId to set
	 */
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}


	/**
	 * Sets the move id.
	 *
	 * @param moveId the moveId to set
	 */
	public void setMoveId(int moveId) {
		this.moveId = moveId;
	}


	/**
	 * Sets the move string.
	 *
	 * @param moveString the moveString to set
	 */
	public void setMoveString(String moveString) {
		this.moveString = moveString;
	}

}
