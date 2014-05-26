package br.com.oraculo.server;

import br.com.oraculo.exceptions.ProtocolWorkerException;
import br.com.oraculo.tasks.AddInRoomTask;
import br.com.oraculo.tasks.GenerateScoreTask;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
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

	public ProtocolWorker(SharedInformation sharedInformation) {
		this.sharedInformation = sharedInformation;
	}

	public void execute(String command, String clientId, Socket socket, String... parameters)
			throws ProtocolWorkerException {

		if ("connect".equals(command)) {
			connect(clientId, parameters[0], parameters[1]);
		} else if ("score".equals(command)) {
			score(parameters[0], socket);
		}

	}

	private void connect(String clientId, String room, String nickname) throws ProtocolWorkerException {
		try {
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

	private JPPFJob createJob(String jobName, Task task) throws JPPFException {
		JPPFJob job = new JPPFJob();
		job.setName(jobName);
		job.addTask(task);
		return job;
	}

	private void executeBlockingJob(final JPPFJob job) throws Exception {
		job.setBlocking(true);
		List<JPPFTask> results = sharedInformation.getJppfClient().submit(job);
		processExecutionResults(results);
	}

	private void processExecutionResults(final List<JPPFTask> results) {
		for (JPPFTask task : results) {
			if (task.getException() != null) {
				System.out.println("Exc: " + task.getException());
				System.out.println("Tomou uma exceção ...");
			}

			Object o = task.getTaskObject();
			if(o instanceof AddInRoomTask) {
				AddInRoomTask addInRoomTask = (AddInRoomTask) o;
				sharedInformation = addInRoomTask.getSharedInformation();
			} else if(o instanceof GenerateScoreTask) {
				GenerateScoreTask generateScoreTask = (GenerateScoreTask) o;
				objectToSend = generateScoreTask.getScores();
			}
		}
	}
}