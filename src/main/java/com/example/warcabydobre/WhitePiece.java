/*package com.example.warcabydobre;

import javafx.scene.paint.Color;

public class WhitePiece extends Piece{

    public WhitePiece(double radius)
    {
        super(radius);
        this.setFill(Color.WHITE);
        setOnMousePressed(e -> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });

        setOnMouseDragged(e -> {
            relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY);
        });
    }
}*/
