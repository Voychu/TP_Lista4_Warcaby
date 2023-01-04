package com.example.warcabydobre;

import javafx.scene.shape.Circle;

public abstract class Piece extends Circle{
    public double mouseX, mouseY;
    public double oldX, oldY;
    public Piece(double radius){super(radius);}
}
