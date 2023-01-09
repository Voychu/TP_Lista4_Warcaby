package com.example.warcabydobre.model;

import com.example.warcabydobre.view.PieceColor;

//klasa pionka jako obiektu abstrakcyjnego gry
public class PieceObject {
	
	private PieceColor color;
	private int x;
	private int y;
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

	
	
	public int getMovementDirection() {
		return color.getMovementDirection();
	}

	public boolean isQueen() {
		return isQueen;
	}

	public void setColor(PieceColor color) {
		this.color = color;
	}


	public void setQueen(boolean isQueen) {
		this.isQueen = isQueen;
	}
	
	//TODO:Aktualizacja x,y
	
	
	
	

}
