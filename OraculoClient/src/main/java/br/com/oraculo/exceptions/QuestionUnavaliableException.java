package br.com.oraculo.exceptions;

/**
 *
 * @author kurt
 */
public class QuestionUnavaliableException extends ClientSideException {

	public QuestionUnavaliableException() {
		super("Não há mais questões disponíveis para esta sala.");
	}
	
}
