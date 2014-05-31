package br.com.oraculo.controller;

import br.com.oraculo.exceptions.BusyRoomException;
import br.com.oraculo.exceptions.ClientSideException;
import br.com.oraculo.models.ApplicationError;

/**
 *
 * @author kurt
 */
public class ErrorManager {

	private ApplicationError applicationError;

	public ErrorManager(ApplicationError appErr) {
		applicationError = appErr;
	}

	public void rethrowServerException() throws ClientSideException {
		if(ApplicationError.BUSY_ROOM_ERROR.equals(applicationError)) {
			throw new BusyRoomException();
		}
	}
	
}