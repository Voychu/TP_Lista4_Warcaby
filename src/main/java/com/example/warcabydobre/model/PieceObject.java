package com.example.warcabydobre.model;

import com.example.warcabydobre.utils.AbstractPiece;
import com.example.warcabydobre.view.PieceColor;

//klasa pionka jako obiektu abstrakcyjnego gry
public class PieceObject extends AbstractPiece{
	
	/*private PieceColor color;
	private int x;
	private int y;*/
	boolean isQueen;
	
	public PieceObject(int x, int y, PieceColor color) {
		super(x,y,color);
		this.isQueen = false;
	}


	public boolean isQueen() {
		return isQueen;
	}



	public void setQueen(boolean isQueen) {
		this.isQueen = isQueen;
	}
	
	//TODO:Aktualizacja x,y
	
	
	
	

}
