package br.com.oraculo.component;

import java.awt.Component;
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
		this.username = username;
		this.score = score;

		JLabel usernameLabel = new JLabel(username);
		JLabel scoreLabel = new JLabel(score.toString());
		add(usernameLabel);
		add(scoreLabel);

		EtchedBorder border = new EtchedBorder(EtchedBorder.RAISED);
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
		for(Component c : getComponents()) {
			c.update(c.getGraphics());
		}
		super.update(graphics);
	}

}