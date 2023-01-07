package com.example.warcabydobre.model;

import java.util.LinkedList;

import com.example.warcabydobre.Config;
import com.example.warcabydobre.controller.GameController;
import com.example.warcabydobre.view.GraphicalPiece;
import com.example.warcabydobre.view.PieceColor;
import com.example.warcabydobre.view.Square;

public class BoardModel {
	
	private static final int numCols = Config.CLASSICAL_CHECKERS_BOARD_WIDTH;
    private static final int numRowsWithPieces = Config.CLASSICAL_CHECKERS_ROWS_WITH_PIECES;
    
    

	//private LinkedList<PieceObject> piecesList;
    private PieceObject[][] piecesArray; 
	

	private int turn;
	private PieceColor pieceColor;
	private LinkedList<Listener> listeners;
	
	/**
	 * @return the piecesArray
	 */
	public PieceObject[][] getPiecesArray() {
		return piecesArray;
	}
	
    
    public interface Listener {
//    	void onChange(BoardModel model, int x, int y);
    	void onMove(int x, int y);
    	void onDelete();
    	//TODO: Dodac metody gdy pionek zmienia sie w damke
    }
    
    public class PieceListener implements Listener{

    	/**
		 * @return the pieceObject
		 */
		public PieceObject getPieceObject() {
			return pieceObject;
		}

		/**
		 * @param pieceObject the pieceObject to set
		 */
		public void setPieceObject(PieceObject pieceObject) {
			this.pieceObject = pieceObject;
		}

		private GraphicalPiece graphicalPiece;
    	private PieceObject pieceObject;
    	private Square[][] board;

    	
    	public PieceListener(GraphicalPiece graphicalPiece, PieceObject pieceObject, Square[][] board){
    		this.graphicalPiece = graphicalPiece;
    		this.pieceObject = pieceObject;
    		this.board = board;
    	}
    	
//		@Override
//		public void onChange(BoardModel model, int x, int y) {
//			int oldX = GameController.toBoardCoordinates(graphicalPiece.getOldX());
//		    int oldY = GameController.toBoardCoordinates(graphicalPiece.getOldY());
//		    
////		    PieceObject pieceObjOld = model.getPiecesArray()[oldX][oldY];
////		    PieceObject pieceObjNew = model.getPiecesArray()[oldX][oldY];
//		    
//		    graphicalPiece.move();
//		    
//		    
//			if (model.getPiecesArray()[x][y] == null) {
//				graphicalPiece.delete();
//			}
//			else if (model.getPiecesArray()[x][y] != null) {
//				
//				graphicalPiece.move(x,y);
//			}
//			
//			
//		}
		

		@Override
		public void onMove(int x, int y) {
			graphicalPiece.move(x, y);
			int oldX = GameController.toBoardCoordinates(graphicalPiece.getOldX());
            int oldY = GameController.toBoardCoordinates(graphicalPiece.getOldY());
			board[oldX][oldY].setGraphicalPiece(null);
            board[x][y].setGraphicalPiece(graphicalPiece);
			
		}

		@Override
		public void onDelete() {
			graphicalPiece.delete();
			
		}
    	
    }
	
    public boolean isBlackSquare(int x, int y) {
    	return (x+y)%2 == 1;
    }
    
    public boolean isOccupied(int x, int y) {
		return piecesArray[x][y] != null;
	}
	
	
	public BoardModel(int player) {
		this.listeners = new LinkedList<>();
		//this.piecesList = new LinkedList<>();
		this.piecesArray = new PieceObject[Config.CLASSICAL_CHECKERS_BOARD_WIDTH][Config.CLASSICAL_CHECKERS_BOARD_HEIGHT];
		if(player == Config.FIRST) {
			this.pieceColor = PieceColor.WHITE;
		}
		else {
			this.pieceColor = PieceColor.BLACK;
		}
		
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
	
	
	public void addListener(Listener listener) {
		listeners.add(listener);
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
		
		for(Listener listener : listeners) {
			PieceListener pieceListener = (PieceListener) listener;
			if(piece == pieceListener.getPieceObject()) {
				pieceListener.onMove(xk, yk);
			}
		}
//		notifyObserver(xp, yp);
		
		
		
	}
	
	public void deletePieceObject(int x, int y) throws InvalidMoveException {
		
		PieceObject piece = piecesArray[x][y];
		
		if(piecesArray[x][y] == null) {
			throw new InvalidMoveException("Nie ma pionka");
		}
		piecesArray[x][y] = null;
//		notifyObserver(x, y);

		for(Listener listener : listeners) {
			PieceListener pieceListener = (PieceListener) listener;
			if(piece == pieceListener.getPieceObject()) {
				pieceListener.onDelete();
			}
		}
	}
	
//	private void notifyObserver(int x, int y) {
//		listeners.stream().forEach(l -> {
//			if (l.getX() == x && l.getY() == y) l.onChange(this, xk, yk);
//		});
//	}
	
	/*private void notifyObservers(int xk, int yk) {
		listeners.stream().forEach(l -> l.onChange(this, xk, yk));
	}*/
	
	
	

}
