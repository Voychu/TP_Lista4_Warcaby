package com.example.warcabydobre.view;

import javafx.scene.paint.Color;

/**
 * The concrete implementation of Square class.
 * It is graphical representation
 * of black fields in the game's board.
 * @author Wojciech Bajurny
 *
 */
public class BlackSquare extends Square{

	
	/**
	 * The constructor of BlackSquare
	 * @param width the width of the square
	 * @param height the height of the square
	 */
    public BlackSquare(double width, double height)
    {
        super(width, height);
        this.setFill(Color.BLACK);
    }

    

}
