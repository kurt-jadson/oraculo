package br.com.oraculo.component;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author kurt
 */
public class ScoreUserInfo extends JPanel {

	private String username;
	private Integer score;
	private static int countUsers;

	private final int HEIGHT = 32;
	private final int WIDTH = 115;
	private final int VERTICAL_PADDING = 10;
	private final int HORIZONTAL_PADDING = 10;

	public ScoreUserInfo() {
		this(null, Integer.valueOf("0"));
	}

	public ScoreUserInfo(String username, Integer score) {
		super(new FlowLayout());
		this.username = username;
		this.score = score;

		setBackground(new Color(123, 123, 123));

		JLabel usernameLabel = new JLabel(username);
		JLabel scoreLabel = new JLabel(score.toString());
		add(usernameLabel);
		add(scoreLabel);

		usernameLabel.setBounds(usernameLabel.getX(), usernameLabel.getY(), 
				85, usernameLabel.getHeight());
		scoreLabel.setBounds(scoreLabel.getX(), scoreLabel.getY(), 40, scoreLabel.getHeight());

		EtchedBorder border = new EtchedBorder(EtchedBorder.LOWERED);
		int yPosition = VERTICAL_PADDING + countUsers * HEIGHT;
		setBounds(HORIZONTAL_PADDING, yPosition, WIDTH, HEIGHT);
		setBorder(border);
		countUsers++;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public static void resetCountUsers() {
		countUsers = 0;
	}

	@Override
	public void update(Graphics graphics) {
		JLabel usernameLabel = (JLabel) getComponent(0);
		JLabel scoreLabel = (JLabel) getComponent(1);
		usernameLabel.setText(username);
		scoreLabel.setText(score.toString());

		usernameLabel.update(usernameLabel.getGraphics());
		scoreLabel.update(scoreLabel.getGraphics());
		super.update(getGraphics());
	}

}