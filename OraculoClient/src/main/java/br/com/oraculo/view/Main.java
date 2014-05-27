package br.com.oraculo.view;

import br.com.oraculo.component.QuestionScreen;
import br.com.oraculo.component.ScoreUserInfo;
import br.com.oraculo.controller.SocketController;
import br.com.oraculo.exceptions.CommunicationException;
import br.com.oraculo.exceptions.UnconnectException;
import br.com.oraculo.models.Question;
import br.com.oraculo.models.QuestionOption;
import br.com.oraculo.models.Score;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author kurt
 */
public class Main extends javax.swing.JFrame {

	private SocketController socketController;

	/**
	 * Creates new form Main
	 */
	public Main() {
		initComponents();
		socketController = new SocketController();
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
        jLabel1 = new javax.swing.JLabel();
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
            .addGap(0, 427, Short.MAX_VALUE)
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

        jLabel1.setText("Tempo");

        btnNext.setText("Iniciar");
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
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 260, Short.MAX_VALUE)
                .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        commandBarLayout.setVerticalGroup(
            commandBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(commandBarLayout.createSequentialGroup()
                .addGroup(commandBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNext)
                    .addComponent(jLabel1))
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
			//Starting
			socketController.start();

			//Verify Test
			socketController.verify();

			//Get Question Test
			Question question = socketController.get();
			System.out.println(question);

			//Answer Question Test
			QuestionOption userAnswer = QuestionOption.A;
			QuestionOption serverAnswer = socketController.send(question.getId(), userAnswer);
			System.out.println("userAnswer: " + userAnswer);
			System.out.println("serverAnswer: " + serverAnswer);
			System.out.println(userAnswer.equals(serverAnswer));

			//Score test
			List<Score> scores = socketController.score();
			for (Score s : scores) {
				score.add(new ScoreUserInfo(s.getClient().getNickname(), s.getScore()));
			}

			score.update(score.getGraphics());
			shape.add(new QuestionScreen(500));
			shape.update(shape.getGraphics());
		} catch (CommunicationException ce) {
			JOptionPane.showMessageDialog(null, ce.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
		}
    }//GEN-LAST:event_btnNextActionPerformed

    private void miConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miConnectActionPerformed
		try {
			socketController.connect("127.0.0.1", 7777, "sala01", "jadson");
		} catch (UnconnectException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
		}
    }//GEN-LAST:event_miConnectActionPerformed

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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem miConnect;
    private javax.swing.JMenuItem miDisconnect;
    private javax.swing.JMenuItem miExit;
    private javax.swing.JMenuItem miSettings;
    private javax.swing.JPanel score;
    private javax.swing.JPanel shape;
    // End of variables declaration//GEN-END:variables
}
