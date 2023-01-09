package com.example.warcabydobre.utils;

import com.example.warcabydobre.model.InvalidMoveException;


public class BoardStructure<T extends AbstractPiece> {

	private T[][] board; 
	
	
	public BoardStructure(T[][] board) {
		this.board = board;
	}
	
	
	public T delete(int x, int y) throws InvalidMoveException{
		T piece = board[x][y];
		
		if(board[x][y] == null) {
			throw new InvalidMoveException("Nie ma pionka");
		}
		board[x][y] = null;
		return piece;
	}
	
	
	//TODO: Pozostale metody z modelu np. move
	// w movie aktualizacja x i y

}
