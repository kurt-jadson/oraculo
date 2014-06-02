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

	private Client client;
	private Room room;
	private Question question; 
	private QuestionOption userAnswer;

	public ProcessAnswerTask(SharedInformation sharedInformation) {
		super(sharedInformation);
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public void setRoom(Room room) {
		this.room = room;
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

		//Assumed 2s is delay client-server communication
		long timeElapsed = client.getTimeElapsed(question) - 2000;
		boolean timeWasted = timeElapsed > (question.getTimeToAnswer() * 1000);
		boolean emptyUserAnswer = QuestionOption.NONE.equals(userAnswer);
		boolean incorrectAnswer = !userAnswer.equals(question.getAnswer());
		client.markAsAnswered(question);

		if(timeWasted || emptyUserAnswer || incorrectAnswer) {
			return;
		}

		getSharedInformation().addPunctuation(room, client, question.getAmount());
	}

}
