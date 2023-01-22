package com.example.warcabydobre.controller;

import com.example.warcabydobre.model.InvalidMoveException;
import com.example.warcabydobre.model.ModelMove;
import com.example.warcabydobre.view.PieceColor;

/**
 * The Interface containing methods responsible for checking move
 */
public interface GameRules {

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

}
