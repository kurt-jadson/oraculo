package br.com.oraculo.tasks;

import br.com.oraculo.models.Room;
import br.com.oraculo.models.Score;
import br.com.oraculo.server.SharedInformation;
import java.util.List;

/**
 *
 * @author kurt
 */
public class GenerateScoreTask extends ReturnResultTask {

	private Room room;
	private List<Score> scores;

	public GenerateScoreTask(SharedInformation sharedInformation) {
		super(sharedInformation);
	}

	@Override
	public void run() {
		System.out.println("Generate " + room.getName() + "'s scores ...");
		scores = getSharedInformation().getScore(room);
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	@Override
	public Object getResultObject() {
		return scores;
	}

}