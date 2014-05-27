package br.com.oraculo.server;

import br.com.oraculo.models.Question;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class MainServer {

	private SharedInformation sharedInformation;

	public MainServer() throws IOException {
		sharedInformation = new SharedInformation();
		loadQuestions();
	}

	public void run() throws IOException {
		ServerSocket serverSocket = new ServerSocket(7777);

		while (true) {
			Socket socket = serverSocket.accept();
			new Thread(new ClientListener(socket, sharedInformation)).start();
		}
	}

	private void loadQuestions() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("oraculoPU");
		EntityManager em = emf.createEntityManager();
//		TypedQuery<Question> query = em.createQuery(Question.NQ_FIND_ALL, Question.class);
		TypedQuery<Question> query = 
				em.createQuery("SELECT q from br.com.oraculo.models.Question q", 
				Question.class);
		sharedInformation.setQuestions(query.getResultList());
	}

	public static void main(String[] args) {
		try {
			new MainServer().run();
		} catch (IOException ex) {
			System.out.println("Error!");
		}
	}
}
