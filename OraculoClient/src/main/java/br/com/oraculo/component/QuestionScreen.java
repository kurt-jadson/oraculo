package br.com.oraculo.component;

import br.com.oraculo.listeners.OptionClickListener;
import br.com.oraculo.models.Question;
import br.com.oraculo.models.QuestionOption;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JViewport;
import javax.swing.ViewportLayout;
import javax.swing.border.EtchedBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;

/**
 *
 * @author kurt
 */
public class QuestionScreen extends JPanel {

	private QuestionOption selected = QuestionOption.NONE;
	private Question question;
	private JViewport questionAreaPanel;
	private JButton[] alternatives;
	private ActionListener optionClickListener;
	private int height;
	private int width;

	public QuestionScreen(int height, int width) {
		optionClickListener = new OptionClickListener(this);
		this.height = height;
		this.width = width;
		drawQuestionArea();
		drawOptions();
		setBounds(0, 0, width, height);
	}

	private void drawQuestionArea() {
		questionAreaPanel = new JViewport();
		questionAreaPanel.setLayout(new ViewportLayout());
		questionAreaPanel.setBounds(10, 10, width - 10, height - 346);
		add(questionAreaPanel);
	}

	private void drawOptions() {
		QuestionOption[] options = QuestionOption.values();
		alternatives = new JButton[options.length];
		int questionArea = height - 346;

		for(int i = 0, j = options.length - 1; i < j; i++) {
			QuestionOption option = options[i];

			JButton optionButton = new JButton();
			optionButton.setBounds(10, 20 + 64*i + questionArea, 100, 64);
			optionButton.addActionListener(optionClickListener);
			optionButton.setActionCommand(Integer.valueOf(option.getIdentifierNumber()).toString());
			add(optionButton);
			alternatives[i] = optionButton;
		}
	}

	public void setQuestion(Question question) {
		this.question = question;

		JLabel labelQuestion = new JLabel(question.getQuestion());
		labelQuestion.setBounds(0, 0, 400, 100);
		questionAreaPanel.add(labelQuestion);
		alternatives[0].setText(question.getAlternativeA());
		alternatives[1].setText(question.getAlternativeB());
		alternatives[2].setText(question.getAlternativeC());
		alternatives[3].setText(question.getAlternativeD());
	}

	public Question getQuestion() {
		return question;
	}

	public QuestionOption getSelected() {
		return selected;
	}

	public void setSelected(QuestionOption selected) {
		this.selected = selected;
	}

}