package br.com.oraculo.exceptions;

/**
 *
 * @author kurt
 */
public class NoMoreQuestionsException extends Exception {

	public NoMoreQuestionsException() {
		super("No more questions is avaliable to this room.");
	}
	
}
