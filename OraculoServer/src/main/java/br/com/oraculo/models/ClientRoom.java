package br.com.oraculo.models;

import java.io.Serializable;

/**
 *
 * @author kurt
 */
public class ClientRoom implements Serializable {

	private static final long serialVersionUID = 5L;
	private Client client;
	private Room room;

	public ClientRoom(Client client, Room room) {
		this.client = client;
		this.room = room;
	}

	public Client getClient() {
		return client;
	}

	public Room getRoom() {
		return room;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 13 * hash + (this.client != null ? this.client.hashCode() : 0);
		hash = 13 * hash + (this.room != null ? this.room.hashCode() : 0);
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
		final ClientRoom other = (ClientRoom) obj;
		if (this.client != other.client && (this.client == null || !this.client.equals(other.client))) {
			return false;
		}
		if (this.room != other.room && (this.room == null || !this.room.equals(other.room))) {
			return false;
		}
		return true;
	}

}