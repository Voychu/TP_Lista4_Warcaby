package com.example.warcabydobre;

import javafx.scene.paint.Color;

public class BlackPiece extends Piece{

    public BlackPiece(double radius)
    {
        super(radius);
        this.setFill(Color.RED);
        setOnMousePressed(e -> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });

        setOnMouseDragged(e -> {
            relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY);
        });
    }
}
