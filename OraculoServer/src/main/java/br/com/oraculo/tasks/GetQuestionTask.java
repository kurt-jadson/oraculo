package br.com.oraculo.tasks;

import br.com.oraculo.exceptions.NoMoreQuestionsException;
import br.com.oraculo.models.Client;
import br.com.oraculo.models.Question;
import br.com.oraculo.models.Room;
import br.com.oraculo.server.SharedInformation;

/**
 *
 * @author kurt
 */
public class GetQuestionTask extends ReturnResultTask {

	private String clientId;
	private String roomName;
	private Question question;

	public GetQuestionTask(SharedInformation sharedInformation) {
		super(sharedInformation);
	}

	public void setClientId(String client) {
		this.clientId = client;
	}

	public void setRoom(String room) {
		this.roomName = room;
	}

	@Override
	public Question getResultObject() {
		return question;
	}

	@Override
	public void run() {
		System.out.println("Getting a question for room " + roomName);

		try {
			Room room = new Room();
			room.setName(roomName);
			question = getSharedInformation().getQuestion(room);

			Client client = getSharedInformation().getClient(clientId, room);
			if(client != null) {
				client.addQuestion(question);
			}
		} catch(NoMoreQuestionsException nmqe) {
			System.out.println("No more questions.");
			//TODO: notificar cliente
		}
	}
	
}
