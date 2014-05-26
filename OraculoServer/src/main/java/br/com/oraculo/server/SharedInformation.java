package br.com.oraculo.server;

import br.com.oraculo.models.Client;
import br.com.oraculo.models.Question;
import br.com.oraculo.models.Room;
import br.com.oraculo.models.Score;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.jppf.client.JPPFClient;

/**
 *
 * @author kurt
 */
public class SharedInformation implements Serializable {

	private static JPPFClient jppfClient = null;
	private Set<Room> rooms;
	private List<Score> scores;
	private List<Question> questions;

	public SharedInformation() {
		rooms = new HashSet<Room>();
		scores = new ArrayList<Score>();
	}

	public static JPPFClient getJppfClient() {
		if(jppfClient == null) {
			jppfClient = new JPPFClient();
		}
		return jppfClient;
	}

	public void subscribeClient(Client client, Room room) {
		for(Room r : rooms) {
			if(r.equals(room)) {
				r.subscribeClient(client);
				return;
			}
		}

		room.subscribeClient(client);
		room.setQuestions(questions);
		rooms.add(room);
	}

	public void addPunctuation(Room room, Client client, int amount) {
		for(Score score : scores) {
			if(score.getRoom().equals(room) && score.getClient().equals(client)) {
				score.addAmount(amount);
				return;
			}
		}

		Score s = new Score();
		s.setClient(client);
		s.setRoom(room);
		scores.add(s);
	}

	public List<Score> getScore(Room room) {
		List<Score> rScore = new ArrayList<Score>();

		for(Score s : scores) {
			if(s.getRoom().equals(room)) {
				rScore.add(s);
			}
		}

		Collections.sort(rScore);
		return rScore;
	}

	public List<Question> getQuestions() {
		return Collections.unmodifiableList(questions);
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public void printRooms() {
		System.out.println(rooms);
	}

}
