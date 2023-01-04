package com.example.warcabydobre;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Optional;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

public class CheckersGame extends Application implements Runnable{
	
    
    private static int MARGIN_X = 30;
    private static int MARGIN_Y = 30;
    //private Stage choosingGameStage;
    private Stage boardStage;
    private static final int numCols = Config.CLASSICAL_CHECKERS_BOARD_WIDTH;
    private static final int numRows = Config.CLASSICAL_CHECKERS_BOARD_HEIGHT;
    private static final int numRowsWithPieces = Config.CLASSICAL_CHECKERS_ROWS_WITH_PIECES;
    private Label textLabel;
    private TextField sendingField;
    Label messageLabel;
    private Button confirmButton;
    private Alert endingGameAlert;
    private int player;
    
    
    public final static int PLAYER1 = 1;
    public final static int PLAYER2 = 2;

    public final static int ACTIVE = 0;
    public final static int NONACTIVE = 1;
    private  static int actualPlayer = PLAYER1;

    private static int showing = ACTIVE;
    
    Socket socket = null;
    PrintWriter out = null;
    BufferedReader in = null;
    private GameController controller;
    private BoardModel boardModel;
    /*private Square[][] squaresArray;*/
    private LinkedList<WhitePiece> whitePiecesList;
    private LinkedList<BlackPiece> blackPiecesList;
    private Group squaresGroup;
    private Group piecesGroup;
    

   
    
    
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
    
    
   
    
    /*private void initchoosingGameStage() {
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
				//receiveInitFromServer();
				//startThread();
				
				initBoardStage();
				
				
				
				
			}
		};
		classicalCheckersButton.setOnAction(eventHandler_classicalcheckers);
    }*/
    
    
    private void initBoardStage(){
    	
    	
		
    	
    	boardStage = new Stage();
    	boardStage.setTitle(Config.APPLICATION_TITLE_TXT);
    	
        
        
        boardStage.setResizable(true);
        // ClassicCheckersBoard ccb = new ClassicCheckersBoard(in, out);
        //Scene windowScene = ccb.getBoardScene();
        BorderPane borderPane = new BorderPane();
        squaresGroup = new Group();
    	piecesGroup = new Group();
    	Pane pane = new Pane();
    	pane.setPrefSize(Config.CLASSICAL_CHECKERS_BOARD_WIDTH*Config.SQUARE_CLASSIC_WIDTH,Config.CLASSICAL_CHECKERS_BOARD_HEIGHT*Config.SQUARE_CLASSIC_HEIGHT);
    	pane.getChildren().addAll(squaresGroup, piecesGroup);
    	
    	/*squaresArray = new Square[numCols][numRows];
    	whitePiecesList = new LinkedList<>();
    	blackPiecesList = new LinkedList<>();*/
    	
    	


    	double helpw = Config.SQUARE_CLASSIC_WIDTH;
        double helph = Config.SQUARE_CLASSIC_HEIGHT;
        //boolean white = true;
        //int a = 0;
        //int b = 0;
        //y = j * helph
        //x = i * helw
        for (int j = 0; j < numRows; j++) {
            for (int i = 0; i < numCols; i++) {
            	double x = i * helpw;
                double y = j * helph;
                if((i+j)%2 ==0) {
                    WhiteSquare wSquare = 
                    		new WhiteSquare(Config.SQUARE_CLASSIC_WIDTH, Config.SQUARE_CLASSIC_HEIGHT);
                    //squaresArray[a][b] = wSquare;
                    squaresGroup.getChildren().addAll(wSquare);
                    wSquare.relocate(x,y);
                    //a+=1;
                }
                else{
                	BlackSquare bSquare = 
                			new BlackSquare(Config.SQUARE_CLASSIC_WIDTH, Config.SQUARE_CLASSIC_HEIGHT);
                	//squaresArray[a][b] = bSquare;
                    squaresGroup.getChildren().addAll(bSquare);
                    bSquare.relocate(x,y);
                    //a+=1;
                }
                //white=!white;
            }
            //white=!white;
            //a=0;
            //b+=1;
        }
       //y = j * helph
       //x = i * helw
        double offset = (Config.SQUARE_CLASSIC_WIDTH - 2*Config.PIECE_RADIUS)/2;
        for (int j=0; j<numRowsWithPieces; j++){
            for(int i = 0; i< numCols; i++) {
                if((i+j)%2 ==1){
                   WhitePiece wPiece = new WhitePiece(Config.PIECE_RADIUS);
                   //whitePiecesList.add(wPiece);
                   piecesGroup.getChildren().addAll(wPiece);
                  
                   double x = i * helpw + offset;
                   double y = j * helph + offset;
                   wPiece.relocate(x,y);
                   
                    
                }
            }
        }
        for (int j=5; j<5+numRowsWithPieces; j++){
            for(int i = 0; i< numCols; i++) {
                if((i+j)%2 ==1){
                    BlackPiece bPiece = new BlackPiece(Config.PIECE_RADIUS);
                    //blackPiecesList.add(bPiece);
                    piecesGroup.getChildren().addAll(bPiece);
                    double x = i * helpw + offset;
                    double y = j * helph + offset;
                    bPiece.relocate(x,y);
                   
                }
            }
        }
        
        
        
        
        borderPane.setCenter(pane);
        
        
    	
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
        
        
    	Scene boardScene = new Scene(borderPane, Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
    	boardStage.setScene(boardScene);
    	boardStage.show();
    	//choosingGameStage.close();
    	
    	
    	EventHandler<ActionEvent> sendingMessageHandler = new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent event)
			{
				sendMessage();
				
			}
		};
		confirmButton.setOnAction(sendingMessageHandler);
        
		
		boardStage.setOnCloseRequest(event -> out.println("bye"));
    	
    	
    	
    	
    	
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
        //messageLabel.setText("OppositeTurn");
        Platform.runLater(()->messageLabel.setText("OppositeTurn"));
        //send.setEnabled(false);
        //sendingField.setText("");
        Platform.runLater(()->sendingField.setText(""));
        
