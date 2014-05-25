package br.com.oraculo.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer 
{

	private ProtocolWorker protocolWorker;
	private SharedInformation sharedInformation;

	public MainServer() throws IOException {
		sharedInformation = new SharedInformation();
		protocolWorker = new ProtocolWorker(sharedInformation);
	}

	public void run() throws IOException {
		ServerSocket serverSocket = new ServerSocket(7777);

		while(true) {
			Socket socket = serverSocket.accept();
			new Thread(new ClientListener(socket, protocolWorker)).start();
		}
	}

    public static void main( String[] args ) {
		try {
			new MainServer().run();
		} catch (IOException ex) {
			System.out.println("Error!");
		}
    }
}
