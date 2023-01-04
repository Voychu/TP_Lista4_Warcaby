package com.example.warcabydobre;

import java.util.LinkedList;

public class BoardModel {
	
	private static final int numCols = Config.CLASSICAL_CHECKERS_BOARD_WIDTH;
    private static final int numRowsWithPieces = Config.CLASSICAL_CHECKERS_ROWS_WITH_PIECES;
	

	private LinkedList<PieceObject> piecesList;
	private int turn;
	private PieceColor pieceColor;
	
	
	public BoardModel(int player) {
		this.piecesList = new LinkedList<>();
		if(player == 1) {
			this.pieceColor = PieceColor.WHITE;
		}
		else {
			this.pieceColor = PieceColor.BLACK;
		}
		
		this.turn = 1;
		for (int y=0; y<numRowsWithPieces; y++){
            for(int x = 0; x< numCols; x++) {
                if((x+y)%2 ==1){
                    PieceObject pieceObject = new PieceObject(PieceColor.WHITE, x, y);
                    piecesList.add(pieceObject);
                    //System.out.println("x" + x + "y" + y);
                }
            }
        }
        for (int y=5; y<5+numRowsWithPieces; y++){
            for(int x = 0; x< numCols; x++) {
                if((x+y)%2 ==1){
                	PieceObject pieceObject = new PieceObject(PieceColor.BLACK, x, y);
                    piecesList.add(pieceObject);
                    //System.out.println("x" + x + "y" + y);
                }
            }
        }
	}
	
	
	

}
