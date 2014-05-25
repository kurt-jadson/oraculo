package br.com.oraculo.listeners;

import br.com.oraculo.component.QuestionScreen;
import br.com.oraculo.enums.QuestionOption;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author kurt
 */
public class OptionClickListener implements ActionListener {

	private QuestionScreen questionScreen;

	public OptionClickListener(QuestionScreen questionScreen) {
		this.questionScreen = questionScreen;
	}

	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		Integer optionIdentifier = Integer.valueOf(actionCommand);
		QuestionOption questionOption = QuestionOption.getByIdentifierNumber(optionIdentifier);
		questionScreen.setSelected(questionOption);
	}


	
}
