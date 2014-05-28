package br.com.oraculo.tasks;

import br.com.oraculo.exceptions.NoMoreQuestionsException;
import br.com.oraculo.models.Client;
import br.com.oraculo.models.Question;
import br.com.oraculo.models.Room;
import br.com.oraculo.server.SharedInformation;
import java.util.Set;
import org.jppf.node.protocol.AbstractTask;

/**
 *
 * @author kurt
 */
public class VerifyTask extends AbstractTask<String> {

	private final SharedInformation sharedInformation;
	private Room room;
	private Boolean verified;

	public VerifyTask(SharedInformation sharedInformation) {
		verified = Boolean.FALSE;
		this.sharedInformation = sharedInformation;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Boolean getVerified() {
		return verified;
	}

	public SharedInformation getSharedInformation() {
		return sharedInformation;
	}

	@Override
	public void run() {
		System.out.println("Verifying if all clients answered question ...");

		try {
			Set<Client> clients = sharedInformation.getClients(room);
			Question question = sharedInformation.getQuestion(room);

			for (Client client : clients) {
				Long startedTime = client.getStartedTime(question);
				if (startedTime != Client.ALREADY_ANSWERED) {
					if(startedTime == Client.NOT_YET_ANSWERED) {
						verified = Boolean.TRUE;
					}
					return;
				}
			}

			sharedInformation.changeQuestion(room);
			verified = Boolean.TRUE;
		} catch (NoMoreQuestionsException ex) {
			//TODO: notificar cliente
		}
	}
}
