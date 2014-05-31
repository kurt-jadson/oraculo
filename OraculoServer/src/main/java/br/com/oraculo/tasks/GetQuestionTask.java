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

	private Client client;
	private Room room;
	private Question question;

	public GetQuestionTask(SharedInformation sharedInformation) {
		super(sharedInformation);
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	@Override
	public Question getResultObject() {
		return question;
	}

	@Override
	public void run() {
		System.out.println("Getting a question for room " + room.getName());

		try {
			question = getSharedInformation().getQuestion(room);
			client.addQuestion(question);
		} catch(NoMoreQuestionsException nmqe) {
			System.out.println("No more questions.");
			//TODO: notificar cliente
		}
	}
	
}
