package br.com.oraculo.server;

import br.com.oraculo.exceptions.ProtocolWorkerException;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author kurt
 */
public class ClientListener implements Runnable {

	private Scanner reader;
	private Socket socket;
	private ProtocolWorker protocolWorker;

	public ClientListener(Socket socket) {
		try {
			this.socket = socket;
			protocolWorker = new ProtocolWorker();
			reader = new Scanner(socket.getInputStream());
		} catch (IOException ex) {
			//notificar o cliente
		}
	}

	public void run() {
		try {
			String message;
			while ((message = reader.nextLine()) != null) {
				String[] all = message.split(":");
				String[] parameters = new String[all.length - 2];

				for (int i = 2, j = all.length; i < j; i++) {
					parameters[i - 2] = all[i];
				}

				String command = all[0];
				String clientId = all[1];
				protocolWorker.execute(command, clientId, socket, parameters);
			}
		} catch (ProtocolWorkerException ex) {
			//notificar o cliente
			ex.printStackTrace();
		}
	}
}
