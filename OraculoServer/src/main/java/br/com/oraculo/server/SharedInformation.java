package br.com.oraculo.server;

import br.com.oraculo.exceptions.NoMoreQuestionsException;
import br.com.oraculo.models.Client;
import br.com.oraculo.models.Mergeable;
import br.com.oraculo.models.Question;
import br.com.oraculo.models.Room;
import br.com.oraculo.models.Score;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jppf.client.JPPFClient;

/**
 *
 * @author kurt
 */
public class SharedInformation implements Serializable, Mergeable<SharedInformation> {

	private static JPPFClient jppfClient = null;
	private static SharedInformation sharedInformation;
	private Set<Room> rooms;
	private List<Score> scores;
	private List<Question> questions;
	private Map<Room, Question> questionInTurn;
	private long version;

	private SharedInformation() {
		rooms = new HashSet<Room>();
		scores = new ArrayList<Score>();
		questionInTurn = new HashMap<Room, Question>();
		version = 0l;
	}

	public static SharedInformation getInstance() {
		if (sharedInformation == null) {
			sharedInformation = new SharedInformation();
		}

		return sharedInformation;
	}

	public static void changeInstance(final SharedInformation s) {
		if(s.getVersion() < sharedInformation.getVersion()) {
			sharedInformation.merge(s);
		} else {
			sharedInformation = s;
		}
		sharedInformation.upVersion();
	}

	public void merge(SharedInformation n) {
		for(Room r : rooms) {
			r.merge(n.getRoom(r.getName()));
		}

		scores.addAll(n.getScores());
		for(Score s : scores) {
			for(Score mS : n.getScores()) {
				if(s.equals(mS)) {
					s.merge(mS);
				}
			}
		}

		System.out.println("Questão no turno ...");
		System.out.println(questionInTurn);
		System.out.println(n.getMapQuestions());
	}

	public static JPPFClient getJppfClient() {
		if (jppfClient == null) {
			jppfClient = new JPPFClient();
		}
		return jppfClient;
	}

	public void subscribeClient(Client client, Room room) {
		for (Room r : rooms) {
			if (r.equals(room)) {
				r.subscribeClient(client);
				return;
			}
		}

		room.subscribeClient(client);
		room.setQuestions(questions);
		rooms.add(room);
	}

	public void start(Room room) {
		if(!room.isStarted()) {
			room.start();
			questionInTurn.put(room, room.getQuestion());
		}
	}

	public Room getRoom(String roomName) {
		for (Room r : rooms) {
			if (roomName.equals(r.getName())) {
				return r;
			}
		}
		return null;
	}

	public Question getQuestion(Room room) throws NoMoreQuestionsException {
		Question questionRound = questionInTurn.get(room);

		if (questionRound == null) {
			throw new NoMoreQuestionsException();
		}

		return questionRound;
	}

	public boolean isSameQuestionOfTurn(Question question, Room room) {
		return question.equals(questionInTurn.get(room));
	}

	public void changeQuestion(Room room) {
		for (Room r : rooms) {
			if (r.equals(room)) {
				questionInTurn.put(room, room.getQuestion());
			}
		}
	}

	public Client getClient(String clientId, Room room) {
		for (Room r : rooms) {
			if (r.equals(room)) {
				return r.getClient(clientId);
			}
		}

		return null;
	}

	public Set<Client> getClients(Room room) {
		for (Room r : rooms) {
			if (r.equals(room)) {
				return r.getClients();
			}
		}

		return Collections.EMPTY_SET;
	}

	public void addPunctuation(Room room, Client client, int amount) {
		for (Score score : scores) {
			if (score.getRoom().equals(room) && score.getClient().equals(client)) {
				score.addAmount(amount);
				return;
			}
		}

		Score s = new Score();
		s.setClient(client);
		s.setRoom(room);
		scores.add(s);
	}

	public Map<Room, Question> getMapQuestions() {
		return questionInTurn;
	}

	public List<Score> getScore(Room room) {
		List<Score> rScore = new ArrayList<Score>();

		for (Score s : scores) {
			if (s.getRoom().equals(room)) {
				rScore.add(s);
			}
		}

		Collections.sort(rScore);
		return rScore;
	}

	public List<Score> getScores() {
		return scores;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public List<Question> getQuestions() {
		return Collections.unmodifiableList(questions);
	}

	public void printRooms() {
		System.out.println(rooms);
	}

	public long getVersion() {
		return version;
	}

	public void upVersion() {
		version++;
	}

}