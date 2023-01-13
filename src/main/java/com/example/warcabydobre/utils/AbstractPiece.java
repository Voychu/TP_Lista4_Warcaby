package com.example.warcabydobre.utils;

import com.example.warcabydobre.view.PieceColor;

public abstract class AbstractPiece {

	private int x;
	private int y;
	private PieceColor color;
	
	public AbstractPiece(int x, int y, PieceColor color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @return the color
	 */
	public PieceColor getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(PieceColor color) {
		this.color = color;
	}
	
	
	public int getMovementDirection() {
		return color.getMovementDirection();
	}

}
