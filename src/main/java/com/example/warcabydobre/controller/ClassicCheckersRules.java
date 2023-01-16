package com.example.warcabydobre.controller;

import com.example.warcabydobre.model.BoardModel;
import com.example.warcabydobre.model.InvalidMoveException;
import com.example.warcabydobre.model.ModelMove;
import com.example.warcabydobre.model.MovementTypes;
import com.example.warcabydobre.model.NotDiagonalException;
import com.example.warcabydobre.model.PieceObject;

public class ClassicCheckersRules implements GameRules{

	private BoardModel boardModel;

	public ClassicCheckersRules(BoardModel boardModel) {
		this.boardModel = boardModel;
	}


	public boolean isMoveValid() {
		// TODO Auto-generated method stub
		return false;
	}

	// bicie i czy zmienia sie w damke
	public ModelMove tryMove(int oldX, int oldY, int newX, int newY) throws InvalidMoveException {
		if (boardModel.isOccupied(newX, newY) || !boardModel.isBlackSquare(newX, newY)) {
			return new ModelMove(MovementTypes.NONE);
		}
		// System.out.println(xp + ", " + yp);

		PieceObject pieceObject = boardModel.getPiecesArray()[oldX][oldY];
		if (pieceObject == null) {
			throw new InvalidMoveException("Na tym polu nie ma pionka");
		}
		
		if(pieceObject.isQueen() && Math.abs(newX - oldX) == Math.abs(newY - oldY)) {
			try {
				System.out.println(boardModel.countPiecesBetween(oldX, oldY, newX, newY));
			} catch (NotDiagonalException e) {
				System.out.println(0);
			}
			return new ModelMove(MovementTypes.QUEEN_DIAGONAL, oldX, oldY, newX, newY);
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
