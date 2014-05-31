package br.com.oraculo.exceptions;

/**
 *
 * @author kurt
 */
public class UnconnectException extends ClientSideException {

	public UnconnectException() {
		super("Cannot connect to server.");
	}

	public UnconnectException(String message) {
		super(message);
	}
	
}