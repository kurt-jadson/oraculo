package br.com.oraculo.server;

import br.com.oraculo.exceptions.NoMoreQuestionsException;
import br.com.oraculo.exceptions.ProtocolWorkerException;
import br.com.oraculo.exceptions.RoomStartedException;
import br.com.oraculo.models.Client;
import br.com.oraculo.models.Question;
import br.com.oraculo.models.QuestionOption;
import br.com.oraculo.models.Room;
import br.com.oraculo.tasks.AddInRoomTask;
import br.com.oraculo.tasks.GenerateScoreTask;
import br.com.oraculo.tasks.GetQuestionTask;
import br.com.oraculo.tasks.ProcessAnswerTask;
import br.com.oraculo.tasks.VerifyTask;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jppf.JPPFException;
import org.jppf.client.JPPFJob;
import org.jppf.node.protocol.Task;
import org.jppf.server.protocol.JPPFTask;

/**
 *
 * @author kurt
 */
public class ProtocolWorker {

	private SharedInformation sharedInformation;
	private Object objectToSend;
	private BufferedOutputStream writer;
	private Scanner reader;

	public ProtocolWorker(SharedInformation sharedInformation) {
		this.sharedInformation = sharedInformation;
	}

	public void execute(String command, String clientId, Socket socket, String... parameters)
			throws ProtocolWorkerException {

		System.out.println("Client " + clientId + " request command " + command);

		if ("connect".equals(command)) {
			connect(clientId, parameters[0], parameters[1], socket);
		} else if ("start".equals(command)) {
			start(clientId, parameters[0]);
		} else if ("verify".equals(command)) {
			verify(parameters[0], socket);
		} else if ("get".equals(command)) {
			get(clientId, parameters[0], socket);
		} else if ("score".equals(command)) {
			score(parameters[0], socket);
		} else if ("send".equals(command)) {
			send(clientId, parameters[0], parameters[1], parameters[2], socket);
		} else if ("disconnect".equals(command)) {
			disconnect(clientId, parameters[0]);
		}

	}

	private void connect(String clientId, String room, String nickname, Socket socket) 
			throws ProtocolWorkerException {
		try {
			writer = new BufferedOutputStream(socket.getOutputStream());
			reader = new Scanner(socket.getInputStream());

			AddInRoomTask task = new AddInRoomTask(sharedInformation);
			task.setClientId(clientId);
			task.setNickname(nickname);
			task.setRoom(room);

			JPPFJob job = createJob("AddInRoom client " + nickname, task);
			executeBlockingJob(job);
		} catch (Exception ex) {
			throw new ProtocolWorkerException("Could not connect client to room.");
		}
	}

	private void start(String clientId, String roomName) throws ProtocolWorkerException {
		try {
			Room room = new Room();
			room.setName(roomName);

			Client client = new Client();
			client.setId(clientId);

			sharedInformation.start(client, room);
		} catch(RoomStartedException ex) {
			throw new ProtocolWorkerException(ex.getMessage());
		}
	}

	private void verify(String roomName, Socket socket) throws ProtocolWorkerException {
		try {
			Room room = new Room();
			room.setName(roomName);

			VerifyTask task = new VerifyTask(sharedInformation);
			task.setRoom(room);

			JPPFJob job = createJob("Verify", task);
			executeBlockingJob(job);

			BufferedOutputStream bufferStream = new BufferedOutputStream(socket.getOutputStream());
			int b = Boolean.TRUE.equals((Boolean) objectToSend) ? 1 : 0;
			bufferStream.write(b);
			bufferStream.flush();
		} catch(Exception ex) {
			throw new ProtocolWorkerException("Impossible to verify answers.");
		} finally {
			objectToSend = null;
		}
	}

	private void get(String clientId, String room, Socket socket) throws ProtocolWorkerException {
		try {
			GetQuestionTask task = new GetQuestionTask(sharedInformation);
			task.setClientId(clientId);
			task.setRoom(room);

			JPPFJob job = createJob("GetQuestion", task);
			executeBlockingJob(job);

			Room r = new Room();
			r.setName(room);
			Client c = sharedInformation.getClient(clientId, r);
			System.out.println(c.getTimeElapsed((Question) objectToSend));

			ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objectOutputStream.writeObject(objectToSend);
			objectOutputStream.flush();
		} catch (Exception ex) {
			throw new ProtocolWorkerException("Could not get question from server.");
		} finally {
			objectToSend = null;
		}
	}

