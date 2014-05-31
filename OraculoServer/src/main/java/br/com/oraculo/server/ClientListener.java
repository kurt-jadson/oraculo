package br.com.oraculo.server;

import br.com.oraculo.exceptions.ServerException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

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
		} catch (ServerException ex) {
			ex.printStackTrace();
			PrintWriter writer;
			try {
				writer = new PrintWriter(socket.getOutputStream());
				writer.println(ex.getErrorId());
				writer.flush();
			} catch (IOException ex1) {
				Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, null, ex1);
			}
		}
	}
}