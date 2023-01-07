package com.example.warcabydobre.srvhandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.application.Platform;


//Proxy do serwera
public class ServerHandler {

	Socket socket = null;
	PrintWriter out = null;
	BufferedReader in = null;
	
	public ServerHandler() {
		// TODO Auto-generated constructor stub
	}
	
	
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


//TODO: Uzyc moveConverter
public MessageState sendMessage(String message) {
	out.println(message);
	

	if (message.equals("bye")) {
		return MessageState.EXIT;
	}
	

	// input.requestFocus();
	//TODO://showing = ACTIVE;
	//TODO://actualPlayer = player;
	return MessageState.OK;
}




public String receiveMessage() {
	try {
		// Odbieranie z serwera
		String str = in.readLine();
		if (str.equals("bye")) {
			return null;
		}
		return str;
		
	} catch (IOException e) {
		//System.out.println("Read failed");
		//System.exit(1);
		return null;
	}
}

public MessageState receiveMessageState(String message) {
	
		if (message.equals("bye")) {
			return MessageState.EXIT;
		} 
		else return MessageState.OK;
		
}


}




