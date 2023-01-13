package com.example.warcabydobre.controller;

import com.example.warcabydobre.model.BoardModel;
import com.example.warcabydobre.model.InvalidMoveException;
import com.example.warcabydobre.model.ModelMove;
import com.example.warcabydobre.model.MovementTypes;
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

	// TODO: sprawdzic waznosc ruchu. Jesli nie, to zwrocic NONE
	// TODO: Jesli dobry, to zwrocic nowe wspolrzedne intowe oraz fakt, czy jest
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
		if (Math.abs(newX - oldX) == 1 && newY - oldY == pieceObject.getMovementDirection())
			return new ModelMove(MovementTypes.FORWARD, oldX, oldY, newX, newY);
		else if (Math.abs(newX - oldX) == 2 && newY - oldY == pieceObject.getMovementDirection() * 2) {
			int x1 = oldX + (newX - oldX) / 2;
			int y1 = oldY + (newY - oldY) / 2;

			PieceObject secondPiece = boardModel.getPiecesArray()[x1][y1];

			if (boardModel.isOccupied(x1, y1) && secondPiece.getColor() != pieceObject.getColor()) {
				return new ModelMove(MovementTypes.CAPTURE_FORWARD, secondPiece, oldX, oldY, newX, newY);
			}
		}
		return new ModelMove(MovementTypes.NONE);
	}

}
