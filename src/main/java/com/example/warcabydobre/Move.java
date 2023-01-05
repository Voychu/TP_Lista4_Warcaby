package com.example.warcabydobre;

public class Move {
    private MovementTypes type;

    public MovementTypes getMovementType(){
        return type;
    }

    private PieceObject pieceObject;

    public PieceObject getPieceObject(){
        return pieceObject;
    }
    public Move(MovementTypes type, PieceObject pieceObject){
        this.type = type;
        this.pieceObject = pieceObject;
    }
    public Move(MovementTypes type){
        this(type,null);
    }
}
