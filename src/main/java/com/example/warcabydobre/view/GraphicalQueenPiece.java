package com.example.warcabydobre.view;

import com.example.warcabydobre.utils.Config;

import javafx.scene.Group;

public class GraphicalQueenPiece extends GraphicalPiece {

	private double queenOffset;
	
	
	public GraphicalQueenPiece(PieceColor color, int x, int y, Group piecesGroup) {
		super(color, x, y, piecesGroup);
		queenOffset = Config.QUEEN_STROKE/2;
		circle.setStrokeWidth(Config.QUEEN_STROKE);
		circle.setTranslateX(queenOffset);
		circle.setTranslateY(queenOffset);
		
	}

}
