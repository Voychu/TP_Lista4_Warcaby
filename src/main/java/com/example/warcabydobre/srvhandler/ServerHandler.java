package com.example.warcabydobre.srvhandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import com.example.warcabydobre.utils.Config;


//Proxy do serwera
public class ServerHandler {

	Socket socket = null;
	PrintWriter outWriter = null;
	BufferedReader inReader = null;

	public ServerHandler(String address, int port) throws UnknownHostException, IOException {
		socket = new Socket(address, port);
		// Inicjalizacja wysylania do serwera
		outWriter = new PrintWriter(socket.getOutputStream(), true);
		// Inicjalizacja odbierania z serwera
		inReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

	/**
	 * @return the socket
	 */
	public Socket getSocket() {
		return socket;
	}

	/**
	 * @return the out
	 */
	public PrintWriter getOutWriter() {
		return outWriter;
	}

	/**
	 * @return the in
	 */
	public BufferedReader getInReader() {
		return inReader;
	}

	/*
	 * public void listenSocket() { try { socket = new Socket("localhost", 4444); //
	 * Inicjalizacja wysylania do serwera outWriter = new
	 * PrintWriter(socket.getOutputStream(), true); // Inicjalizacja odbierania z
	 * serwera inReader = new BufferedReader(new
	 * InputStreamReader(socket.getInputStream())); } catch (UnknownHostException e)
	 * { System.out.println("Unknown host: localhost"); System.exit(1); } catch
	 * (IOException e) { System.out.println("No I/O"); System.exit(1); } }
	 */

	public int receiveInitFromServer() throws IOException {
		System.out.println("Oczekiwanie...");
		int player = Integer.parseInt(inReader.readLine());
		System.out.println("Polaczono");
		return player;

	}

//TODO: Uzyc moveConverter
	public MessageState sendMessage(String message) {
		outWriter.println(message);

		if (message.equals("bye")) {
			return MessageState.EXIT;
		}

		// input.requestFocus();
		// TODO://showing = ACTIVE;
		// TODO://actualPlayer = player;
		return MessageState.OK;
	}

	public String receiveMessage() {
		try {
			// Odbieranie z serwera
			String str = inReader.readLine();
			if (str.equals("bye")) {
				return null;
			}
			return str;

		} catch (IOException e) {
			// System.out.println("Read failed");
			// System.exit(1);
			return null;
		}
	}

	public MessageState determineMessageState(String message) {

		if (message.equals("bye")) {
			return MessageState.EXIT;
		} else
			return MessageState.OK;

	}
	
	
	public String waitForServerApproval() {
		String message = receiveMessage();
		return message;
	}
	
	public void closeConnection(){
		try {
			this.socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
