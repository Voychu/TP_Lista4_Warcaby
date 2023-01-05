package com.example.warcabydobre.view;

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
    public GraphicalPiece getGraphicalPiece(){
        return graphicalPiece;
    }
    public void setGraphicalPiece(GraphicalPiece graphicalPiece){
        this.graphicalPiece = graphicalPiece;
    }

}
