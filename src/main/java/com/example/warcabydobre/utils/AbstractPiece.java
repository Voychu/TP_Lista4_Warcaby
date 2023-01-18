package com.example.warcabydobre.utils;

import com.example.warcabydobre.view.PieceColor;


/**
 * Abstract class representing model Piece.
 *
 * @author Jan Poreba
 */
public abstract class AbstractPiece {

	/** The x coordinate of the piece. */
	private int x;
	
	/** The x coordinate of the piece. */
	private int y;
	
	/** The color of the piece. */
	private PieceColor color;
	
	/**
	 * Constructor of abstractPiece
	 *
	 * @param x the x coordinate of the piece.
	 * @param y the y coordinate of the piece.
	 * @param color the color of the piece.
	 */
	public AbstractPiece(int x, int y, PieceColor color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}

	/**
	 * Gets the x.
	 *
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Gets the y.
	 *
	 * @return the y
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Gets the color.
	 *
	 * @return the color
	 */
	public PieceColor getColor() {
		return color;
	}

	/**
	 * Sets the x.
	 *
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Sets the y.
	 *
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	

	/**
	 * Sets the color.
	 *
	 * @param color the color to set
	 */
	public void setColor(PieceColor color) {
		this.color = color;
	}
	
	
	/**
	 * Gets the movement direction of the pieces
	 * based on its color.
	 *
	 * @return the movement direction of the piece
	 */
	public int getMovementDirection() {
		return color.getMovementDirection();
	}

}
