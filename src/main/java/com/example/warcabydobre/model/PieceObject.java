package com.example.warcabydobre.model;

import com.example.warcabydobre.utils.AbstractPiece;
import com.example.warcabydobre.view.PieceColor;


/**
 * Class representing model piece.
 * It is concrete implementation of
 * AbstarctPiece.
 */

public class PieceObject extends AbstractPiece{
	
	/** flags storing information
	 * whether the piece is queen */
	boolean isQueen;
	
	/**
	 * Constructor of pPieceObject
	 *
	 * @param x the x coordinate of the piece.
	 * @param y the y coordinate of the piece.
	 * @param color the color of the piece.
	 */
	public PieceObject(int x, int y, PieceColor color) {
		super(x,y,color);
		this.isQueen = false;
	}


	/**
	 * Checks if is queen.
	 *
	 * @return true, if is queen
	 */
	public boolean isQueen() {
		return isQueen;
	}



	/**
	 * Sets the queen.
	 *
	 * @param isQueen value of isQueen flag
	 */
	public void setQueen(boolean isQueen) {
		this.isQueen = isQueen;
	}
	
	
	

}
