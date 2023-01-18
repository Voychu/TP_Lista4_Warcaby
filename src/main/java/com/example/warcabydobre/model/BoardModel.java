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



/**
 * The Class BoardModel. It is model of the game.
 * It stores current pieces' positions and properties.
 * @author Jan Poreba
 */
public class BoardModel {
	
	/** The Constant numCols number of columns in board*/
	private static final int numCols = Config.CLASSICAL_CHECKERS_BOARD_WIDTH;
	
	/** The Constant numRows number of rows in board*/
	private static final int numRows = Config.CLASSICAL_CHECKERS_BOARD_HEIGHT;
    
    /** The Constant numRowsWithPieces. 
     * number of rows with pieces on the board*/
    private static final int numRowsWithPieces 
    	= Config.CLASSICAL_CHECKERS_ROWS_WITH_PIECES;
    
    

    /** Model array of the pieces representing by
     * pieceObjects */
    private PieceObject[][] piecesArray; 
	

	
	/** List of listeners assigned to pieceObjects*/
	private LinkedList<Listener> listeners;
	
	
	/**
	 * Constructor of BoardModel class
	 *
	 * @param player the player whose the boardModel is
	 */
	public BoardModel(int player) {
		this.listeners = new LinkedList<>();
		this.piecesArray = new PieceObject[numCols][numRows];
		
		for (int y=0; y<numRowsWithPieces; y++){
            for(int x = 0; x< numCols; x++) {
                if(isBlackSquare(x,y)){
                    PieceObject pieceObject = new PieceObject(x, y, PieceColor.BLACK);
                    piecesArray[x][y] = pieceObject;
                }
            }
        }
        for (int y=5; y<5+numRowsWithPieces; y++){
            for(int x = 0; x< numCols; x++) {
                if(isBlackSquare(x,y)){
                	PieceObject pieceObject = new PieceObject(x, y, PieceColor.WHITE);
                	piecesArray[x][y] = pieceObject;
                }
            }
        }
	}
	
	/**
	 * Gets the pieces array.
	 *
	 * @return the piecesArray
	 */
	public PieceObject[][] getPiecesArray() {
		return piecesArray;
	}
	
	
    
    /**
     * The listener interface for receiving 
     * pieceObjects events and handling it.
     */
    public interface Listener {
    	
	    /**
	     * Method called on piece's move
	     *
	     * @param x the x coordinate of piece
	     * @param y the y coordinate of piece
	     */
	    void onMove(int x, int y);
    	
	    /**
	     * Method called on deleting piece.
	     */
	    void onDelete();
    	
	    /**
	     * Method called on piece's transformation to queen
	     *
	     * @param x the x coordinate of piece
	     * @param y the y coordinate of piece
	     */
	    void onTransform(int x, int y);
    }
    
    /**
     * The pieceListener class for receiving pieceObjects events.
     * The class that is interested in processing a pieceObject
     * event implements this interface, and the object created
     * with that class is registered with a graphicalPiece using the
     * addListener() method. When
     * the pieceObject event occurs, the appropriate
     * operation on graphicalPiece is performed.
     *
     * @see Listener
     * @see GraphicalPiece
     */
    public class PieceListener implements Listener{

    	/**
	     * Gets the piece object.
	     *
	     * @return the pieceObject
	     */
		public PieceObject getPieceObject() {
			return pieceObject;
		}

		/**
		 * Sets the piece object.
		 *
		 * @param pieceObject the pieceObject to set
		 */
		public void setPieceObject(PieceObject pieceObject) {
			this.pieceObject = pieceObject;
		}

		/**
		 * Sets the graphical piece.
		 *
		 * @param graphicalPiece the graphicalPiece to set
		 */
		public void setGraphicalPiece(GraphicalPiece graphicalPiece) {
			this.graphicalPiece = graphicalPiece;
		}

		
		
		/** Graphical representation of the piece. */
		private GraphicalPiece graphicalPiece;
    	
	    /** Model representation of piece. */
	    private PieceObject pieceObject;
    	
	    /** Graphical representation of the board. */
	    private Square[][] board;
    	
	    /** Array of graphicalPieces on the board */
	    private GraphicalPiece[][] piecesArray;
    	

    	
    	/**
	     * Constructor of PieceListener
	     *
	     * @param graphicalPiece the graphicalPiece on which 
	     * listener will perform actions
	     * @param pieceObject the piece object 
	     * whose actions listener will listen
	     * @param board graphical board on which 
	     * graphical pieces move
	     * @param piecesArray array of graphicalPieces 
	     * on the board
	     */
	    public PieceListener(GraphicalPiece graphicalPiece, PieceObject pieceObject, 
    			Square[][] board, GraphicalPiece[][] piecesArray){
    		this.graphicalPiece = graphicalPiece;
    		this.pieceObject = pieceObject;
    		this.board = board;
    		this.piecesArray = piecesArray;
    	}
    	
		

		/**
		 * Method moving graphicalPiece
		 * on pieceObject's move.
		 *
		 * @param x the x coordinate of field 
		 * where pieceObject moved
		 * @param y the y coordinate of field
		 * where pieceObject moved
		 */
		@Override
		public void onMove(int x, int y) {
			Platform.runLater(() -> graphicalPiece.move(x, y));
			int oldX = GameController.toBoardCoordinates(graphicalPiece.getOldX());
            int oldY = GameController.toBoardCoordinates(graphicalPiece.getOldY());
            Platform.runLater(() -> piecesArray[oldX][oldY] = null);
			Platform.runLater(() -> board[oldX][oldY].setGraphicalPiece(null));
            Platform.runLater(() -> piecesArray[x][y] = graphicalPiece);
            Platform.runLater(() -> board[x][y].setGraphicalPiece(graphicalPiece));
			
		}

