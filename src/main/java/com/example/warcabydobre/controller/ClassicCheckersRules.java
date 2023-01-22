package com.example.warcabydobre.controller;

import com.example.warcabydobre.model.BoardModel;
import com.example.warcabydobre.model.InvalidMoveException;
import com.example.warcabydobre.model.ModelMove;
import com.example.warcabydobre.model.MovementTypes;
import com.example.warcabydobre.model.NotDiagonalException;
import com.example.warcabydobre.model.PieceObject;
import com.example.warcabydobre.view.PieceColor;

/**
 * The class responsible for checking moves 
 * according to classic checkers game's
 * rules.
 * 
 */
public class ClassicCheckersRules implements GameRules {

	/**
	 * Model of the game that stores current pieces' 
	 * positions and properties.
	 */
	private BoardModel boardModel;

	/**
	 * Constructor of ClassicCheckersRules class
	 *
	 * @param boardModel model of the game
	 */
	public ClassicCheckersRules(BoardModel boardModel) {
		this.boardModel = boardModel;
	}

	/**
	 * Method checking if queen can
	 * move to passed field according
	 * to classic checkers' rules.
	 * @param oldX x coordinate of initial position of the move
	 * @param oldY y coordinate of initial position of the move
	 * @param newX x coordinate of final position of the move
	 * @param newY y coordinate of final position of the move
	 * @return true, if queen can move to the given field
	 * on the board
	 */
	private boolean isQueenDiagonalMove(int oldX, int oldY, int newX, int newY) {
		if(!(Math.abs(newX - oldX) == Math.abs(newY - oldY))) {
			return false;
		}
		try {
			int numberOfWhite = boardModel.countPiecesBetween(oldX, oldY, newX, newY, PieceColor.WHITE);
			int numberOfBlack = boardModel.countPiecesBetween(oldX, oldY, newX, newY, PieceColor.BLACK);
			if(numberOfWhite + numberOfBlack <= 1) {
				return true;
			}
			else {
				return false;
			}
		} 
		catch (NotDiagonalException e) {
			return false;
		}
	}

	
	
	/**
	 * Method checking if move is valid and if it is valid it returns type of the
	 * move. It operates according to classic checkers game's rules.
	 *
	 * @param oldX x coordinate of initial position of the move
	 * @param oldY y coordinate of initial position of the move
	 * @param newX x coordinate of final position of the move
	 * @param newY y coordinate of final position of the move
	 * @return type of the move
	 * @throws InvalidMoveException the exception thrown when the move is incorrect
	 */
	@Override
	public ModelMove tryMove(int oldX, int oldY, int newX, int newY) throws InvalidMoveException {
		if (boardModel.isOccupied(newX, newY) || !boardModel.isBlackSquare(newX, newY)) {
			return new ModelMove(MovementTypes.NONE);
		}

		PieceObject pieceObject = boardModel.getPiecesArray()[oldX][oldY];
		if (pieceObject == null) {
			throw new InvalidMoveException("Na tym polu nie ma pionka");
		}
		PieceColor oppositeColor = pieceObject.getOppositeColor();
		PieceColor myColor = pieceObject.getColor();
		
		if (pieceObject.isQueen() && isQueenDiagonalMove(oldX, oldY, newX, newY)) {
			try {
				int numberOfMyPieces = 
						boardModel.countPiecesBetween(oldX, oldY, newX, newY, myColor);
				int numberOfEnemysPieces = 
						boardModel.countPiecesBetween(oldX, oldY, newX, newY, oppositeColor);
				if(numberOfEnemysPieces == 1 && numberOfMyPieces == 0) {
					return new ModelMove(MovementTypes.QUEEN_CAPTURE, oldX, oldY, newX, newY);
				}
				if(numberOfEnemysPieces == 0 && numberOfMyPieces == 0) {
					return new ModelMove(MovementTypes.QUEEN_DIAGONAL, oldX, oldY, newX, newY);
				}
			} catch (NotDiagonalException e) {
				System.out.println(0);
			}
			
		}

		if (Math.abs(newX - oldX) == 1 && newY - oldY == pieceObject.getMovementDirection())
			return new ModelMove(MovementTypes.FORWARD, oldX, oldY, newX, newY);
		else if (Math.abs(newX - oldX) == 2 && Math.abs(newY - oldY) == 2) {
			int x1 = oldX + (newX - oldX) / 2;
			int y1 = oldY + (newY - oldY) / 2;

			PieceObject secondPiece = boardModel.getPiecesArray()[x1][y1];

			if (boardModel.isOccupied(x1, y1) && secondPiece.getColor() != pieceObject.getColor()) {
				return new ModelMove(MovementTypes.SINGLE_CAPTURE, secondPiece, oldX, oldY, newX, newY);
			}
		}
		return new ModelMove(MovementTypes.NONE);
	}

}
