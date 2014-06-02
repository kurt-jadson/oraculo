package br.com.oraculo.view;

import br.com.oraculo.models.Question;
import br.com.oraculo.models.QuestionOption;

/**
 *
 * @author kurt
 */
public class TimeoutRequester implements Runnable {

	private Main main;
	private int timeToAnswer;

	public TimeoutRequester(Main main, int timeToAnswer) {
		this.main = main;
		this.timeToAnswer = timeToAnswer * 1000;
	}

	public void run() {
		long timeStart = System.currentTimeMillis();
		long timeElapsed = System.currentTimeMillis() - timeStart;

		while(timeElapsed < timeToAnswer && !main.getQuestionScreen().wasConfirmSent()) {
			timeElapsed = System.currentTimeMillis() - timeStart;
			Long counter = (timeToAnswer - timeElapsed) / 1000;
			main.getLbTime().setText(Integer.valueOf(counter.intValue()).toString());
			main.getLbTime().update(main.getLbTime().getGraphics());
		}

		if(!main.getQuestionScreen().wasConfirmSent()) {
			main.getBtnNext().setText("Prosseguir");
		}

		main.getTimeoutRequestThread().stop();
	}
	
}
