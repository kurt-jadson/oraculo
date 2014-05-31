package br.com.oraculo.exceptions;

/**
 *
 * @author kurt
 */
public abstract class ServerException extends Exception {

	public ServerException() {

	}

	public ServerException(String message) {

	}

	public abstract int getErrorId();
	
}
