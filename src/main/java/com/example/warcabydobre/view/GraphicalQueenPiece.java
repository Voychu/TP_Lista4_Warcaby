package com.example.warcabydobre.view;

import com.example.warcabydobre.utils.Config;

import javafx.scene.Group;

public class GraphicalQueenPiece extends GraphicalPiece {

	
	
	public GraphicalQueenPiece(PieceColor color, int x, int y, Group piecesGroup) {
		super(color, x, y, piecesGroup);
		offset = (Config.SQUARE_CLASSIC_WIDTH - 2*Config.PIECE_RADIUS - Config.QUEEN_STROKE)/2;
		circle.setStrokeWidth(Config.PIECE_STROKE);
	}

}
