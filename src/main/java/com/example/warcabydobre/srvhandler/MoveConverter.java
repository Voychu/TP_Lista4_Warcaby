package com.example.warcabydobre.srvhandler;

import com.example.warcabydobre.model.ModelMove;
import com.example.warcabydobre.model.MovementTypes;

public class MoveConverter {

	
	//TODO: move xp yp xk yk split po spacji
	public static ModelMove convertToMove(String message) {
		return null;
	}
	
	public static String convertMoveToString(ModelMove move) {
		MovementTypes type = move.getMovementType();
		if(type != MovementTypes.NONE) {
			return "move " + move.getOldX() + " " + move.getOldY() +" "+
					move.getNewX() + " " + move.getNewY();
		}
		else return null;
		
	}

}
