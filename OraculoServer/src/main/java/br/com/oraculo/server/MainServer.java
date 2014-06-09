package br.com.oraculo.server;

import br.com.oraculo.exceptions.ServerNotStartedException;
import br.com.oraculo.models.Question;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class MainServer {

	private int port;

	public MainServer() throws IOException {
		loadQuestions();
	}

	public void run() throws IOException {
		ServerSocket serverSocket = new ServerSocket(port);
		System.out.println("OraculoServer has started in port " + port);

		while (true) {
			Socket socket = serverSocket.accept();
			new Thread(new ClientListener(socket)).start();
		}
	}

	private void loadQuestions() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("oraculoPU");
		EntityManager em = emf.createEntityManager();
//		TypedQuery<Question> query = em.createQuery(Question.NQ_FIND_ALL, Question.class);
		TypedQuery<Question> query = 
				em.createQuery("SELECT q from br.com.oraculo.models.Question q", 
				Question.class);
		SharedInformation.getInstance().setQuestions(query.getResultList());
	}

	public void setPort(int port) {
		this.port = port;
	}

	public static void main(String[] args) {
		Scanner scanner = null;

		//Make compatible with netbeans directory development
		File file = new File("src/main/resources/META-INF/config/settings.txt");

		if(!file.exists()) {
			file = new File("META-INF/config/settings.txt");
		}

		//External file
		if(!file.exists()) {
			file = new File("config/settings.txt");
		}

		try {
			scanner = new Scanner(file);
			String config = scanner.nextLine();

			String[] params = config.split("=");
			if("server.port".equals(params[0])) {
				int p = Integer.valueOf(params[1]);

				MainServer mainServer = new MainServer();
				mainServer.setPort(p);
				mainServer.run();
			} else {
				throw new ServerNotStartedException("Could not locate parameter \"server.port\" in config file");
			}
		} catch (IOException ex) {
			System.out.println("Error!");
		} catch(NumberFormatException nfe) {
			System.out.println("Port number is not valid port.");
		} catch(ServerNotStartedException snse) {
			System.out.println(snse.getMessage());
		} finally {
			if(scanner != null) {
				scanner.close();
			}
		}
	}
}
