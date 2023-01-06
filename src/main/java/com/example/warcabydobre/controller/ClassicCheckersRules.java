package com.example.warcabydobre.controller;

import com.example.warcabydobre.model.InvalidMoveException;
import com.example.warcabydobre.model.ModelMove;
import com.example.warcabydobre.model.MovementTypes;
import com.example.warcabydobre.model.PieceObject;


public class ClassicCheckersRules implements GameRules{

	 private PieceObject[][] piecesArray; 
	 
	 
	private boolean isOccupied(int x, int y) {
		return piecesArray[x][y] != null;
	}
	 
	public ClassicCheckersRules(PieceObject[][] piecesArray) {
		this.piecesArray = piecesArray;
		//TODO: referencja do modelu zamiast piecesArray
	}
	
	@Override
	public boolean isMoveValid() {
		// TODO Auto-generated method stub
		return false;
	}

	//TODO: sprawdzic waznosc ruchu. Jesli nie, to zwrocic NONE
	//TODO: Jesli dobry, to zwrocic nowe wspolrzedne intowe oraz fakt, czy jest bicie i czy zmienia sie w damke
	@Override
	public ModelMove tryMove(int oldX, int oldY, int newX, int newY) throws InvalidMoveException{
		//TODO: metody publiczna w modelu, czy pole zajete
		if(isOccupied(newX, newY) || (newX + newY) % 2 == 0)
        {
        return new ModelMove(MovementTypes.NONE);
        }
    //int xp = toBoardCoordinates(graphicalPiece.getOldX());//TODO w kontrolerze
    //int yp = toBoardCoordinates(graphicalPiece.getOldY());//TODO w kontrolerze
    //System.out.println(xp + ", " + yp);
	
	PieceObject pieceObject = piecesArray[oldX][oldY];
	if(pieceObject == null) {
		throw new InvalidMoveException();
	}
    if(Math.abs(newX - oldX) == 1 && newY - oldY == pieceObject.getMovementDirection())
        return new ModelMove(MovementTypes.FORWARD);
    else if (Math.abs(newX - oldX) == 2 && newY - oldY == pieceObject.getMovementDirection()* 2)
        {
            int x1 = oldX + (newX - oldX)/2;
            int y1 = oldY + (newY - oldY)/2;
            
            PieceObject secondPiece = piecesArray[x1][y1];

            if (isOccupied(x1,y1) && secondPiece.getColor() != pieceObject.getColor())
                {
                return new ModelMove(MovementTypes.CAPTURE_FORWARD, secondPiece);
                }
        }
    return new ModelMove(MovementTypes.NONE);
	}
	
	
	

}
