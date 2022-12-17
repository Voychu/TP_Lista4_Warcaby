package com.example.warcabydobre;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.WindowEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class WinClient implements Runnable{
	
	Socket socket = null;
	PrintWriter out = null;
    BufferedReader in = null;

    private int player;

    public final static int PLAYER1 = 1;
    public final static int PLAYER2 = 2;

    public final static int ACTIVE = 0;
    public final static int NONACTIVE = 1;
    private  static int actualPlayer = PLAYER1;

    private static int showing = ACTIVE;

    WinClient() {
        setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 40));
        msg = new Label("Status");
        input = new TextField(20);
        output = new Label();
        output.setBackground(Color.WHITE);
        send = new Button("Send");
        send.addActionListener(this);
        setLayout(new GridLayout(4, 1));
        add(msg);
        add(input);
        add(send);
        add(output);
    }

    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == send) {
            send();
        }
    }

    private void send(){
        // Wysylanie do serwera
        out.println(input.getText());
        msg.setText("OppositeTurn");
        send.setEnabled(false);
        input.setText("");
        input.requestFocus();
        showing = ACTIVE;
        actualPlayer = player;
    }

    private void receive(){
        try {
            // Odbieranie z serwera
            String str = in.readLine();
            output.setText(str);
            msg.setText("My turn");
            send.setEnabled(true);
            input.setText("");
            input.requestFocus();
        }
        catch (IOException e) {
            System.out.println("Read failed"); System.exit(1);}
    }

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

    /*
        Poczatkowe ustawienia klienta. Ustalenie ktory socket jest ktorym kliente
    */
    private void receiveInitFromServer() {
        try {
            player = Integer.parseInt(in.readLine());
            if (player== PLAYER1) {
                msg.setText("My Turn");
            } else {
                msg.setText("Opposite turn");
                send.setEnabled(false);
            }
        } catch (IOException e) {
            System.out.println("Read failed");
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        WinClient frame = new WinClient();
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.pack();
        frame.setVisible(true);
        frame.listenSocket();
        frame.receiveInitFromServer();
        frame.startThread();
    }

    private void startThread() {
        Thread gTh = new Thread(this);
        gTh.start();
    }

    @Override
    public void run() {
       if (player==PLAYER1) {
            f1();
        }
        else{
            f2();
        }
        // Mozna zrobic w jednej metodzie. Zostawiam
        // dla potrzeb prezentacji
        // f(player);
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
                    receive();
                    showing =NONACTIVE;
                }
                notifyAll();
            }
        }
    }

    /// Metoda uruchamiana w run dla PLAYER1
    void f1(){
        while(true) {
            synchronized (this) {
                if (actualPlayer== PLAYER1) {
                    try {
                        wait(10);
                    } catch (InterruptedException e) {
                    }
                }
              if (showing ==ACTIVE){
                  receive();
                 showing =NONACTIVE;
             }
                notifyAll();
            }
        }
    }

    /// Metoda uruchamiana w run dla PLAYER2
    void f2(){
        while(true) {
            synchronized (this) {
                if (actualPlayer== PLAYER2) {
                    try {
                        wait(10);
                    } catch (InterruptedException e) {
                    }
                }
                if (showing ==ACTIVE){
                    receive();
                    showing =NONACTIVE;
                }
                notifyAll();
            }
        }
    }
}




