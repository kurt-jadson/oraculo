package br.com.oraculo.exceptions;

/**
 *
 * @author kurt
 */
public class CommunicationException extends ClientSideException {

	public CommunicationException() {
		super("Could not communicate to server.");
	}
	
}
