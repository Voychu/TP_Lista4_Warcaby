package com.example.warcabydobre.srv;

import java.io.*;
import java.net.*;


public class WinServerThread {

    public static void main(String[] args) {

        Socket[] players = new Socket[2];
        try (ServerSocket serverSocket = new ServerSocket(4444)) {

            System.out.println("Server is listening on port 4444");

            /**while (true) {
                Socket firstClient = serverSocket.accept();
                System.out.println("First client connected");
                System.out.println("Waiting for the second player");


                Socket secondClient = serverSocket.accept();
                System.out.println("Second client connected");


                Game g = new Game(firstClient, secondClient);
                Thread gTh = new Thread(g);
                gTh.start();


            }*/

            for(int i=0; i<2; i++)
            {
                players[i] = serverSocket.accept();
                System.out.println("Player connected");
            }
            System.out.println("Two players connected");
            //GameData gameData = new GameData();
            Game g = new Game(players[0],players[1]);
            Thread gTh = new Thread(g);
            gTh.start();

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}


