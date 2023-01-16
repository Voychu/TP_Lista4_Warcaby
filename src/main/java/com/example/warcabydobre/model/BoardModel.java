package com.example.warcabydobre.model;

import java.util.LinkedList;

import com.example.warcabydobre.controller.GameController;
import com.example.warcabydobre.utils.Config;
import com.example.warcabydobre.view.GraphicalPiece;
import com.example.warcabydobre.view.GraphicalQueenPiece;
import com.example.warcabydobre.view.PieceColor;
import com.example.warcabydobre.view.Square;

import javafx.application.Platform;
import javafx.scene.Group;


public class BoardModel {
	
	private static final int numCols = Config.CLASSICAL_CHECKERS_BOARD_WIDTH;
	private static final int numRows = Config.CLASSICAL_CHECKERS_BOARD_HEIGHT;
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
    	void onTransform(int x, int y);
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

		/**
		 * @param graphicalPiece the graphicalPiece to set
		 */
		public void setGraphicalPiece(GraphicalPiece graphicalPiece) {
			this.graphicalPiece = graphicalPiece;
		}

		private GraphicalPiece graphicalPiece;
    	private PieceObject pieceObject;
    	private Square[][] board;
    	private GraphicalPiece[][] piecesArray;
    	

    	
    	public PieceListener(GraphicalPiece graphicalPiece, PieceObject pieceObject, 
    			Square[][] board, GraphicalPiece[][] piecesArray){
    		this.graphicalPiece = graphicalPiece;
    		this.pieceObject = pieceObject;
    		this.board = board;
    		this.piecesArray = piecesArray;
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
			//graphicalPiece.move(x, y);
			Platform.runLater(() -> graphicalPiece.move(x, y));
			int oldX = GameController.toBoardCoordinates(graphicalPiece.getOldX());
            int oldY = GameController.toBoardCoordinates(graphicalPiece.getOldY());
			//board[oldX][oldY].setGraphicalPiece(null);
            Platform.runLater(() -> piecesArray[oldX][oldY] = null);
			Platform.runLater(() -> board[oldX][oldY].setGraphicalPiece(null));
			
            //board[x][y].setGraphicalPiece(graphicalPiece);
            Platform.runLater(() -> piecesArray[x][y] = graphicalPiece);
            Platform.runLater(() -> board[x][y].setGraphicalPiece(graphicalPiece));
			
		}

		@Override
		public void onDelete() {
			int oldX = GameController.toBoardCoordinates(graphicalPiece.getOldX());
            int oldY = GameController.toBoardCoordinates(graphicalPiece.getOldY());
            Platform.runLater(() -> piecesArray[oldX][oldY] = null);
			Platform.runLater(() -> board[oldX][oldY].setGraphicalPiece(null));
			Platform.runLater(() -> graphicalPiece.delete());
			
		}

		@Override
		public void onTransform(int x, int y) {
			PieceColor color = graphicalPiece.getColor();
			Group pieceGroup = graphicalPiece.getPiecesGroup();
			
			GraphicalQueenPiece graphicalQueen = new GraphicalQueenPiece(color, x, y, pieceGroup);
			Platform.runLater(() -> pieceGroup.getChildren().addAll(graphicalQueen));
			Platform.runLater(() -> board[x][y].setGraphicalPiece(graphicalQueen));
			Platform.runLater(() -> piecesArray[x][y] = graphicalQueen);
			Platform.runLater(() -> graphicalPiece.delete());
			double helpx = x * Config.SQUARE_CLASSIC_WIDTH;
			double helpy = y * Config.SQUARE_CLASSIC_HEIGHT;
			Platform.runLater(() -> graphicalQueen.relocate(helpx, helpy));
			Platform.runLater(() -> this.setGraphicalPiece(graphicalQueen));
			//this.setGraphicalPiece(graphicalQueen);
			
			
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
		this.piecesArray = new PieceObject[numCols][numRows];
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
                    PieceObject pieceObject = new PieceObject(x, y, PieceColor.BLACK);
                    piecesArray[x][y] = pieceObject;
                    //System.out.println("x" + x + "y" + y);
                }
            }
        }
        for (int y=5; y<5+numRowsWithPieces; y++){
            for(int x = 0; x< numCols; x++) {
                if(isBlackSquare(x,y)){
                	PieceObject pieceObject = new PieceObject(x, y, PieceColor.WHITE);
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
	
	
	public void transformToQueen(int x, int y) {
		PieceObject pieceObject = piecesArray[x][y];
		pieceObject.setQueen(true);
		
		for(Listener listener : listeners) {
			PieceListener pieceListener = (PieceListener) listener;
			if(pieceObject == pieceListener.getPieceObject()) {
				pieceListener.onTransform(x, y);
			}
		}
	}
	public void transformGraphicsToQueen(){
		//transformacja graficzna damki
	}
	
	
	public String toString() {
		String boardString = "";
		for(int y = 0; y < numRows; y++) {
			for(int x = 0; x < numCols; x++) {
				if(piecesArray[x][y] != null) {
					PieceColor color = piecesArray[x][y].getColor();
					if(color == PieceColor.BLACK) {
						boolean isQueen = piecesArray[x][y].isQueen();
						if(isQueen) {
							boardString += "B ";
						}
						else {
							boardString += "b ";
						}
						
					}
					else {
						boolean isQueen = piecesArray[x][y].isQueen();
						if(isQueen) {
							boardString += "W ";
						}
						else {
							boardString += "w ";
						}
					}
				}
				else {
					boardString += "  ";
				}
			}
			boardString += "\n";
		}
		return boardString;
	}
	
	
	
	public int countPiecesBetween(int oldX, int oldY, int newX, int newY) throws NotDiagonalException {
		if(Math.abs(newX - oldX) != Math.abs(newY - oldY)) {
			throw new NotDiagonalException("To nie jest przekatna");
		}
		int stepX = 0, stepY = 0;
		if(newX - oldX > 0) {
			stepX = 1;
		}
		else {
			stepX = -1;
		}
		if(newY - oldY > 0) {
			stepY = 1;
		}
		else {
			stepY = -1;
		}
		int currX = oldX, currY = oldY;
		currX += stepX;;
		currY += stepY;
		int counter = 0;
		while(currX != newX && currY != newY) {
			if(this.isOccupied(currX, currY)) {
				counter++;
			}
			currX += stepX;
			currY += stepY;
		}
		return counter;
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
