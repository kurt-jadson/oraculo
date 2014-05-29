package br.com.oraculo.tasks;

import br.com.oraculo.server.SharedInformation;
import org.jppf.node.protocol.AbstractTask;

/**
 *
 * @author kurt
 */
public abstract class ReturnResultTask extends AbstractTask<String> {

	private final SharedInformation sharedInformation;

	public ReturnResultTask(SharedInformation sharedInformation) {
		this.sharedInformation = sharedInformation;
	}

	public SharedInformation getSharedInformation() {
		return this.sharedInformation;
	}

	public abstract Object getResultObject();
}