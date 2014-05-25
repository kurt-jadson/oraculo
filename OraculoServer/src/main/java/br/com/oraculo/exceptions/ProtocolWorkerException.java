package br.com.oraculo.exceptions;

/**
 *
 * @author kurt
 */
public class ProtocolWorkerException extends Exception {

	public ProtocolWorkerException() {
		super("An error gived in execution of protocol rule.");
	}

	public ProtocolWorkerException(String message) {
		super(message);
	}
	
}
