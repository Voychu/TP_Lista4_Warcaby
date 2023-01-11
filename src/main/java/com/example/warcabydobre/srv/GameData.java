package com.example.warcabydobre.srv;

import java.util.LinkedList;

import com.example.warcabydobre.controller.GameController;
import com.example.warcabydobre.model.InvalidMoveException;
import com.example.warcabydobre.model.PieceObject;
import com.example.warcabydobre.model.BoardModel.Listener;
import com.example.warcabydobre.model.BoardModel.PieceListener;
import com.example.warcabydobre.utils.Config;
import com.example.warcabydobre.view.GraphicalPiece;
import com.example.warcabydobre.view.PieceColor;
import com.example.warcabydobre.view.Square;

public class GameData {

	private static final int numCols = Config.CLASSICAL_CHECKERS_BOARD_WIDTH;
    private static final int numRowsWithPieces = Config.CLASSICAL_CHECKERS_ROWS_WITH_PIECES;
    
    

	//private LinkedList<PieceObject> piecesList;
    private PieceObject[][] piecesArray; 
	

	private int turn;
	//private PieceColor pieceColor;
	
	/**
	 * @return the piecesArray
	 */
	public PieceObject[][] getPiecesArray() {
		return piecesArray;
	}
	
    
	
    public boolean isBlackSquare(int x, int y) {
    	return (x+y)%2 == 1;
    }
    
    public boolean isOccupied(int x, int y) {
		return piecesArray[x][y] != null;
	}
	
	
	public GameData() {
		//this.piecesList = new LinkedList<>();
		this.piecesArray = new PieceObject[Config.CLASSICAL_CHECKERS_BOARD_WIDTH][Config.CLASSICAL_CHECKERS_BOARD_HEIGHT];
		/*if(player == Config.FIRST) {
			this.pieceColor = PieceColor.WHITE;
		}
		else {
			this.pieceColor = PieceColor.BLACK;
		}*/
		
		this.turn = Config.FIRST;
		for (int y=0; y<numRowsWithPieces; y++){
            for(int x = 0; x< numCols; x++) {
                if(isBlackSquare(x,y)){
                    PieceObject pieceObject = new PieceObject(PieceColor.BLACK, x, y);
                    piecesArray[x][y] = pieceObject;
                    //System.out.println("x" + x + "y" + y);
                }
            }
        }
        for (int y=5; y<5+numRowsWithPieces; y++){
            for(int x = 0; x< numCols; x++) {
                if(isBlackSquare(x,y)){
                	PieceObject pieceObject = new PieceObject(PieceColor.WHITE, x, y);
                	piecesArray[x][y] = pieceObject;
                    //System.out.println("x" + x + "y" + y);
                }
            }
        }
	}
	
	
	
	
	
	public void movePieceObject(int xp, int yp, int xk, int yk) throws InvalidMoveException{
		PieceObject piece = piecesArray[xp][yp];
		if(piece == null) {
			throw new InvalidMoveException("brak pionka");
		}
		
		//biale pola
		if(!isBlackSquare(xk,yk)){
            throw new InvalidMoveException("Nie mozna wykonac ruchu na biale pole");
        }
		
		//TODO metody prywatne do kazdej osobnej procedury
		//TODO ruch do tylu
		//TODO pionek nakoncu planszy isQueen = true
		
		piecesArray[xp][yp] = null;
		piecesArray[xk][yk] = piece;
		
		
		
	}
	
	public void deletePieceObject(int x, int y) throws InvalidMoveException {
		
		PieceObject piece = piecesArray[x][y];
		
		if(piecesArray[x][y] == null) {
			throw new InvalidMoveException("Nie ma pionka");
		}
		piecesArray[x][y] = null;

	}
	
	

}
