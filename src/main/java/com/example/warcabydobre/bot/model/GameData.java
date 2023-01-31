package com.example.warcabydobre.bot.model;

import java.util.LinkedList;

import com.example.warcabydobre.controller.GameController;
import com.example.warcabydobre.model.InvalidMoveException;
import com.example.warcabydobre.model.NotDiagonalException;
import com.example.warcabydobre.model.PieceObject;
import com.example.warcabydobre.model.BoardModel.Listener;
import com.example.warcabydobre.model.BoardModel.PieceListener;
import com.example.warcabydobre.utils.Config;
import com.example.warcabydobre.view.PieceColor;


public class GameData {

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
	

	
	
	
	/**
	 * Constructor of BoardModel class
	 *
	 * @param player the player whose the boardModel is
	 */
	public GameData(int player) {
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
		
		if(piecesArray[x][y] == null) {
			throw new InvalidMoveException("Nie ma pionka");
		}
		piecesArray[x][y] = null;
		
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
	 * Method counting pieces of passed color
	 * on diagonal between two fields we want to move.
	 *
	 * @param oldX the x coordinate of
	 * initial position of the piece
	 * @param oldY the y coordinate of
	 * initial position of the piece
	 * @param newX the x coordinate of field
	 * where we want to move
	 * @param newY x coordinate of field
	 * where we want to move
	 * @param color color of pieces which
	 * we want to calculate the number
	 * @return int the number of pieces of 
	 * passed color situated on the diagonal 
	 * between two fields we want to move
	 * @throws NotDiagonalException the exception 
	 * thrown where the two field aren't on diagonal.
	 */
	public int countPiecesBetween(int oldX, int oldY, int newX, int newY, 
			PieceColor color) throws NotDiagonalException {
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
			PieceObject currentPiece = piecesArray[currX][currY];
			if(this.isOccupied(currX, currY) && currentPiece.getColor() == color) {
				counter++;
			}
			currX += stepX;
			currY += stepY;
		}
		return counter;
	}
	
	/**
	 * Method deleting first piece between
	 * two passed fields
	 * @param oldX the x coordinate of
	 * initial position of moving piece
	 * @param oldY the y coordinate of
	 * initial position of moving piece
	 * @param newX the x coordinate of field
	 * where we want to move
	 * @param newY x coordinate of field
	 * where we want to move
	 */
	public void deleteCapturedPiece(int oldX, int oldY, int newX, int newY) {
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
		while(currX != newX && currY != newY) {
			if(this.isOccupied(currX, currY)) {
				try {
					deletePieceObject(currX, currY);
				} catch (InvalidMoveException ex) {
					System.out.println(ex.getMessage());
				}
				return;
			}
			currX += stepX;
			currY += stepY;
		}
	}

}
