package br.com.oraculo.server;

import br.com.oraculo.exceptions.ProtocolWorkerException;
import br.com.oraculo.exceptions.RoomStartedException;
import br.com.oraculo.exceptions.ServerException;
import br.com.oraculo.models.Client;
import br.com.oraculo.models.Question;
import br.com.oraculo.models.QuestionOption;
import br.com.oraculo.models.Room;
import br.com.oraculo.tasks.AddInRoomTask;
import br.com.oraculo.tasks.GenerateScoreTask;
import br.com.oraculo.tasks.GetQuestionTask;
import br.com.oraculo.tasks.ProcessAnswerTask;
import br.com.oraculo.tasks.ReturnResultTask;
import br.com.oraculo.tasks.VerifyTask;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
import org.jppf.JPPFException;
import org.jppf.client.JPPFJob;
import org.jppf.node.protocol.Task;
import org.jppf.server.protocol.JPPFTask;

/**
 *
 * @author kurt
 */
public class ProtocolWorker {

	private Object objectToSend;
	private PrintWriter writer;
	private Scanner reader;

	public void execute(String command, String clientId, Socket socket, String... parameters)
			throws ServerException {

		System.out.println("Client " + clientId + " request command " + command);

		if ("connect".equals(command)) {
			connect(clientId, parameters[0], parameters[1], socket);
		} else if ("start".equals(command)) {
			start(parameters[0]);
		} else if ("verify".equals(command)) {
			verify(parameters[0], socket, clientId, parameters[1]);
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

	private void connect(String clientId, String roomName, String nickname, Socket socket)
			throws ServerException {
		try {
			writer = new PrintWriter(socket.getOutputStream());
			reader = new Scanner(socket.getInputStream());

			AddInRoomTask task = new AddInRoomTask(SharedInformation.getInstance());
			task.setClientId(clientId);
			task.setNickname(nickname);
			task.setRoomName(roomName);

			JPPFJob job = createJob("AddInRoom client " + nickname, task);
			executeBlockingJob(job);

			Room room = SharedInformation.getInstance().getRoom(roomName);
			if (room.isStarted()) {
				throw new RoomStartedException();
			}

			writer.println("done");
			writer.flush();
		} catch (ServerException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new ProtocolWorkerException("Could not connect client to room.");
		}
	}

	private void start(String roomName) throws ProtocolWorkerException {
		Room room = SharedInformation.getInstance().getRoom(roomName);
		SharedInformation.getInstance().start(room);
	}

	private void verify(String roomName, Socket socket, String clientId, String questionId) 
			throws ProtocolWorkerException {
		try {
			Room room = SharedInformation.getInstance().getRoom(roomName);
			Question question = findQuestionById(Long.valueOf(questionId));

			VerifyTask task = new VerifyTask(SharedInformation.getInstance());
			task.setRoom(room);
			task.setQuestion(question);

			JPPFJob job = createJob("Verify", task);
			executeBlockingJob(job);

			writer.println(objectToSend);
			writer.flush();
		} catch (Exception ex) {
			throw new ProtocolWorkerException("Impossible to verify answers.");
		} finally {
			objectToSend = null;
		}
	}

	private void get(String clientId, String roomName, Socket socket) throws ProtocolWorkerException {
		try {
			Room room = SharedInformation.getInstance().getRoom(roomName);
			Client client = SharedInformation.getInstance().getClient(clientId, room);

			GetQuestionTask task = new GetQuestionTask(SharedInformation.getInstance());
			task.setRoom(room);
			task.setClient(client);

			JPPFJob job = createJob("GetQuestion", task);
			executeBlockingJob(job);

			ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objectOutputStream.writeObject(objectToSend);
			objectOutputStream.flush();
		} catch (Exception ex) {
			throw new ProtocolWorkerException("Could not get question from server.");
		} finally {
			objectToSend = null;
		}
	}

	private void score(String roomName, Socket socket) throws ProtocolWorkerException {
		try {
			Room room = SharedInformation.getInstance().getRoom(roomName);

			GenerateScoreTask task = new GenerateScoreTask(SharedInformation.getInstance());
			task.setRoom(room);

			JPPFJob job = createJob("Getting score from room " + room, task);
			executeBlockingJob(job);

			ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objectOutputStream.writeObject(objectToSend);
			objectOutputStream.flush();
		} catch (IOException ex) {
			throw new ProtocolWorkerException("Could not get score for room " + roomName);
		} catch (Exception ex) {
			throw new ProtocolWorkerException("Could not get score for room " + roomName);
		} finally {
			objectToSend = null;
		}
	}

	private void send(String clientId, String roomName, String questionId, String answer, Socket socket)
			throws ProtocolWorkerException {
		try {
			Room room = SharedInformation.getInstance().getRoom(roomName);
			Client client = SharedInformation.getInstance().getClient(clientId, room);

			Question question = findQuestionById(Long.valueOf(questionId));
			ProcessAnswerTask task = new ProcessAnswerTask(SharedInformation.getInstance());
			task.setClient(client);
			task.setRoom(room);
			task.setQuestion(question);
			task.setUserAnswer(QuestionOption.getByIdentifierNumber(Integer.valueOf(answer)));

			JPPFJob job = createJob("ProcessAnswer", task);
			executeBlockingJob(job);

			writer.println(question.getAnswer().getIdentifierNumber());
			writer.flush();
		} catch (Exception ex) {
			throw new ProtocolWorkerException("Could not send answer.");
		}

	}

	private void disconnect(String clientId, String roomName) {
		System.out.println("Disconnecting client");
		Room r = new Room();
		r.setName(roomName);

		Client c = SharedInformation.getInstance().getClient(clientId, r);
		c.setConnected(Boolean.FALSE);
		writer.close();
		reader.close();
	}

	private Question findQuestionById(Long questionId) {
		for (Question q : SharedInformation.getInstance().getQuestions()) {
			if (q.getId().equals(questionId)) {
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
			if (o instanceof ReturnResultTask) {
				ReturnResultTask returnResultTask = (ReturnResultTask) o;
				SharedInformation.changeInstance(returnResultTask.getSharedInformation());
				objectToSend = returnResultTask.getResultObject();
			}
		}
	}
}