package br.com.oraculo.view;

import br.com.oraculo.component.QuestionScreen;
import br.com.oraculo.component.ScoreUserInfo;
import br.com.oraculo.controller.SocketController;
import br.com.oraculo.exceptions.ClientSideException;
import br.com.oraculo.exceptions.CommunicationException;
import br.com.oraculo.exceptions.SuccessException;
import br.com.oraculo.models.Question;
import br.com.oraculo.models.QuestionOption;
import br.com.oraculo.models.Score;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author kurt
 */
public class Main extends javax.swing.JFrame {

	private SocketController socketController;
	private QuestionScreen questionScreen;

	/**
	 * Creates new form Main
	 */
	public Main() {
		initComponents();
		socketController = new SocketController();
		new Thread(new UpdateScreen(this)).start();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        shape = new javax.swing.JPanel();
        score = new javax.swing.JPanel();
        commandBar = new javax.swing.JPanel();
        lbTime = new javax.swing.JLabel();
        btnNext = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        miConnect = new javax.swing.JMenuItem();
        miDisconnect = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        miExit = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        miSettings = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        shape.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout shapeLayout = new javax.swing.GroupLayout(shape);
        shape.setLayout(shapeLayout);
        shapeLayout.setHorizontalGroup(
            shapeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        shapeLayout.setVerticalGroup(
            shapeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 445, Short.MAX_VALUE)
        );

        score.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout scoreLayout = new javax.swing.GroupLayout(score);
        score.setLayout(scoreLayout);
        scoreLayout.setHorizontalGroup(
            scoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 130, Short.MAX_VALUE)
        );
        scoreLayout.setVerticalGroup(
            scoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        lbTime.setText("00");

        btnNext.setText("Iniciar");
        btnNext.setEnabled(false);
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout commandBarLayout = new javax.swing.GroupLayout(commandBar);
        commandBar.setLayout(commandBarLayout);
        commandBarLayout.setHorizontalGroup(
            commandBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, commandBarLayout.createSequentialGroup()
                .addComponent(lbTime)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 261, Short.MAX_VALUE)
                .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        commandBarLayout.setVerticalGroup(
            commandBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(commandBarLayout.createSequentialGroup()
                .addGroup(commandBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNext)
                    .addComponent(lbTime))
                .addGap(0, 6, Short.MAX_VALUE))
        );

        jMenu1.setText("Arquivo");

        miConnect.setText("Conectar");
        miConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miConnectActionPerformed(evt);
            }
        });
        jMenu1.add(miConnect);

        miDisconnect.setText("Desconectar");
        miDisconnect.setEnabled(false);
        miDisconnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miDisconnectActionPerformed(evt);
            }
        });
        jMenu1.add(miDisconnect);
        jMenu1.add(jSeparator1);

        miExit.setText("Sair");
        jMenu1.add(miExit);

        menuBar.add(jMenu1);

        jMenu2.setText("Opções");

        miSettings.setText("Preferências");
        jMenu2.add(miSettings);

        menuBar.add(jMenu2);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(shape, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(commandBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(score, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(score, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(shape, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(commandBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
		try {
			if ("Iniciar".equals(evt.getActionCommand())) {
				socketController.start();
				socketController.verify(0l);
				requestQuestion();
				btnNext.setText("Confirmar");
			} else if ("Confirmar".equals(evt.getActionCommand())) {
				request(questionScreen.getSelected());
				btnNext.setText("Continuar");
			} else if ("Prosseguir".equals(evt.getActionCommand())) {
				request(questionScreen.getSelected());
				btnNext.setText("Confirmar");
			} else if ("Continuar".equals(evt.getActionCommand())) {
				request(null);
				btnNext.setText("Confirmar");
			}

		} catch (CommunicationException ce) {
			JOptionPane.showMessageDialog(null, ce.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
		}
    }//GEN-LAST:event_btnNextActionPerformed

	private void request(QuestionOption response) throws CommunicationException {
		Long questionId = questionScreen.getQuestion().getId();

		if (response != null) {
			//Send answer to server and receive correct answer from.
			questionScreen.confirmSent();
			QuestionOption correct = socketController.send(questionId, response);
			questionScreen.setCorrectAnswer(correct);
		} else {
			socketController.verify(questionId);

			//Request room's score
			List<Score> scores = socketController.score();
			score.removeAll();
			ScoreUserInfo.resetCountUsers();
			for (Score s : scores) {
				ScoreUserInfo sui = new ScoreUserInfo(s.getClient().getNickname(), s.getScore());
				score.add(sui);
				sui.update(sui.getGraphics());
			}
			score.update(score.getGraphics());

			requestQuestion();
		}
	}

	private void requestQuestion() throws CommunicationException {
		Question question = socketController.get();
		questionScreen = new QuestionScreen(shape.getWidth(), shape.getHeight());
		questionScreen.setQuestion(question);
		shape.removeAll();
		shape.add(questionScreen);
		shape.update(shape.getGraphics());
		new Thread(new TimeoutRequester(this, question.getTimeToAnswer())).start();
	}

    private void miConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miConnectActionPerformed
		try {
			socketController.connect("127.0.0.1", 7777, "sala01", "jadson");
		} catch (SuccessException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Success!", JOptionPane.INFORMATION_MESSAGE);
			btnNext.setEnabled(true);
			miConnect.setEnabled(false);
			miDisconnect.setEnabled(true);
		} catch (ClientSideException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
		}
    }//GEN-LAST:event_miConnectActionPerformed

    private void miDisconnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miDisconnectActionPerformed
		socketController.disconnect();
		miConnect.setEnabled(true);
		miDisconnect.setEnabled(false);
    }//GEN-LAST:event_miDisconnectActionPerformed

	public SocketController getSocketController() {
		return socketController;
	}

	public QuestionScreen getQuestionScreen() {
		return questionScreen;
	}

	public JButton getBtnNext() {
		return btnNext;
	}

	public JLabel getLbTime() {
		return lbTime;
	}

	public JPanel getShapeQuestion() {
		return shape;
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
		 * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		//</editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Main().setVisible(true);
			}
		});
	}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNext;
    private javax.swing.JPanel commandBar;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JLabel lbTime;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem miConnect;
    private javax.swing.JMenuItem miDisconnect;
    private javax.swing.JMenuItem miExit;
    private javax.swing.JMenuItem miSettings;
    private javax.swing.JPanel score;
    private javax.swing.JPanel shape;
    // End of variables declaration//GEN-END:variables
}
