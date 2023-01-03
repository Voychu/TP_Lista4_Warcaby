package com.example.warcabydobre;

public class PieceObject {
	
	private PieceColor color;
	int x;
	int y;
	boolean isQueen;
	
	public PieceObject(PieceColor color, int x, int y) {
		this.color = color;
		this.x = x;
		this.y = y;
		this.isQueen = false;
	}

	public PieceColor getColor() {
		return color;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isQueen() {
		return isQueen;
	}

	public void setColor(PieceColor color) {
		this.color = color;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setQueen(boolean isQueen) {
		this.isQueen = isQueen;
	}
	
	
	
	

}
