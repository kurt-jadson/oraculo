package br.com.oraculo.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author kurt
 */
public class Room implements Serializable {

	private static final long serialVersionUID = 2L;
	private String name;
	private Set<Client> clients;
	private List<Question> questions;
	private boolean started;

	public Room() {
		clients = new HashSet<Client>();
		questions = new ArrayList<Question>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void start() {
		started = true;
	}

	public boolean isStarted() {
		return started;
	}

	public void subscribeClient(Client client) {
		clients.add(client);
	}

	public void unsubscribeClient(Client client) {
		clients.remove(client);
	}

	public Client getClient(String id) {
		for(Client client : clients) {
			if(client.getId().equals(id)) {
				return client;
			}
		}

		return null;
	}

	public Set<Client> getClients() {
		return Collections.unmodifiableSet(clients);
	}

	public void setQuestions(List<Question> questions) {
		this.questions.addAll(questions);
	}

	public Question getQuestion() {
		if(questions.isEmpty()) {
			return null;
		}
		
		Collections.shuffle(questions);
		return questions.remove(0);
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 61 * hash + (this.name != null ? this.name.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Room other = (Room) obj;
		if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Room{" + "name=" + name + ", clients=" + clients + '}';
	}

}