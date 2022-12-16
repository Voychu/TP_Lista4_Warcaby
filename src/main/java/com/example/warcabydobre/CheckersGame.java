package com.example.warcabydobre;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

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
    
    Socket socket = null;
    PrintWriter out = null;
    BufferedReader in = null;

   
    
    
    /*
    Połaczenie z socketem
     */
    public void listenSocket() {
        try {
            socket = new Socket("localhost", 4444);
            // Inicjalizacja wysylania do serwera
            out = new PrintWriter(socket.getOutputStream(), true);
            // Inicjalizacja odbierania z serwera
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (UnknownHostException e) {
            System.out.println("Unknown host: localhost");
            System.exit(1);
        } catch (IOException e) {
            System.out.println("No I/O");
            System.exit(1);
        }
    }
    
    
   
    
    private void initchoosingGameStage() {
    	choosingGameStage = new Stage();
    	
    	choosingGameStage.setTitle(Config.APPLICATION_TITLE_TXT);
    	VBox vBox = new VBox();
    	Label classicalCheckersLabel =
    			new Label(Config.CLASSICAL_CHECKERS_LABEL_TXT);
    	double size1 = classicalCheckersLabel.getMaxWidth();
    	Label secondCheckersLabel =
    			new Label(Config.SECOND_CHECKERS_LABEL_TXT);
    	double size2 = secondCheckersLabel.getPrefWidth();
    	HBox labelsHBox = new HBox(classicalCheckersLabel, secondCheckersLabel);
    	labelsHBox.setAlignment(Pos.CENTER);
    	labelsHBox.setSpacing(Config.GAP);
    	Button classicalCheckersButton = 
    			new Button(Config.CLASSICAL_CHECKERS_BUTTON_TXT);
    	classicalCheckersButton.setPrefWidth(size1);
    	Button secondCheckersButton = 
    			new Button(Config.SECOND_CHECKERS_BUTTON_TXT);
    	secondCheckersButton.setPrefWidth(size2);
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
				listenSocket();
				initBoardStage();
				
			}
		};
		classicalCheckersButton.setOnAction(eventHandler_classicalcheckers);
    }
    
    
    private void initBoardStage(){
    	boardStage = new Stage();
    	boardStage.setTitle(Config.APPLICATION_TITLE_TXT);
    	
        
        
        boardStage.setResizable(true);
        ClassicCheckersBoard ccb = new ClassicCheckersBoard(in, out);
        Scene windowScene = ccb.getBoardScene();
    	boardStage.setScene(windowScene);
    	boardStage.show();
    	choosingGameStage.close();
        
    	
    	
    	
    	
    	
    }
    
    
    
    
    
	
	
    @Override
	public void start(Stage arg0) throws Exception {
		initchoosingGameStage();
		
	}
    
    public static void main(String[] args){
        
        launch(args);
       
        
        
    }







	
    
    

}
