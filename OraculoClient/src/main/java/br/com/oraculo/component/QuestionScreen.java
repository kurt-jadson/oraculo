package br.com.oraculo.component;

import br.com.oraculo.listeners.OptionClickListener;
import br.com.oraculo.models.Question;
import br.com.oraculo.models.QuestionOption;
import java.awt.Color;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.jdesktop.swingx.JXLabel;

/**
 *
 * @author kurt
 */
public class QuestionScreen extends JPanel {

	private QuestionOption selected = QuestionOption.NONE;
	private Question question;
	private JPanel questionAreaPanel;
	private JButton[] alternatives;
	private ActionListener optionClickListener;
	private int height;
	private int width;
	private boolean confirmSent;

	public QuestionScreen(int width, int height) {
		optionClickListener = new OptionClickListener(this);
		this.height = height;
		this.width = width;
		drawQuestionArea();
		drawOptions();
		setBounds(0, 0, width, height);
	}

	private void drawQuestionArea() {
		questionAreaPanel = new JPanel();
		questionAreaPanel.setBounds(20, 20, width - 20, height - 346);
		add(questionAreaPanel);
	}

	private void drawOptions() {
		QuestionOption[] options = QuestionOption.values();
		alternatives = new JButton[options.length - 1];
		int questionArea = height - 346;

		for(int i = 0, j = options.length - 1; i < j; i++) {
			QuestionOption option = options[i];

			JButton optionButton = new JButton();
			optionButton.setBackground(new Color(123, 123, 123));
			optionButton.setBounds(10, 20 + 64*i + questionArea, width - 20, 64);
			optionButton.addActionListener(optionClickListener);
			optionButton.setActionCommand(Integer.valueOf(option.getIdentifierNumber()).toString());
			add(optionButton);
			alternatives[i] = optionButton;
		}
	}

	public void setQuestion(Question question) {
		this.question = question;

		JXLabel labelQuestion = new JXLabel(question.getQuestion());
		labelQuestion.setLineWrap(true);
		labelQuestion.setBounds(20, 0, questionAreaPanel.getWidth() - 10, 
				questionAreaPanel.getHeight() - 10);
		questionAreaPanel.add(labelQuestion);
		labelQuestion.setAlignmentX(CENTER_ALIGNMENT);
		labelQuestion.setAlignmentY(CENTER_ALIGNMENT);
		alternatives[0].setText("<html>" + question.getAlternativeA() + "</html>");
		alternatives[1].setText("<html>" + question.getAlternativeB() + "</html>");
		alternatives[2].setText("<html>" + question.getAlternativeC() + "</html>");
		alternatives[3].setText("<html>" + question.getAlternativeD() + "</html>");
	}

	public Question getQuestion() {
		return question;
	}

	public QuestionOption getSelected() {
		return selected;
	}

	public void setSelected(QuestionOption selected) {
		this.selected = selected;

		for(int i = 0, j = alternatives.length; i < j; i++) {
			if(selected.getIdentifierNumber() == i + 1) {
				alternatives[i].setBackground(new Color(207, 157, 6));
			} else {
				alternatives[i].setBackground(new Color(123, 123, 123));
			}
		}
	}

	public void setDisabledOptions() {
		for(JButton b : alternatives) {
			b.setEnabled(false);
		}
	}

	public void setEnabledOptions() {
		for(JButton b : alternatives) {
			b.setEnabled(true);
		}
	}

	public void setCorrectAnswer(QuestionOption answer) {
		alternatives[answer.getIdentifierNumber() - 1].setBackground(new Color(7, 124, 40));

		if(!answer.equals(selected) && !QuestionOption.NONE.equals(selected)) {
			alternatives[selected.getIdentifierNumber() - 1].setBackground(new Color(217, 0, 0));
		}
	}

	public void recalculateSizes(int width, int height) {
		try {
			int heightQuestionArea = height - 346;
			int widthQuestionArea = width - 20;
			setBounds(0, 0, width, height);
			questionAreaPanel.setBounds(0, 0, widthQuestionArea, heightQuestionArea);
			
			if(questionAreaPanel.getComponentCount() > 0) {
				JXLabel label = (JXLabel) questionAreaPanel.getComponent(0);
				label.setBounds(20, 0,
						widthQuestionArea - 10, heightQuestionArea - 10);
				label.setAlignmentX(CENTER_ALIGNMENT);
				label.setAlignmentY(CENTER_ALIGNMENT);
			}
			
			int i = 0;
			for(JButton b : alternatives) {
				b.setBounds(10, 20 + 64*i++ + heightQuestionArea, widthQuestionArea, 64);
			}
		} catch(Exception ex) {
			//don't stop application on resize components in window	
		}
	}

	public void confirmSent() {
		confirmSent = true;
	}

	public boolean wasConfirmSent() {
		return confirmSent;
	}

}