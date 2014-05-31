package br.com.oraculo.tasks;

import br.com.oraculo.exceptions.NoMoreQuestionsException;
import br.com.oraculo.models.Client;
import br.com.oraculo.models.Question;
import br.com.oraculo.models.Room;
import br.com.oraculo.server.SharedInformation;
import java.util.Set;

/**
 *
 * @author kurt
 */
public class VerifyTask extends ReturnResultTask {

	private Room room;
	private Boolean verified;

	public VerifyTask(SharedInformation sharedInformation) {
		super(sharedInformation);
		verified = Boolean.FALSE;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	@Override
	public Boolean getResultObject() {
		return verified;
	}

	@Override
	public void run() {
		System.out.println("Verifying if all clients answered question ...");

		try {
			Set<Client> clients = room.getClients();
			Question question = getSharedInformation().getQuestion(room);

			for (Client client : clients) {
				System.out.println("Question in turn: " + question);
				System.out.println("Client: " + client);

				Long startedTime = client.getStartedTime(question);
				if (startedTime != Client.ALREADY_ANSWERED) {
					if (startedTime == Client.NOT_YET_ANSWERED) {
						verified = Boolean.TRUE;
						System.out.println("Não respondeu, mas vai");
					}
					return;
				}
				System.out.println("Já respondeu");
			}

			verified = Boolean.TRUE;
			getSharedInformation().changeQuestion(room);
		} catch (NoMoreQuestionsException ex) {
			//TODO: notificar cliente
		}
	}
}