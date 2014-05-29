package br.com.oraculo.tasks;

import br.com.oraculo.exceptions.NoMoreQuestionsException;
import br.com.oraculo.models.Client;
import br.com.oraculo.models.Question;
import br.com.oraculo.models.Room;
import br.com.oraculo.server.SharedInformation;
import org.jppf.node.protocol.AbstractTask;

/**
 *
 * @author kurt
 */
public class GetQuestionTask extends AbstractTask<String> {

	private final SharedInformation sharedInformation;
	private String clientId;
	private String roomName;
	private Question question;

	public GetQuestionTask(SharedInformation sharedInformation) {
		this.sharedInformation = sharedInformation;
	}

	public void setClientId(String client) {
		this.clientId = client;
	}

	public void setRoom(String room) {
		this.roomName = room;
	}

	public Question getQuestion() {
		return question;
	}

	public SharedInformation getSharedInformation() {
		return sharedInformation;
	}

	@Override
	public void run() {
		System.out.println("Getting a question for room " + roomName);

		try {
			Room room = new Room();
			room.setName(roomName);
			question = sharedInformation.getQuestion(room);

			Client client = sharedInformation.getClient(clientId, room);
			if(client != null) {
				client.addQuestion(question);
			}
		} catch(NoMoreQuestionsException nmqe) {
			System.out.println("No more questions.");
			//TODO: notificar cliente
		}
	}
	
}
