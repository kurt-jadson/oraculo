package br.com.oraculo.tasks;

import br.com.oraculo.models.Client;
import br.com.oraculo.models.Room;
import br.com.oraculo.server.SharedInformation;
import org.jppf.node.protocol.AbstractTask;

/**
 *
 * @author kurt
 */
public class AddInRoomTask extends AbstractTask<String> {

	private String clientId;
	private String roomName;
	private String nickname;
	private final SharedInformation sharedInformation;

	public AddInRoomTask(SharedInformation sharedInformation) {
		this.sharedInformation = sharedInformation;
	}

	public void run() {
		System.out.println("Connecting client " + nickname + " in room " + roomName);

		Client client = new Client();
		client.setId(clientId);
		client.setNickname(nickname);

		Room room = new Room();
		room.setName(roomName);

		sharedInformation.subscribeClient(client, room);
		sharedInformation.addPunctuation(room, client, 0);
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getRoom() {
		return roomName;
	}

	public void setRoom(String room) {
		this.roomName = room;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public SharedInformation getSharedInformation() {
		return sharedInformation;
	}

}