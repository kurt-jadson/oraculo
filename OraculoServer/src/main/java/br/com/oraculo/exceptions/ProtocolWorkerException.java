package br.com.oraculo.exceptions;

import br.com.oraculo.models.ApplicationError;

/**
 *
 * @author kurt
 */
public class ProtocolWorkerException extends ServerException {

	public ProtocolWorkerException() {
		super("An error gived in execution of protocol rule.");
	}

	public ProtocolWorkerException(String message) {
		super(message);
	}

	@Override
	public int getErrorId() {
		return ApplicationError.GENERIC_PROTOCOL_ERROR.getErrorNumber();
	}
	
}