        Platform.runLater(()->textLabel.setText(""));
        
        
        Platform.runLater(()->confirmButton.setDisable(true));
        
        if(message.equals("bye")) {
        	try {
				
				Platform.runLater(()->System.exit(0));
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
        //input.requestFocus();
        showing = ACTIVE;
        actualPlayer = player;
    }
    
    
    public void receiveMessage() {
    	 try {
             // Odbieranie z serwera
             String str = in.readLine();
             
             if(str.equals("bye")) {
            	 /*endingGameAlert.setTitle("Uwaga");
            	 endingGameAlert.setHeaderText("Rozlaczono z serwerem.");
            	 endingGameAlert.setContentText("Czy chcesz zamknac okno?");
            	 endingGameAlert.initModality(Modality.APPLICATION_MODAL);
                 
                 Optional<ButtonType> clickedButton  = endingGameAlert.showAndWait();
                 if(clickedButton.get() == ButtonType.OK){
                     System.exit(0);
                 }*/
            	//Platform.runLater(()->System.exit(0));
            	System.exit(0);
 				socket.close();
             }
             //textLabel.setText(str);
             Platform.runLater(()->textLabel.setText(str));
             //messageLabel.setText("My turn");
             Platform.runLater(()->messageLabel.setText("My turn"));
             //send.setEnabled(true);
             //sendingField.setText("");
             Platform.runLater(()-> sendingField.setText(""));
             
             Platform.runLater(()->confirmButton.setDisable(false));
             //input.requestFocus();
         }
         catch (IOException e) {
             System.out.println("Read failed"); System.exit(1);}
    }
    
    
    
    private void receiveInitFromServer() {
        try {
        	System.out.println("Oczekiwanie...");
            player = Integer.parseInt(in.readLine());
            System.out.println("Polaczono");
            if (player== PLAYER1) {
            	//messageLabel.setText("My Turn");
            	Platform.runLater(()->messageLabel.setText("My Turn"));
            	
            } else {
            	//messageLabel.setText("Opposite turn");
            	Platform.runLater(()->messageLabel.setText("Opposite turn"));
            	Platform.runLater(()->confirmButton.setDisable(true));
                //sendingField.setEnabled(false);
            }
        } catch (IOException e) {
            System.out.println("Read failed");
            System.exit(1);
        }
    }

    public void getSendingText() {
    }
    
    
    private void startThread() {
        Thread gTh = new Thread(this);
        gTh.start();
    }

    @Override
    public void run() {
       /*if (player==PLAYER1) {
            f1();
        }
        else{
            f2();
        }*/
        // Mozna zrobic w jednej metodzie. Zostawiam
        // dla potrzeb prezentacji
        f(player);
    }
    
    
 // Jedna metoda dla kazdego Playera
    void f(int iPlayer){
        while(true) {
            synchronized (this) {
                if (actualPlayer== iPlayer) {
                    try {
                        wait(10);
                    } catch (InterruptedException e) {
                    }
                }
                if (showing ==ACTIVE){
                	receiveMessage();
                    showing =NONACTIVE;
                }
                notifyAll();
            }
        }
    }

    
    
    
    private void initMVC() {
        this.boardModel = new BoardModel(player);
        System.out.println(player);
        //this.controller = new GameController(this.boardModel);
    }
	
	
    @Override
	public void start(Stage arg0) throws Exception {
    	//tworzenie kontrolera
		initBoardStage();
		listenSocket();
		
		receiveInitFromServer();
		initMVC();
		startThread();
		
		
		
		
		
		
	}
    
    public static void main(String[] args){
    	
        launch(args);
        
        
        
       
        
        
    }







	
    
    

}
