package br.com.oraculo.tasks;

import br.com.oraculo.models.Client;
import br.com.oraculo.models.Question;
import br.com.oraculo.models.QuestionOption;
import br.com.oraculo.models.Room;
import br.com.oraculo.server.SharedInformation;

/**
 *
 * @author kurt
 */
public class ProcessAnswerTask extends ReturnResultTask {

	private String clientId;
	private String roomName;
	private Question question; 
	private QuestionOption userAnswer;

	public ProcessAnswerTask(SharedInformation sharedInformation) {
		super(sharedInformation);
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public void setUserAnswer(QuestionOption userAnswer) {
		this.userAnswer = userAnswer;
	}

	@Override
	public Object getResultObject() {
		return null;
	}

	@Override
	public void run() {
		System.out.println("Processing client answer ...");

		Room room = new Room();
		room.setName(roomName);
		Client client = getSharedInformation().getClient(clientId, room);

		//Assumed 2s is delay client-server communication
		long timeElapsed = client.getTimeElapsed(question) - 2000;
		boolean timeWasted = timeElapsed > question.getTimeToAnswer();
		boolean emptyUserAnswer = QuestionOption.NONE.equals(userAnswer);
		boolean incorrectAnswer = !userAnswer.equals(question.getAnswer());
		client.markAsAnswered(question);

		if(timeWasted || emptyUserAnswer || incorrectAnswer) {
			return;
		}

		getSharedInformation().addPunctuation(room, client, question.getAmount());
	}

}
