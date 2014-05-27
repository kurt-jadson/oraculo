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

	private SharedInformation sharedInformation;
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
				if (client.getStartedTime(question) != -1L) {
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
