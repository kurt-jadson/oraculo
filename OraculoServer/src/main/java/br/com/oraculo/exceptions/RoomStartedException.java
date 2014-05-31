package br.com.oraculo.exceptions;

import br.com.oraculo.models.ApplicationError;

/**
 *
 * @author kurt
 */
public class RoomStartedException extends ServerException {

	public RoomStartedException() {
		super("The room already initialized.");
	}

	@Override
	public int getErrorId() {
		return ApplicationError.BUSY_ROOM_ERROR.getErrorNumber();
	}
	
}
