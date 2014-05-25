package br.com.oraculo.tasks;

import br.com.oraculo.models.Room;
import br.com.oraculo.models.Score;
import br.com.oraculo.server.SharedInformation;
import java.util.List;
import org.jppf.node.protocol.AbstractTask;

/**
 *
 * @author kurt
 */
public class GenerateScoreTask extends AbstractTask<String> {

	private Room room;
	private final SharedInformation sharedInformation;
	private List<Score> scores;

	public GenerateScoreTask(SharedInformation sharedInformation) {
		this.sharedInformation = sharedInformation;
	}

	@Override
	public void run() {
		System.out.println("Generate " + room.getName() + "'s scores ...");
		scores = sharedInformation.getScore(room);
	}

	public void setRoom(String roomName) {
		room = new Room();
		room.setName(roomName);
	}

	public List<Score> getScores() {
		return scores;
	}

}