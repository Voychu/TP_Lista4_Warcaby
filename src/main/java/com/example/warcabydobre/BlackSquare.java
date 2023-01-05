package com.example.warcabydobre;

import javafx.scene.paint.Color;

//s
public class BlackSquare extends Square{

	//na tych polach gramy//

    public BlackSquare(double width, double height)
    {
        super(width, height);
        this.setFill(Color.BLACK);
    }

    private GraphicalPiece graphicalPiece;

    public boolean isOccupied(){
        return graphicalPiece!= null;
    }
    public GraphicalPiece getPieceObject(){
        return graphicalPiece;
    }
    public void setPieceObject(GraphicalPiece graphicalPiece){
        this.graphicalPiece = graphicalPiece;
    }

}
