package br.com.oraculo.exceptions;

/**
 *
 * @author kurt
 */
public class ClientSideException extends Exception {

	public ClientSideException() {
		super("An error occur.");
	}

	public ClientSideException(String message) {
		super(message);
	}
	
}