		/**
		 * Method deleting graphicalPiece
		 * on pieceObject's deletion
		 */
		@Override
		public void onDelete() {
			int oldX = GameController.toBoardCoordinates(graphicalPiece.getOldX());
            int oldY = GameController.toBoardCoordinates(graphicalPiece.getOldY());
            Platform.runLater(() -> piecesArray[oldX][oldY] = null);
			Platform.runLater(() -> board[oldX][oldY].setGraphicalPiece(null));
			Platform.runLater(() -> graphicalPiece.delete());
			
		}

		/**
		 * Method transforming graphicalPiece to queen
		 * on pieceObject's transformation to queen
		 *
		 * @param x the x coordinate of field
		 * where the transformation is taking place
		 * @param y the y coordinate of field
		 * where the transformation is taking place
		 */
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
		}
		
		
    	
    }
	
    /**
     * Checks if field is black
     *
     * @param x the x coordinate of checked field
     * @param y the y coordinate of checked field
     * @return true, if the field is black
     */
    public boolean isBlackSquare(int x, int y) {
    	return (x+y)%2 == 1;
    }
    
    /**
     * Checks if field is occupied.
     *
     * @param x the x coordinate of checked field
     * @param y the y coordinate of checked field
     * @return true, if the field is occupied
     */
    public boolean isOccupied(int x, int y) {
		return piecesArray[x][y] != null;
	}
	
	
	
	
	
	/**
	 * Adds the listener to the list of pieces' listeners.
	 *
	 * @param listener the listener which is added to the list
	 */
	public void addListener(Listener listener) {
		listeners.add(listener);
	}
	
	
	
	
	/**
	 * Method responsible for moving pieces.
	 * It performs move on pieceObject 
	 * on boardModel's array and
	 * delegates performing move
	 * on graphicalPiece
	 * to the appropriate listener.

	 *
	 * @param xp the x coordinate
	 * of initial position of piece
	 * on the board
	 * @param yp the y coordinate
	 * of initial position of piece
	 * on the board
	 * @param xk the x coordinate
	 * of field where piece is moving
	 * @param yk the y coordinate
	 * of field where piece is moving
	 * @throws InvalidMoveException the exception thrown
	 * when the move is incorrect
	 */
	public void movePieceObject(int xp, int yp, int xk, int yk) throws InvalidMoveException{
		PieceObject piece = piecesArray[xp][yp];
		if(piece == null) {
			throw new InvalidMoveException("brak pionka");
		}
		
		//biale pola
		if(!isBlackSquare(xk,yk)){
            throw new InvalidMoveException("Nie mozna wykonac ruchu na biale pole");
        }
		
		piece.setX(xk);
		piece.setY(yk);
		piecesArray[xp][yp] = null;
		piecesArray[xk][yk] = piece;
		
		
		for(Listener listener : listeners) {
			PieceListener pieceListener = (PieceListener) listener;
			if(piece == pieceListener.getPieceObject()) {
				pieceListener.onMove(xk, yk);
			}
		}
	}
	
	/**
	 * Method responsible for deleting pieces.
	 * It deletes pieceObject from boardModel's
	 * array and delegates performing deletion
	 * of graphicalPiece to the appropriate
	 * listener.
	 * 
	 *
	 * @param x the x coordinate of position 
	 * on the board of deleting piece
	 * @param y the y coordinate of position 
	 * on board of deleting piece
	 * @throws InvalidMoveException the exception thrown
	 * when the move is incorrect
	 */
	public void deletePieceObject(int x, int y) throws InvalidMoveException {
		
		PieceObject piece = piecesArray[x][y];
		
		if(piecesArray[x][y] == null) {
			throw new InvalidMoveException("Nie ma pionka");
		}
		piecesArray[x][y] = null;
		

		for(Listener listener : listeners) {
			PieceListener pieceListener = (PieceListener) listener;
			if(piece == pieceListener.getPieceObject()) {
				pieceListener.onDelete();
			}
		}
	}
	
	
	/**
	 * Method responsible for transforming pieces to queen.
	 * It sets pieceObject to queen and
	 * delegates performing transformation
	 * graphicalPiece to queen to the appropriate
	 * listener.
	 *
	 * @param x the x coordinate of field
	 * where the transformation take place
	 * @param y the y coordinate of field
	 * where the transformation take place
	 */
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

	
	/**
	 * Method returning string representation
	 * of boardModel based on actuals state
	 * of piecesArray.
	 *
	 * @return the string representation
	 * of boardModel
	 */
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
	
	
	
	/**
	 * Method counting pieces on diagonal
	 * between two fields we want to move.
	 *
	 * @param oldX the x coordinate of
	 * initial position of the piece
	 * @param oldY the y coordinate of
	 * initial position of the piece
	 * @param newX the x coordinate of field
	 * where we want to move
	 * @param newY x coordinate of field
	 * where we want to move
	 * @return int the number of pieces
	 * situated on the diagonal 
	 * between two fields we want to move
	 * @throws NotDiagonalException the exception 
	 * thrown where the two field aren't on diagonal.
	 */
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
	

	
	
	

}
