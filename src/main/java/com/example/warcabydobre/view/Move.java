package com.example.warcabydobre.view;

import com.example.warcabydobre.model.MovementTypes;

public class Move {
    private MovementTypes type;

    public MovementTypes getMovementType(){
        return type;
    }

    private GraphicalPiece graphicalPiece;

    public GraphicalPiece getGraphicalPiece(){
        return graphicalPiece;
    }
    public Move(MovementTypes type, GraphicalPiece graphicalPiece){
        this.type = type;
        this.graphicalPiece = graphicalPiece;
    }
    public Move(MovementTypes type){
        this(type,null);
    }
}
