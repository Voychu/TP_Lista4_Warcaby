package com.example.warcabydobre;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    public static void main (String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        GridPane root = new GridPane();
        Scene scene = new Scene(root, 160, 160);

        for(int k = 0; k<8; k++) {
            Rectangle rectangle = new Rectangle();
            rectangle.setFill(Color.BLACK);
            rectangle.setHeight(20);
            rectangle.setWidth(20);
            root.add(rectangle, k, k);
        }
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        }


}