package com.example.warcabydobre;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class PieceObject extends StackPane {
	
	private PieceColor color;
	//int xs;
	//int y;
	boolean isQueen;

	private double mouseX, mouseY;
	private double oldX, oldY;
	
	public PieceObject(PieceColor color, int x, int y) {

		move(x, y);

		this.color = color;
		//this.x = x;
		//this.y = y;
		this.isQueen = false;

		Circle circle = new Circle();

		if(color==PieceColor.WHITE)
			circle.setFill(Color.WHITE);
		else if(color==PieceColor.BLACK)
			circle.setFill(Color.RED);

		circle.setRadius(Config.PIECE_RADIUS);
		getChildren().add(circle);
		setOnMousePressed(e -> {
			mouseX = e.getSceneX();
			mouseY = e.getSceneY();
		});

		setOnMouseDragged(e -> {
			relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY);
		});
	}

	public PieceColor getColor() {
		return color;
	}

	/*public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}*/

	public boolean isQueen() {
		return isQueen;
	}

	public void setColor(PieceColor color) {
		this.color = color;
	}

	/*public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}*/

	public void setQueen(boolean isQueen) {
		this.isQueen = isQueen;
	}

	public void move(int x, int y) {
		oldX = x * Config.SQUARE_CLASSIC_WIDTH;
		oldY = y * Config.SQUARE_CLASSIC_HEIGHT;
		relocate(oldX, oldY);
	}
	public void abortMove() {
		relocate(oldX, oldY);
	}
	
	

}
