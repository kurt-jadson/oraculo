package br.com.oraculo.exceptions;

import br.com.oraculo.models.ApplicationError;

/**
 *
 * @author kurt
 */
public class NoMoreQuestionsException extends ServerException {

	public NoMoreQuestionsException() {
		super("No more questions is avaliable to this room.");
	}

	@Override
	public int getErrorId() {
		return ApplicationError.NO_MORE_QUESTIONS.getErrorNumber();
	}
	
}
