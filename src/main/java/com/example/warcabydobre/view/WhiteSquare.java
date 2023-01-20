package com.example.warcabydobre.view;
import javafx.scene.paint.Color;



/**
 * The concrete implementation of Square class.
 * It is graphical representation
 * of white fields in the game's board.
 * @author Wojciech Bajurny
 *
 */
public class WhiteSquare extends Square{


	/**
	 * The constructor of WhiteSquare
	 * @param width the width of the square
	 * @param height the height of the square
	 */
    public WhiteSquare(double width, double height)
    {
        super(width, height);
        this.setFill(Color.WHITE);
    }
    

}
