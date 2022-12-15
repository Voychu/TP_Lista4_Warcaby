package com.example.warcabydobre;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

public class CheckersGame extends Application{
	
    
    private static int MARGIN_X = 30;
    private static int MARGIN_Y = 30;
    private Stage choosingGameStage;
    private Stage boardStage;
    
    
    
    
    private void initchoosingGameStage() {
    	choosingGameStage = new Stage();
    	
    	choosingGameStage.setTitle(Config.APPLICATION_TITLE_TXT);
    	VBox vBox = new VBox();
    	Label classicalCheckersLabel =
    			new Label(Config.CLASSICAL_CHECKERS_LABEL_TXT);
    	Label secondCheckersLabel =
    			new Label(Config.SECOND_CHECKERS_LABEL_TXT);
    	HBox labelsHBox = new HBox(classicalCheckersLabel, secondCheckersLabel);
    	labelsHBox.setAlignment(Pos.CENTER);
    	labelsHBox.setSpacing(Config.GAP);
    	Button classicalCheckersButton = 
    			new Button(Config.CLASSICAL_CHECKERS_BUTTON_TXT);
    	Button secondCheckersButton = 
    			new Button(Config.SECOND_CHECKERS_BUTTON_TXT);
    	HBox buttonsHBox = new HBox(classicalCheckersButton, secondCheckersButton);
    	buttonsHBox.setAlignment(Pos.CENTER);
    	buttonsHBox.setSpacing(Config.GAP);
    	vBox.getChildren().addAll(labelsHBox, buttonsHBox);
    	vBox.setAlignment(Pos.CENTER);
    	vBox.setSpacing(Config.GAP);
    	choosingGameStage.setResizable(true);
    	Scene windowScene = new Scene(vBox, Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
    	choosingGameStage.setScene(windowScene);
    	choosingGameStage.show();


		EventHandler<ActionEvent> eventHandler_classicalcheckers = new EventHandler<ActionEvent>()
			{
			public void handle(ActionEvent event)
				{
				initBoardStage();
				}
			};
		classicalCheckersButton.setOnAction(eventHandler_classicalcheckers);
    }
    
    
    private void initBoardStage(){
    	boardStage = new Stage();
    	boardStage.setTitle(Config.APPLICATION_TITLE_TXT);
    	
    	final int numCols = Config.CLASSICAL_CHECKERS_BOARD_WIDTH;
        final int numRows = Config.CLASSICAL_CHECKERS_BOARD_HEIGHT;
    	
    	BorderPane borderPane = new BorderPane();
    	GridPane gPane = new GridPane();
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
        
        
        boardStage.setResizable(true);
    	Scene windowScene = new Scene(borderPane, Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
    	boardStage.setScene(windowScene);
    	boardStage.show();
        
    	
    	
    	
    	
    	
    }
    
    
    
    
    
	
	
    @Override
	public void start(Stage arg0) throws Exception {
		initchoosingGameStage();
		
	}
    
    public static void main(String[] args){
        
        launch(args);
       
        
        
    }







	
    
    

}