	private void score(String room, Socket socket) throws ProtocolWorkerException {
		try {
			GenerateScoreTask task = new GenerateScoreTask(sharedInformation);
			task.setRoom(room);

			JPPFJob job = createJob("Getting score from room " + room, task);
			executeBlockingJob(job);

			ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objectOutputStream.writeObject(objectToSend);
			objectOutputStream.flush();
		} catch (IOException ex) {
			throw new ProtocolWorkerException("Could not get score for room " + room);
		} catch (Exception ex) {
			throw new ProtocolWorkerException("Could not get score for room " + room);
		} finally {
			objectToSend = null;
		}
	}

	private void send(String clientId, String room, String questionId, String answer, Socket socket) 
			throws ProtocolWorkerException {
		try {
			Question question = findQuestionById(Long.valueOf(questionId));
			ProcessAnswerTask task = new ProcessAnswerTask(sharedInformation);
			task.setClientId(clientId);
			task.setRoomName(room);
			task.setQuestion(question);
			task.setUserAnswer(QuestionOption.getByIdentifierNumber(Integer.valueOf(answer)));

			Room r = new Room();
			r.setName(room);
			Client c = sharedInformation.getClient(clientId, r);
			System.out.println(c.getTimeElapsed(question));

			JPPFJob job = createJob("ProcessAnswer", task);
			executeBlockingJob(job);

			writer.write(question.getAnswer().getIdentifierNumber());
			writer.flush();
		} catch(Exception ex) {
			ex.printStackTrace();
			throw new ProtocolWorkerException("Could not send answer.");
		}

	}

	private void disconnect(String clientId, String roomName) {
		try {
			writer.close();
			reader.close();
		} catch(IOException ex) {

		}
	}

	private Question findQuestionById(Long questionId) {
		for(Question q : sharedInformation.getQuestions()) {
			if(q.getId().equals(questionId)) {
				return q;
			}
		}
		return null;
	}

	private JPPFJob createJob(String jobName, Task task) throws JPPFException {
		JPPFJob job = new JPPFJob();
		job.setName(jobName);
		job.addTask(task);
		return job;
	}

	private void executeBlockingJob(final JPPFJob job) throws Exception {
		job.setBlocking(true);
		List<JPPFTask> results = SharedInformation.getJppfClient().submit(job);
		processExecutionResults(results);
	}

	private void processExecutionResults(final List<JPPFTask> results) {
		for (JPPFTask task : results) {
			if (task.getException() != null) {
				task.getException().printStackTrace();
			}

			Object o = task.getTaskObject();
			if (o instanceof AddInRoomTask) {
				AddInRoomTask addInRoomTask = (AddInRoomTask) o;
				sharedInformation = addInRoomTask.getSharedInformation();
			} else if (o instanceof GetQuestionTask) {
				GetQuestionTask getQuestionTask = (GetQuestionTask) o;
				sharedInformation = getQuestionTask.getSharedInformation();
				objectToSend = getQuestionTask.getQuestion();

				Room r = new Room();
				r.setName("sala01");
				Client c = sharedInformation.getClient("aaaaaaaaaa", r);
				System.out.println("TIme: " + c.getTimeElapsed(getQuestionTask.getQuestion()));
			} else if (o instanceof GenerateScoreTask) {
				GenerateScoreTask generateScoreTask = (GenerateScoreTask) o;
				objectToSend = generateScoreTask.getScores();
			} else if (o instanceof ProcessAnswerTask) {
				ProcessAnswerTask processAnswerTask = (ProcessAnswerTask) o;
				sharedInformation = processAnswerTask.getSharedInformation();
			} else if (o instanceof VerifyTask) {
				VerifyTask verifyTask = (VerifyTask) o;
				objectToSend = verifyTask.getVerified();
				sharedInformation = verifyTask.getSharedInformation();
			}
		}
	}
}