package com.example.warcabydobre;

import javafx.scene.paint.Color;


public class BlackSquare extends Square{

	//na tych polach nie gramy//

    public BlackSquare(double width, double height)
    {
        super(width, height);
        this.setFill(Color.BLACK);
    }

}
