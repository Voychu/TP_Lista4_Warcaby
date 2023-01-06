package com.example.warcabydobre.view;

public enum PieceColor {

	WHITE(-1), BLACK(1);

	//public final int pieceColor;

	/*PieceColor(int i) {
		pieceColor = i;
	}*/

	private final int movementDirection;

	PieceColor(int movementDirection) {
		this.movementDirection = movementDirection;
	}

	public int getMovementDirection() {
		return movementDirection;
	}

}
