package com.example.warcabydobre;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class WhiteSquare extends Square{

    //na tych polach nie gramy
    WhiteSquare()
    {

        rectangle = new Rectangle();
        rectangle.setFill(Color.WHITE);
        rectangle.setWidth(30);
        rectangle.setHeight(30);
    }

}
