package br.com.oraculo.controller;

import br.com.oraculo.exceptions.CommunicationException;
import br.com.oraculo.exceptions.UnconnectException;
import br.com.oraculo.models.Score;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author kurt
 */
public class SocketController {

	private Socket socket;
	private String clientId;
	private String room;
	
	public void connect(String host, int port, String room, String nickname) throws UnconnectException {
		try {
			socket = new Socket(host, port);
			clientId = getClientIdentifier();
			this.room = room;

			StringBuilder messageToServer = new StringBuilder("connect:")
					.append(clientId)
					.append(":")
					.append(room)
					.append(":")
					.append(nickname);

			PrintWriter writer = new PrintWriter(socket.getOutputStream());
			writer.println(messageToServer);
			writer.flush();
		} catch (Exception ex) {
			throw new UnconnectException();
		}
	}

	private String getClientIdentifier() {
		StringBuilder builder = new StringBuilder();

		for(int i = 0; i < 10; i++) {
			int num = (int) Math.random() * 119 + 97;
			char[] c = Character.toChars(i);
			builder.append(c[0]);
		}

		return builder.toString();
	}

	public void start() throws CommunicationException {
		try {
			PrintWriter writer = new PrintWriter(socket.getOutputStream());
			writer.println("start:" + clientId);
			writer.flush();
		} catch(IOException ex) {
			throw new CommunicationException();
		}
	}

	public void verify() throws CommunicationException {
		try {
			PrintWriter writer = new PrintWriter(socket.getOutputStream());
			writer.println("verify:" + clientId);
			writer.flush();

			String advance = "no";
			while(advance.equals("no")) {
				Thread.sleep(2000);
				Scanner reader = new Scanner(socket.getInputStream());
				advance = reader.nextLine();
			}

			get();
		} catch(Exception ex) {
			throw new CommunicationException();
		}
	}

	public void get() {

	}

	public List<Score> score() throws CommunicationException {
		try {
			PrintWriter writer = new PrintWriter(socket.getOutputStream());

			StringBuilder messageToServer = new StringBuilder("score:")
					.append(clientId)
					.append(":")
					.append(room);

			writer.println(messageToServer);
			writer.flush();

			ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
			List<Score> scores = (List<Score>) objectInputStream.readObject();
			return scores;
		} catch(Exception ex) {
			throw new CommunicationException();
		}
	}
	
}
