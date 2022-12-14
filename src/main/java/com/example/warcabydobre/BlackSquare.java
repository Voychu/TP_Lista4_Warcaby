package com.example.warcabydobre;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BlackSquare extends Square{

    //na tych polach gramy//
    BlackSquare(){
        rectangle = new Rectangle();
        rectangle.setFill(Color.BLACK);
        rectangle.setWidth(80);
        rectangle.setHeight(80);
    }

}
