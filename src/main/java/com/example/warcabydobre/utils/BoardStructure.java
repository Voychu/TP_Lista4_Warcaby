package com.example.warcabydobre.utils;

import com.example.warcabydobre.model.InvalidMoveException;
import com.example.warcabydobre.model.PieceObject;


public abstract class BoardStructure<T extends AbstractPiece> {

//	protected T[][] boardArray; 
//	
//	
//	public BoardStructure() {
//		//this.boardArray = board;
//	}
//	
//	/**
//	 * @return the boardArray
//	 */
//	public T[][] getBoardArray() {
//		return boardArray;
//	}
//
//	/**
//	 * @param boardArray the boardArray to set
//	 */
//	public void setBoardArray(T[][] boardArray) {
//		this.boardArray = boardArray;
//	}
//
//	public boolean isBlackSquare(int x, int y) {
//    	return (x+y)%2 == 1;
//    }
//    
//    public boolean isOccupied(int x, int y) {
//		return boardArray[x][y] != null;
//	}
//    
//    
//    public void movePieceObject(int xp, int yp, int xk, int yk) throws InvalidMoveException{
//		T piece = boardArray[xp][yp];
//		if(piece == null) {
//			throw new InvalidMoveException("brak pionka");
//		}
//		
//		//biale pola
//		if(!isBlackSquare(xk,yk)){
//            throw new InvalidMoveException("Nie mozna wykonac ruchu na biale pole");
//        }
//		
//		//TODO metody prywatne do kazdej osobnej procedury
//		//TODO ruch do tylu
//		//TODO pionek nakoncu planszy isQueen = true
//		
//		boardArray[xp][yp] = null;
//		boardArray[xk][yk] = piece;
//		piece.setX(xk);
//		piece.setY(yk);
//		
//		
//		
//	}
//	
//	
//	public T delete(int x, int y) throws InvalidMoveException{
//		T piece = boardArray[x][y];
//		
//		if(boardArray[x][y] == null) {
//			throw new InvalidMoveException("Nie ma pionka");
//		}
//		boardArray[x][y] = null;
//		return piece;
//	}
	
	
	//TODO: Pozostale metody z modelu np. move
	// w movie aktualizacja x i y

}
