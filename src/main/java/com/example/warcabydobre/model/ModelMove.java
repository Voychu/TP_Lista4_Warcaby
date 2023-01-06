package com.example.warcabydobre.model;
import com.example.warcabydobre.view.GraphicalPiece;

public class ModelMove {
    private MovementTypes type;

    public MovementTypes getMovementType(){
        return type;
    }

    private PieceObject pieceObject;

    public PieceObject getPieceObject(){
        return pieceObject;
    }
    public ModelMove(MovementTypes type, PieceObject pieceObject){
        this.type = type;
        this.pieceObject = pieceObject;
    }
    public ModelMove(MovementTypes type){
        this(type,null);
    }
}
