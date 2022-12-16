package com.example.warcabydobre;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    Label textLabel;
    TextField sendingField;
    Label messageLabel;
    PrintWriter out = null;
    BufferedReader in = null;
    private Button confirmButton;
    private int player;

    public final static int PLAYER1 = 1;
    public final static int PLAYER2 = 2;

    public final static int ACTIVE = 0;
    public final static int NONACTIVE = 1;
    private  static int actualPlayer = PLAYER1;

    private static int showing = ACTIVE;


    ClassicCheckersBoard(BufferedReader in, PrintWriter out){

    	this.in = in;
    	this.out = out;
    	
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
        textLabel = new Label("Gra: test");
        HBox labelHBox = new HBox(textLabel);
        labelHBox.setAlignment(Pos.CENTER);
        labelHBox.setSpacing(Config.GAP);
        vBox.getChildren().addAll(labelHBox);
        vBox.setAlignment(Pos.CENTER);
    	vBox.setSpacing(Config.GAP);
        
        borderPane.setRight(vBox);
        
        
        VBox lVBox = new VBox();
        sendingField = new TextField();
        HBox textFieldHBox = new HBox(sendingField);
        textFieldHBox.setAlignment(Pos.CENTER);
        textFieldHBox.setSpacing(Config.GAP);
        confirmButton = new Button("wyslij");
        HBox buttonHBox = new HBox(confirmButton);
        buttonHBox.setAlignment(Pos.CENTER);
        buttonHBox.setSpacing(Config.GAP);
        messageLabel = new Label("Status:");
        HBox messageLabelHBox = new HBox(messageLabel);
        messageLabelHBox.setAlignment(Pos.CENTER);
        messageLabelHBox.setSpacing(Config.GAP);
        lVBox.getChildren().addAll(textFieldHBox, buttonHBox, messageLabelHBox);
        lVBox.setAlignment(Pos.CENTER);
    	lVBox.setSpacing(Config.GAP);
        
        borderPane.setLeft(lVBox);
        
        
    	boardScene = new Scene(borderPane, Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
    	
    }
    
    public Scene getBoardScene() {
		return boardScene;
    	
    }
    
    public void setLabelText(String text) {
    	textLabel.setText(text);
    }
    
    /*public String sendMessage() {
    	return sendingField.getText();
    }*/
    
    public void sendMessage() {
    	String message = sendingField.getText();
    	out.println(message);
        messageLabel.setText("OppositeTurn");
        //send.setEnabled(false);
        sendingField.setText("");
        //input.requestFocus();
        showing = ACTIVE;
        actualPlayer = player;
    }
    
    
    public void receiveMessage() {
    	 try {
             // Odbieranie z serwera
             String str = in.readLine();
             textLabel.setText(str);
             messageLabel.setText("My turn");
             //send.setEnabled(true);
             sendingField.setText("");
             //input.requestFocus();
         }
         catch (IOException e) {
             System.out.println("Read failed"); System.exit(1);}
    }
    
    
    
    private void receiveInitFromServer() {
        try {
            player = Integer.parseInt(in.readLine());
            if (player== PLAYER1) {
                messageLabel.setText("My Turn");
            } else {
            	messageLabel.setText("Opposite turn");
                //sendingField.setEnabled(false);
            }
        } catch (IOException e) {
            System.out.println("Read failed");
            System.exit(1);
        }
    }
    
}
