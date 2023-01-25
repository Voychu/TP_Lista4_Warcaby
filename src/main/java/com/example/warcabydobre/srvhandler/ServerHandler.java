package com.example.warcabydobre.srvhandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import com.example.warcabydobre.utils.Config;


/**
 * Proxy to the server. It delegates performing
 * server operations.
 *
 */
public class ServerHandler {

	/**
	 * socket of the player
	 */
	Socket socket = null;
	/**
	 * object responsible for sending messages
	 * to the server
	 */
	PrintWriter outWriter = null;
	/**
	 * object responsible for reading messages
	 * from the server
	 */
	BufferedReader inReader = null;

	/**
	 * Constructor of serverHandler
	 * @param address on which player communicate
	 * with the server
	 * @param port on which server is listening
	 * @throws UnknownHostException exception thrown
	 * when there is an error on the server
	 * @throws IOException exception thrown when
	 * there is an error while accessing information from 
	 * the server
	 */
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

	/**
	 * Method responsible for waiting for both players
	 * connection and seting who is first and who
	 * is second
	 * @return int number of connected player 
	 * @throws IOException exception thrown when
	 * there is an error while accessing information from 
	 * the server
	 */
	public int receiveInitFromServer() throws IOException {
		System.out.println("Oczekiwanie...");
		int player = Integer.parseInt(inReader.readLine());
		System.out.println("Polaczono");
		return player;

	}

	/**
	 * Method responsible for sending message 
	 * to other player via server
	 * @param message which is sent to the server and then it's going 
	 * to be received by the second player.
	 * @return the state of sent message - if the sending was successful or not.
	 */
	public MessageState sendMessage(String message) {
		outWriter.println(message);
		System.out.println(message);

		if (message.equals("bye")) {
			return MessageState.EXIT;
		}

		return MessageState.OK;
	}

	/**
	 * Receiving the message sent to the server by another player.
	 * @return received message
	 */
	public String receiveMessage() {
		try {
			// Odbieranie z serwera
			String str = inReader.readLine();
			System.out.println(str);
			if (str.equals("bye")) {
				return null;
			}
			return str;

		} catch (IOException e) {
			return null;
		}
	}

	
	
	/**
	 * Method responsible for
	 * closing connection with the server
	 */
	public void closeConnection(){
		try {
			this.socket.close();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}

}
