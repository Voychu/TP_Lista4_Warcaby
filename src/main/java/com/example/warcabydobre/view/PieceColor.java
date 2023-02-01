package com.example.warcabydobre.view;

/**
 * The enum which values represent possible piece colors
 * @author Wojciech Bajurny
 */
public enum PieceColor {

	WHITE(-1), BLACK(1);

	//public final int pieceColor;

	/*PieceColor(int i) {
		pieceColor = i;
	}*/

	private final int movementDirection;

	/**
	 * Constructor of PieceColor.
	 * @param movementDirection the direction in which the piece is going to move.
	 */
	PieceColor(int movementDirection) {
		this.movementDirection = movementDirection;
	}

	/**
	 * Gets  the movement direction.
	 * @return movementDirection
	 */
	public int getMovementDirection() {
		return movementDirection;
	}

}
