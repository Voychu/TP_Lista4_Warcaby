package com.example.warcabydobre;

import java.util.LinkedList;

public class BoardModel {
	
	private static final int numCols = Config.CLASSICAL_CHECKERS_BOARD_WIDTH;
    private static final int numRowsWithPieces = Config.CLASSICAL_CHECKERS_ROWS_WITH_PIECES;
	

	private LinkedList<PieceObject> piecesList;
	private int turn;
	private int playerColor;
	
	
	public BoardModel(int playerColor) {
		this.piecesList = new LinkedList<>();
		this.playerColor = playerColor;
		this.turn = 1;
		for (int y=0; y<numRowsWithPieces; y++){
            for(int x = 0; x< numCols; x++) {
                if((x+y)%2 ==1){
                    PieceObject pieceObject = new PieceObject(Config.FIRST, x, y);
                    piecesList.add(pieceObject);
                    //System.out.println("x" + x + "y" + y);
                }
            }
        }
        for (int y=5; y<5+numRowsWithPieces; y++){
            for(int x = 0; x< numCols; x++) {
                if((x+y)%2 ==1){
                	PieceObject pieceObject = new PieceObject(Config.SECOND, x, y);
                    piecesList.add(pieceObject);
                    //System.out.println("x" + x + "y" + y);
                }
            }
        }
	}
	
	
	

}
