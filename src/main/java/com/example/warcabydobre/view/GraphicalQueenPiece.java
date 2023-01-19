package com.example.warcabydobre.view;

import com.example.warcabydobre.utils.Config;

import javafx.scene.Group;

/**
 * Graphical representation of the QueenPiece. It extends the Graphical Piece.
 * @author Jan Poręba
 */
public class GraphicalQueenPiece extends GraphicalPiece {

	/**
	 * The offsets which centers the Piece on a square.
	 */
	private double queenOffset;

	/**
	 * The constructor for the GraphicalQueenPiece.
	 * @param color
	 * @param x
	 * @param y
	 * @param piecesGroup
	 */
	public GraphicalQueenPiece(PieceColor color, int x, int y, Group piecesGroup) {
		super(color, x, y, piecesGroup);
		queenOffset = Config.QUEEN_STROKE/2;
		circle.setStrokeWidth(Config.QUEEN_STROKE);
		circle.setTranslateX(queenOffset);
		circle.setTranslateY(queenOffset);
		
	}

}
