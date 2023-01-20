package com.example.warcabydobre.srv;

import java.io.*;
import java.net.Socket;

import javafx.scene.control.Alert;


/**
 * Class of thread servicing
 * single game player.
 */
public class Game implements Runnable{

    /** The socket of first player. */
    private Socket firstPlayer;
    
    /** The socket of second player. */
    private Socket secondPlayer;
    
    


    /** The Constant refers to first player */
    private final static int FIRST=1;
    
    /** The Constant refers to second player */
    private final static int SECOND=2;
    
    /** Flag storing information whose is turn. */
    private static int turn=FIRST;


    /**
     * Constructor of Game class
     *
     * @param firstPlayer the socket of first player
     * @param secondPlayer the socket of second player
     */
    public Game(Socket firstPlayer, Socket secondPlayer){
        this.firstPlayer = firstPlayer;
        this.secondPlayer= secondPlayer;


    }
    
    /**
     * method responsible for running player's thread
     */
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
                    	outS.println("-> ("+" Rozlaczanie 2 gracza... "+")");
                    }
                    // Wypisywanie na serwerze
                    System.out.println(line);
                    // Wysylanie do socketa
                    outF.println(line);
                    turn=FIRST;
                }

                if (turn==FIRST) {
                    // Odbieranie od socketa
                    line = inF.readLine();
                    if(line.equals("bye")) {
                    	outF.println("-> ("+" Rozlaczanie 1 gracza... "+")");
                    }
                    // Wypisywanie na serwerze
                    System.out.println(line);
                    // Wysylanie do socketa
                    outS.println(line);
                    turn=SECOND;
                }
            } while (!line.equals("bye"));
            firstPlayer.close();
        	secondPlayer.close();

        } catch (IOException ex) {
            System.err.println("ex");
        }
    }

}