package br.com.oraculo.exceptions;

/**
 *
 * @author kurt
 */
public class BusyRoomException extends ClientSideException {

	public BusyRoomException() {
		super("This room is busy.");
	}
	
}
