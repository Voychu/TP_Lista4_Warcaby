package com.example.warcabydobre.view;

import com.example.warcabydobre.Config;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class GraphicalPiece extends StackPane {
	
	private PieceColor color;
	

	private double mouseX, mouseY;
	private double oldX, oldY;

	private double offset = (Config.SQUARE_CLASSIC_WIDTH - 2*Config.PIECE_RADIUS)/2;
	
	public GraphicalPiece(PieceColor color, int x, int y) {

		move(x, y);

		this.color = color;
		

		Circle circle = new Circle();

		if(color==PieceColor.WHITE)
			circle.setFill(Color.WHITE);
		else if(color==PieceColor.BLACK)
			circle.setFill(Color.RED);

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

	public PieceColor getColor() {
		return color;
	}

	public double getOldX() {
		return oldX;
	}

	public double getOldY() {
		return oldY;
	}


	public void setColor(PieceColor color) {
		this.color = color;
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
