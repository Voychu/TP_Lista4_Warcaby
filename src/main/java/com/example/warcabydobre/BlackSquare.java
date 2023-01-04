package com.example.warcabydobre;

import javafx.scene.paint.Color;


public class BlackSquare extends Square{

	//na tych polach gramy//

    public BlackSquare(double width, double height)
    {
        super(width, height);
        this.setFill(Color.BLACK);
    }

    private PieceObject pieceObject;

    public boolean isOccupied(){
        return pieceObject!= null;
    }
    public PieceObject getPieceObject(){
        return pieceObject;
    }
    public void setPieceObject(PieceObject pieceObject){
        this.pieceObject = pieceObject;
    }

}
