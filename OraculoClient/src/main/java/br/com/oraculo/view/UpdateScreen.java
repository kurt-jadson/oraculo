package br.com.oraculo.view;

/**
 *
 * @author kurt
 */
public class UpdateScreen implements Runnable {

	private Main main;

	public UpdateScreen(Main main) {
		this.main = main;
	}

	public void run() {
		while(true) {
			main.getLbTime().update(main.getLbTime().getGraphics());
			if(main.getQuestionScreen() != null) {
				main.getQuestionScreen().recalculateSizes(main.getShapeQuestion().getWidth(),
						main.getShapeQuestion().getHeight());
			}
		}
	}
	
}
