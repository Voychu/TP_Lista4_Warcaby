package com.example.warcabydobre.controller;

import com.example.warcabydobre.model.InvalidMoveException;
import com.example.warcabydobre.model.ModelMove;
import com.example.warcabydobre.view.PieceColor;

/**
 * The Interface containing methods responsible for checking move
 */
public interface GameRules {

	/**
	 * Method returning color of pieces
	 * of passed player
	 * @param player number of player in
	 * the game
	 * @return color of player's pieces
	 * @throws InvalidPlayerException the exception 
	 * thrown when player number is incorrect
	 */
	public PieceColor getPieceColor(int player) throws InvalidPlayerException;
	
	
	
	/**
	 * Method checking whether player playing with pieces of passed color can move
	 * 
	 * @param color color of pieces of checked player
	 * @return true, if player can move
	 */
	public boolean canPlayerMove(PieceColor color);

	/**
	 * Method checking if move is valid and if it is valid it returns type of the
	 * move
	 *
	 * @param oldX x coordinate of initial position of the move
	 * @param oldY y coordinate of initial position of the move
	 * @param newX x coordinate of final position of the move
	 * @param newY y coordinate of final position of the move
	 * @return type of the move
	 * @throws InvalidMoveException the exception thrown when the move is incorrect
	 */
	public ModelMove tryMove(int oldX, int oldY, int newX, int newY) throws InvalidMoveException;


	/**
	 * Method checking if player wins
	 * @param color of player's pieces
	 * @return true if the player wins 
	 */
	boolean playerWin(PieceColor color);


	/**
	 * Method checking if player lost.
	 *@param color of player's pieces
	 * @return true if the player lost 
	 */
	boolean playerLost(PieceColor color);

}
