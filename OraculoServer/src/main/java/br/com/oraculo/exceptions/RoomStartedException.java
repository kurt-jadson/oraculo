package br.com.oraculo.exceptions;

/**
 *
 * @author kurt
 */
public class RoomStartedException extends Exception {

	public RoomStartedException() {
		super("The room already initialized.");
	}
	
}
