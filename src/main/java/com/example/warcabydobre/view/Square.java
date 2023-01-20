package com.example.warcabydobre.view;

import javafx.scene.shape.Rectangle;


/**
 * The class of graphical representation
 * of the fields in the game's board.
 * @author Wojciech Bajurny
 */
public abstract class Square extends Rectangle{

	/** The graphical piece that is on 
	 * this field. 
	**/
	private GraphicalPiece graphicalPiece;
	
	
	/**
	 * Constructor of Square.
	 *
	 * @param width the width of the square
	 * @param height the height of the square
	 */
	public Square(double width, double height) {
    	super(width, height);
    }
	
	

    /**
     * Checks if this field is
     * occupied by any graphicalPiece.
     *
     * @return true, if this field is occupied
     */
    public boolean isOccupied(){
        return graphicalPiece!= null;
    }
    
    /**
     * Gets the graphical piece.
     *
     * @return the graphical piece
     */
    public GraphicalPiece getGraphicalPiece(){
        return graphicalPiece;
    }
    
    /**
     * Sets the graphical piece.
     *
     * @param graphicalPiece the new graphical piece
     */
    public void setGraphicalPiece(GraphicalPiece graphicalPiece){
        this.graphicalPiece = graphicalPiece;
    }

}
