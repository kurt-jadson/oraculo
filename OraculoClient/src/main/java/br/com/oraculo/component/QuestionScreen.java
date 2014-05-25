package br.com.oraculo.component;

import br.com.oraculo.enums.QuestionOption;
import br.com.oraculo.listeners.OptionClickListener;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author kurt
 */
public class QuestionScreen extends JPanel {

	private QuestionOption selected = QuestionOption.NONE;
	private ActionListener optionClickListener;
	private int size;

	public QuestionScreen(int height) {
		optionClickListener = new OptionClickListener(this);
		this.size = height;
		drawQuestionArea();
		drawOptions();
		setBounds(0, 0, 250, height);
	}

	private void drawQuestionArea() {
		JPanel questionAreaPanel = new JPanel();
		EtchedBorder border = new EtchedBorder(EtchedBorder.RAISED);
		questionAreaPanel.setBounds(10, 10, 250, size - 346);
		questionAreaPanel.setBorder(border);
		add(questionAreaPanel);
	}

	private void drawOptions() {
		QuestionOption[] options = QuestionOption.values();
		int questionArea = size - 346;

		for(int i = 0, j = options.length - 1; i < j; i++) {
			QuestionOption option = options[i];

			JButton optionButton = new JButton();
			optionButton.setBounds(10, 20 + 64*i + questionArea, 100, 64);
			optionButton.addActionListener(optionClickListener);
			optionButton.setActionCommand(Integer.valueOf(option.getIdentifierNumber()).toString());
			add(optionButton);
		}
	}

	public QuestionOption getSelected() {
		return selected;
	}

	public void setSelected(QuestionOption selected) {
		this.selected = selected;
	}

}