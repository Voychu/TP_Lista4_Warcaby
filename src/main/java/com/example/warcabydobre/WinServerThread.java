package com.example.warcabydobre;

import java.io.*;
import java.net.*;


public class WinServerThread {

    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(4444)) {

            System.out.println("Server is listening on port 4444");

            while (true) {
                Socket firstClient = serverSocket.accept();
                System.out.println("First client connected");
                System.out.println("Waiting for the second player");

                Socket secondClient = serverSocket.accept();
                System.out.println("Second client connected");

                Game g = new Game(firstClient, secondClient);
                Thread gTh = new Thread(g);
                gTh.start();

                // TODO: Musi byc dokldnie dwoch klientow

            }

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}


