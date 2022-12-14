package com.example.warcabydobre;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

public class CheckersGame extends Application{
	
    
    private static final int GAP_SIZE = 1;
    private static final int RECT_SIZE_X = 30;
    private static final int RECT_SIZE_Y = 30;
    private static int MARGIN_X = 30;
    private static int MARGIN_Y = 30;
    private Rectangle[][] rectList;
    private Stage choosingGameStage;
    
    
    
    
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

		//zamysl
		EventHandler<ActionEvent> eventHandler_classicalcheckers = new EventHandler<ActionEvent>()
			{
			public void handle(ActionEvent event)
				{
				choosingGameStage.setScene(ClassicCheckersBoard.CCscene);
				ClassicCheckersBoard ccb = new ClassicCheckersBoard();
				}
			};
    }
    
    
    
    
    
	
	
	/*GridPane grid = new GridPane();
    grid.setHgap(GAP_SIZE);
    grid.setVgap(GAP_SIZE);
    grid.setPadding(new Insets(0, m, 0, n));

    int width = m*RECT_SIZE_X + (m-1)*GAP_SIZE + MARGIN_X;
    int height = n*RECT_SIZE_Y + (n-1)*GAP_SIZE + MARGIN_Y;
    Scene scene = new Scene(grid, width, height);
    primaryStage.setScene(scene);
    
    for(int i=0;i<n;i++){
        for(int j=0;j<m;j++){
            rectList[j][i] = new Rectangle(RECT_SIZE_X,RECT_SIZE_Y);
            //rectList[j][i].setFill(c);
            grid.add(rectList[j][i], j, i);
            
            
        }
    }
    //Rectangle rect = new Rectangle(RECT_SIZE_X, RECT_SIZE_Y);
    //grid.add(rect,0,0);
    
    
    
    primaryStage.sizeToScene();
    primaryStage.setTitle(PROGRAM_TITLE_TXT);
    primaryStage.show();*/
	
	
    @Override
	public void start(Stage arg0) throws Exception {
		initchoosingGameStage();
		
	}
    
    public static void main(String[] args){
        
        launch(args);
       
        
        
    }







	
    
    

}
