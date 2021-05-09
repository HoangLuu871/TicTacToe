package myproject.tictactoe;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author HLuu
 */
public class UIGame extends javax.swing.JFrame implements ActionListener{
    public static TicTacToe game = new TicTacToe();
    
    //action when play
    public void actionPerformed(ActionEvent e) {
        game.COUNT = 1;
        //if turn % 2 == 0 turn X, else turn O)//if you want to swap turn, reverse it
        for (int i = 0; i < game.height; i++) {
            for (int j = 0; j < game.width; j++) {
                if (e.getSource() == game.box[i][j] 
                        && game.box[i][j].getText() != "X" 
                        && game.box[i][j].getText() != "O") {
                    if (game.turn %2 == 0) {
                        game.box[i][j].setText("X");
                        game.box[i][j].setFont(new java.awt.Font("Stabillo Medium", 1, 23));
                        game.box[i][j].setForeground(Color.red);
                        game.turn++;
                        if (game.checkWin(i, j, game.box[i][j].getText())) {
                            JOptionPane.showMessageDialog(null, "X win! Restart?", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                            game.turn = 0;
                            for (int i1 = 0; i1 < game.height; i1++) {
                                for (int j1 = 0; j1 < game.width; j1++) {
                                    game.box[i1][j1].setText("");
                                    game.box[i1][j1].setBackground(Color.white);
                                }
                            }
                        }
                    } else {
                        game.box[i][j].setText("O");
                        game.box[i][j].setFont(new java.awt.Font("Stabillo Medium", 1, 23));
                        game.box[i][j].setForeground(Color.green);
                        game.turn++;
                        if (game.checkWin(i, j, game.box[i][j].getText())) {
                            JOptionPane.showMessageDialog(null, "O win! Restart?", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                            game.turn = 0;
                            for (int i1 = 0; i1 < game.height; i1++) {
                                for (int j1 = 0; j1 < game.width; j1++) {
                                    game.box[i1][j1].setText("");
                                    game.box[i1][j1].setBackground(Color.white);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    public UIGame() {
        initComponents();
        for (int i = 0; i < game.height; i++) {
            for (int j = 0; j < game.width; j++) {
                game.box[i][j] = new JButton();
                game.box[i][j].setBackground(Color.white);
                game.box[i][j].setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
                game.box[i][j].addActionListener(this);
                gamePanel.add(game.box[i][j]);
            }
        }
        setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        gamePanel = new javax.swing.JPanel();
        chatFeildScroll = new javax.swing.JScrollPane();
        chatField = new javax.swing.JTextArea();
        typeBox = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tic Tac Toe");
        setPreferredSize(new java.awt.Dimension(1200, 700));
        setResizable(false);
        setSize(new java.awt.Dimension(1200, 700));
        getContentPane().setLayout(null);

        gamePanel.setBackground(new java.awt.Color(204, 0, 204));
        gamePanel.setPreferredSize(new java.awt.Dimension(600, 600));
        gamePanel.setLayout(new java.awt.GridLayout(game.height, game.width, -4, -4));
        getContentPane().add(gamePanel);
        gamePanel.setBounds(70, 40, 600, 600);

        chatFeildScroll.setPreferredSize(new java.awt.Dimension(450, 450));

        chatField.setEditable(false);
        chatField.setColumns(20);
        chatField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        chatField.setLineWrap(true);
        chatField.setRows(5);
        chatField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        chatField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        chatField.setFocusable(false);
        chatField.setSelectionColor(new java.awt.Color(255, 255, 255));
        chatFeildScroll.setViewportView(chatField);

        getContentPane().add(chatFeildScroll);
        chatFeildScroll.setBounds(710, 380, 450, 200);

        typeBox.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        typeBox.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        typeBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        typeBox.setSelectionColor(new java.awt.Color(0, 0, 0));
        typeBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                typeBoxKeyPressed(evt);
            }
        });
        getContentPane().add(typeBox);
        typeBox.setBounds(710, 590, 450, 40);

        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\Admin\\OneDrive\\Documents\\NetBeansProjects\\TicTacToe\\background.png")); // NOI18N
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, -10, 1210, 720);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //press enter in type box to chat
    private void typeBoxKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_typeBoxKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            game.chatString += (typeBox.getText() + "\n"); //set new chat string
            chatField.setText(game.chatString); //show chat dialog to chatField
            typeBox.setText("");
        }
    }//GEN-LAST:event_typeBoxKeyPressed

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
            java.util.logging.Logger.getLogger(UIGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UIGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UIGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UIGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UIGame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane chatFeildScroll;
    private javax.swing.JTextArea chatField;
    private javax.swing.JPanel gamePanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField typeBox;
    // End of variables declaration//GEN-END:variables
}
