package com.example.warcabydobre;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class ClassicCheckersBoard {

    
	private Scene boardScene;
	private BorderPane borderPane;
	private GridPane gPane;
    final int numCols = Config.CLASSICAL_CHECKERS_BOARD_WIDTH;
    final int numRows = Config.CLASSICAL_CHECKERS_BOARD_HEIGHT;


    ClassicCheckersBoard() {

    	
    	
    	borderPane = new BorderPane();
    	gPane = new GridPane();
    	gPane.setVgap(Config.BOARD_GAP);
    	gPane.setHgap(Config.BOARD_GAP);
    	
    	
    	
        for (int y = 0; y < numRows; y++) {
            for (int x = 0; x < numCols; x++) {
                if((x+y)%2 ==0) {
                    WhiteSquare wSquare = 
                    		new WhiteSquare(Config.SQUARE_CLASSIC_WIDTH, Config.SQUARE_CLASSIC_HEIGHT);
                    gPane.add(wSquare,y,x);
                }
                else{
                	BlackSquare bSquare = 
                			new BlackSquare(Config.SQUARE_CLASSIC_WIDTH, Config.SQUARE_CLASSIC_HEIGHT);
                    gPane.add(bSquare,y,x);
                }
            }
        }
        borderPane.setCenter(gPane);
        
        VBox vBox = new VBox();
        Label textLabel = new Label("Gra: test");
        HBox labelHBox = new HBox(textLabel);
        labelHBox.setAlignment(Pos.CENTER);
        labelHBox.setSpacing(Config.GAP);
        vBox.getChildren().addAll(labelHBox);
        vBox.setAlignment(Pos.CENTER);
    	vBox.setSpacing(Config.GAP);
        
        borderPane.setLeft(vBox);
        
        
    	boardScene = new Scene(borderPane, Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
    	
    }
    
    public Scene getBoardScene() {
		return boardScene;
    	
    }
}
