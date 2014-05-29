package br.com.oraculo.tasks;

import br.com.oraculo.models.Client;
import br.com.oraculo.models.Room;
import br.com.oraculo.server.SharedInformation;

/**
 *
 * @author kurt
 */
public class AddInRoomTask extends ReturnResultTask {

	private String clientId;
	private String roomName;
	private String nickname;

	public AddInRoomTask(SharedInformation sharedInformation) {
		super(sharedInformation);
	}

	public void run() {
		System.out.println("Connecting client " + nickname + " in room " + roomName);

		Client client = new Client();
		client.setId(clientId);
		client.setNickname(nickname);

		Room room = new Room();
		room.setName(roomName);

		getSharedInformation().subscribeClient(client, room);
		getSharedInformation().addPunctuation(room, client, 0);
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public void setRoom(String room) {
		this.roomName = room;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Override
	public Object getResultObject() {
		return null;
	}

}