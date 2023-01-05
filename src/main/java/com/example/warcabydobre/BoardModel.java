package com.example.warcabydobre;

import java.util.LinkedList;

public class BoardModel {
	
	private static final int numCols = Config.CLASSICAL_CHECKERS_BOARD_WIDTH;
    private static final int numRowsWithPieces = Config.CLASSICAL_CHECKERS_ROWS_WITH_PIECES;
    
    public interface Listener {
    	void onChange(BoardModel model);
    }
	

	private LinkedList<PieceObject> piecesList;
	private int turn;
	private PieceColor pieceColor;
	private LinkedList<Listener> listeners;
	
	
	
	public BoardModel(int player) {
		this.listeners = new LinkedList<>();
		this.piecesList = new LinkedList<>();
		if(player == Config.FIRST) {
			this.pieceColor = PieceColor.WHITE;
		}
		else {
			this.pieceColor = PieceColor.BLACK;
		}
		
		this.turn = Config.FIRST;
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
	
	
	public void addListener(Listener listener) {
		listeners.add(listener);
	}
	
	
	public void setMove() {
		
		notifyObservers();
	}
	
	private void notifyObservers() {
		listeners.stream().forEach(l -> l.onChange(this));
	}
	
	
	

}
