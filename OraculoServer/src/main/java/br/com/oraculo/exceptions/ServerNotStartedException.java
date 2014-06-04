package br.com.oraculo.exceptions;

/**
 *
 * @author kurt
 */
public class ServerNotStartedException extends Exception {

	public ServerNotStartedException() {
		super("This server could not been started.");
	}

	public ServerNotStartedException(String message) {
		super(message);
	}
	
}
