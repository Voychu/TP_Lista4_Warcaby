package com.example.warcabydobre.view;

import javafx.scene.shape.Rectangle;

public abstract class Square extends Rectangle{

    
	public Square(double width, double height) {
    	super(width, height);
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
