package com.example.warcabydobre.view;

import com.example.warcabydobre.utils.Config;

import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * The graphical representation of a piece.
 * @author Wojciech Bajurny
 */
public class GraphicalPiece extends StackPane {
	
	private PieceColor color;
	
	private Group piecesGroup;
	

	private double mouseX, mouseY;
	private double oldX, oldY;
	
	protected Circle circle;

	private double offset = (Config.SQUARE_CLASSIC_WIDTH - 2*Config.PIECE_RADIUS - Config.PIECE_STROKE)/2;

	/**
	 * Constructor of GraphicalPiece, the visual representation of the PieceObject which the player can interact with.
	 * @param color uses PieceColor enum, black or white.
	 * @param x the x coordinate of the Piece.
	 * @param y the y coordinate of the Piece.
	 * @param piecesGroup which visually updates the players if the Pieces are still in the game or if they have been captured.
	 */
	public GraphicalPiece(PieceColor color, int x, int y, Group piecesGroup) {

		move(x, y);

		this.color = color;
		this.piecesGroup = piecesGroup;
		

		circle = new Circle();
		


		if(color==PieceColor.WHITE){
			circle.setFill(Color.WHITE);
			circle.setStroke(Color.RED);
		}
			
		else if(color==PieceColor.BLACK) {
			circle.setFill(Color.RED);
			circle.setStroke(Color.WHITE);
		}
		
		circle.setStrokeWidth(Config.PIECE_STROKE);
			

		circle.setRadius(Config.PIECE_RADIUS);
		circle.setTranslateX(offset);
		circle.setTranslateY(offset);
		getChildren().add(circle);
		setOnMousePressed(e -> {
			mouseX = e.getSceneX();
			mouseY = e.getSceneY();
		});

		setOnMouseDragged(e -> {
			relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY);
		});
	}

	/**
	 * Gets the color of the piece.
	 * @return the color enum.
	 */
	public PieceColor getColor() {
		return color;
	}

	/**
	 * Gets the X coordinate before moving the piece.
	 * @return the oldX
	 */
	public double getOldX() {
		return oldX;
	}

	/**
	 * Gets the Y coordinate before moving the piece.
	 * @return the oldY
	 */
	public double getOldY() {
		return oldY;
	}


	/**
	 * @return the piecesGroup
	 */
	public Group getPiecesGroup() {
		return piecesGroup;
	}

	/**
	 * Sets the color enum for the piece.
	 * @param color
	 */
	public void setColor(PieceColor color) {
		this.color = color;
	}


	/**
	 *
	 * @param x
	 * @param y
	 */
	public void move(int x, int y) {
		oldX = x * Config.SQUARE_CLASSIC_WIDTH;
		oldY = y * Config.SQUARE_CLASSIC_HEIGHT;
		relocate(oldX, oldY);
	}

	/**
	 * Removes the graphic piece from the group - it is no longer visible and can not be interacted with.
	 */
	public void delete() {
		piecesGroup.getChildren().remove(this);
		
	}

	/**
	 * Returns to the starting position of the piece. Used when the move is not possible.
	 */
	public void abortMove() {
		relocate(oldX, oldY);
	}
	
	

}
