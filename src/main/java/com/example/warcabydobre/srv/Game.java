package com.example.warcabydobre.srv;

import java.io.*;
import java.net.Socket;

import javafx.scene.control.Alert;

public class Game implements Runnable{

    private Socket firstPlayer;
    private Socket secondPlayer;
    
    


    private final static int FIRST=1;
    private final static int SECOND=2;
    private static int turn=FIRST;


    public Game(Socket firstPlayer, Socket secondPlayer){
        this.firstPlayer = firstPlayer;
        this.secondPlayer= secondPlayer;


    }
    @Override
    public void run() {

        try{
            //Inicjalizacja pobieranie od socketa dla player1
            InputStream inputF = firstPlayer.getInputStream();
            BufferedReader inF = new BufferedReader(new InputStreamReader(inputF));

            //Inicjalizacja pobieranie od socketa dla player2
            InputStream inputS = secondPlayer.getInputStream();
            BufferedReader inS = new BufferedReader(new InputStreamReader(inputS));

            //Inicjalizacja Wysylania do socketa dla player1
            OutputStream outputF = firstPlayer.getOutputStream();
            PrintWriter outF = new PrintWriter(outputF, true);

            //Inicjalizacja Wysylania do socketa dla player2
            OutputStream outputS = secondPlayer.getOutputStream();
            PrintWriter outS = new PrintWriter(outputS, true);

            outF.println("1");
            outS.println("2");

            String line="";
            do {
                if (turn==SECOND) {
                    // Odbieranie od socketa
                    line = inS.readLine();
                    if(line.equals("bye")) {
                    	//firstPlayer.close();
                    	//secondPlayer.close();
                    	outS.println("-> ("+" Rozlaczanie 2 gracza... "+")");
                    }
                    // Wypisywanie na serwerze
                    System.out.println(line);
                    // Wysylanie do socketa
                    outF.println("-> (" + line + ")");
                    turn=FIRST;
                }

                if (turn==FIRST) {
                    // Odbieranie od socketa
                    line = inF.readLine();
                    if(line.equals("bye")) {
                    	outF.println("-> ("+" Rozlaczanie 1 gracza... "+")");
                    	//firstPlayer.close();
                    	//secondPlayer.close();
                    }
                    // Wypisywanie na serwerze
                    System.out.println(line);
                    // Wysylanie do socketa
                    outS.println("-> (" + line + ")");
                    turn=SECOND;
                }
            } while (!line.equals("bye"));
            firstPlayer.close();
        	secondPlayer.close();

        } catch (IOException ex) {
            System.err.println("ex");
        }
    }

    private void sendMove(DataOutputStream out, String text) throws IOException {
        out.writeChars(text);

    }
}