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

	/**
	 * Awaiting for player connection
	 * @return int of connected player
	 * @throws IOException
	 */
	public int receiveInitFromServer() throws IOException {
		System.out.println("Oczekiwanie...");
		int player = Integer.parseInt(inReader.readLine());
		System.out.println("Polaczono");
		return player;

	}

	/**
	 *
	 * @param message which is sent to the server and then it's going to be received by the second player.
	 * @return the state of sent message - if the sending was succesful or not.
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
	 * @return
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
