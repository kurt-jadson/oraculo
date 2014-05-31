package br.com.oraculo.exceptions;

import br.com.oraculo.models.ApplicationError;

/**
 *
 * @author kurt
 */
public class ServerException extends Exception {

	private int errorNumber;

	public ServerException(int errorNumber) {
		this.errorNumber = errorNumber;
	}

	public ApplicationError getApplicationError() {
		return ApplicationError.getApplicationError(errorNumber);
	}
	
}
