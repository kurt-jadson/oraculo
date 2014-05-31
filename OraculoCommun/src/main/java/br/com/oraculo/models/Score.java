package br.com.oraculo.models;

import java.io.Serializable;

/**
 *
 * @author kurt
 */
public class Score implements Serializable, Comparable<Score>, Mergeable<Score> {

	private static final long serialVersionUID = 3L;
	private Client client;
	private Room room;
	private Integer score;

	public Score() {
		score = Integer.valueOf("0");
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public void addAmount(int amount) {
		this.score += amount;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 53 * hash + (this.client != null ? this.client.hashCode() : 0);
		hash = 53 * hash + (this.room != null ? this.room.hashCode() : 0);
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
		final Score other = (Score) obj;
		if (this.client != other.client && (this.client == null || !this.client.equals(other.client))) {
			return false;
		}
		if (this.room != other.room && (this.room == null || !this.room.equals(other.room))) {
			return false;
		}
		return true;
	}

	public int compareTo(Score o) {
		return this.getScore().compareTo(o.getScore());
	}

	public void merge(Score m) {
		if(m.getScore() > score) {
			score = m.getScore();
		}
	}

}