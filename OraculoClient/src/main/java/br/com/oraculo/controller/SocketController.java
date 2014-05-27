package br.com.oraculo.controller;

import br.com.oraculo.exceptions.CommunicationException;
import br.com.oraculo.exceptions.UnconnectException;
import br.com.oraculo.models.Question;
import br.com.oraculo.models.QuestionOption;
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
	private PrintWriter writer;
	private Scanner reader;

	public void connect(String host, int port, String room, String nickname) throws UnconnectException {
		try {
			socket = new Socket(host, port);
			clientId = getClientIdentifier();
			this.room = room;

			writer = new PrintWriter(socket.getOutputStream());
			reader = new Scanner(socket.getInputStream());

			String messageToServer = buildMessage("connect", clientId, room, nickname);
			writer.println(messageToServer);
			writer.flush();
		} catch (Exception ex) {
			throw new UnconnectException();
		}
	}

	private String getClientIdentifier() {
		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < 10; i++) {
			int num = (int) Math.random() * 22 + 97;
			char c = (char) num;
			builder.append(c);
		}

		return builder.toString();
	}

	public void start() throws CommunicationException {
		String messageToServer = buildMessage("start", clientId, room);
		writer.println(messageToServer);
		writer.flush();
	}

	public void verify() throws CommunicationException {
		try {
			String messageToServer = buildMessage("verify", clientId, room);
			writer.println(messageToServer);
			writer.flush();

			String advance = reader.nextLine();
			if (advance.equals("false")) {
				Thread.sleep(2000);
				verify();
			}
		} catch (Exception ex) {
			throw new CommunicationException();
		}
	}

	public Question get() throws CommunicationException {
		try {
			String messageToServer = buildMessage("get", clientId, room);
			writer.println(messageToServer);
			writer.flush();

			ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
			Question question = (Question) objectInputStream.readObject();
			return question;
		} catch (Exception ex) {
			throw new CommunicationException();
		}
	}

	public QuestionOption send(Long questionId, QuestionOption answer) {
		String messageToServer = buildMessage(
				"send",
				clientId,
				room,
				questionId.toString(),
				Integer.valueOf(answer.getIdentifierNumber()).toString());
		writer.println(messageToServer);
		writer.flush();

		String serverAnswer = reader.nextLine();
		return QuestionOption.getByIdentifierNumber(Integer.valueOf(serverAnswer));
	}

	public List<Score> score() throws CommunicationException {
		try {
			String messageToServer = buildMessage("score", clientId, room);
			writer.println(messageToServer);
			writer.flush();

			ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
			List<Score> scores = (List<Score>) objectInputStream.readObject();
			return scores;
		} catch (Exception ex) {
			throw new CommunicationException();
		}
	}

	public void disconnect() {
		String messageToServer = buildMessage("disconnect", clientId, room);
		writer.println();
		writer.flush();
		writer.close();
		reader.close();
	}

	private String buildMessage(String... parameters) {
		StringBuilder builder = new StringBuilder();

		for (String s : parameters) {
			builder.append(s).append(":");
		}

		builder.deleteCharAt(builder.length() - 1);
		return builder.toString();
	}
}
