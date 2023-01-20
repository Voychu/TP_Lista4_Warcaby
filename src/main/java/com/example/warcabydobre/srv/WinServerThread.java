package com.example.warcabydobre.srv;

import java.io.*;
import java.net.*;


/**
 * Class of the server with which 
 * can connect 2 players.
 * @author Wojciech Bajurny
 */
public class WinServerThread {

    /**
     * Main method of WinServerThread class
     * servicing server activities
     *
     * @param args Parameters with which
     * program is called
     */
    public static void main(String[] args) {

        Socket[] players = new Socket[2];
        try (ServerSocket serverSocket = new ServerSocket(4444)) {

            System.out.println("Server is listening on port 4444");


            for(int i=0; i<2; i++)
            {
                players[i] = serverSocket.accept();
                System.out.println("Player connected");
            }
            System.out.println("Two players connected");
            Game g = new Game(players[0],players[1]);
            Thread gTh = new Thread(g);
            gTh.start();

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}


