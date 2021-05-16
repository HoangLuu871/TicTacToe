package myproject.tictactoe;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import myproject.tictactoe.Utils.utils;

/**
 * @author HLuu
 */
public class UIGame extends javax.swing.JFrame implements ActionListener {

    public static TicTacToe game = new TicTacToe();
    private final Player player;

    //action when play
    public void actionPerformed(ActionEvent e) {
        game.COUNT = 1;

        // if not player turn than stop function
        if (!player.isTurn) {
            return;
        }

        //if turn % 2 == 0 turn X, else turn O)//if you want to swap turn, reverse it
        for (int i = 0; i < game.height; i++) {
            for (int j = 0; j < game.width; j++) {
                // check object to string because its easy to send into socket
                String coords = utils.getCoords(e.getSource().toString());
                System.out.println(coords);
                if (coords.equals(utils.getCoords(game.box[i][j].toString()))
                        && game.box[i][j].getText() != "X"
                        && game.box[i][j].getText() != "O") {
                    System.out.println(e.getSource());

                    game.box[i][j].setText(this.player.symbol);
                    game.box[i][j].setFont(new java.awt.Font("Stabillo Medium", 1, 23));
                    game.box[i][j].setForeground(this.player.playerColor);
                    game.turn++;

                    try {
                        // broadcast move to other player
                        this.player.makeMove(coords);
                    } catch (IOException ex) {
                        Logger.getLogger(UIGame.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    if (game.checkWin(i, j, game.box[i][j].getText())) {
                        matchResults(this.player.symbol);
                        this.player.isOpponentWin = false;

                        try {
                            this.player.sendWinResult();
                        } catch (IOException ex) {
                            Logger.getLogger(UIGame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                }
            }
        }
    }

    public UIGame(Player player) {
        this.player = player;
        this.player.game = game;
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

    public void matchResults(String symbol) {
        String matchInfo = this.player.symbol.equals(symbol) ? "Winner winner chicken dinner" : "Game Over";
        int option = JOptionPane.showConfirmDialog(null, "Win! Restart?", matchInfo, JOptionPane.YES_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            for (int i1 = 0; i1 < game.height; i1++) {
                for (int j1 = 0; j1 < game.width; j1++) {
                    game.box[i1][j1].setText("");
                    game.box[i1][j1].setBackground(Color.white);
                }
            }
            game.turn = 0;
        } else {
            //logout
        }
        game.clearMap();
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
        logo = new javax.swing.JLabel();
        background = new javax.swing.JLabel();
        logoutButton = new javax.swing.JButton();
        showPlayer = new javax.swing.JTextField();
        restartButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tic Tac Toe");
        setPreferredSize(new java.awt.Dimension(1200, 700));
        setResizable(false);
        setSize(new java.awt.Dimension(1200, 700));
        getContentPane().setLayout(null);

        restartButton.setBackground(new java.awt.Color(255, 255, 255));
        restartButton.setFont(new java.awt.Font("Action Man Shaded", 1, 18)); // NOI18N
        restartButton.setText("Restart");
        restartButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 5));
        restartButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                restartButtonMousePressed(evt);
            }
        });
        getContentPane().add(restartButton);
        restartButton.setBounds(710, 320, 110, 40);

        logoutButton.setBackground(new java.awt.Color(255, 255, 255));
        logoutButton.setFont(new java.awt.Font("Action Man Shaded", 1, 20)); // NOI18N
        logoutButton.setText("Logout");
        logoutButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 5));
        logoutButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                logoutButtonMousePressed(evt);
            }
        });
        getContentPane().add(logoutButton);
        logoutButton.setBounds(1060, 10, 110, 40);

        showPlayer.setEditable(false);
        showPlayer.setFont(new java.awt.Font("Action Man Shaded", 1, 24)); // NOI18N
        showPlayer.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        showPlayer.setText("Username: You are X");
        showPlayer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 5));
        getContentPane().add(showPlayer);
        showPlayer.setBounds(210, 0, 310, 40);

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
        chatField.setBorder(javax.swing.BorderFactory.createLineBorder(null));
        chatField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        chatField.setFocusable(false);
        chatField.setSelectionColor(new java.awt.Color(255, 255, 255));
        chatFeildScroll.setViewportView(chatField);

        getContentPane().add(chatFeildScroll);
        chatFeildScroll.setBounds(710, 380, 450, 200);

        typeBox.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        typeBox.setBorder(javax.swing.BorderFactory.createLineBorder(null));
        typeBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        typeBox.setSelectionColor(new java.awt.Color(0, 0, 0));
        typeBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                typeBoxKeyPressed(evt);
            }
        });
        getContentPane().add(typeBox);
        typeBox.setBounds(710, 590, 450, 40);

        logo.setIcon(new javax.swing.ImageIcon("logo tictactoe(edited).png")); // NOI18N
        getContentPane().add(logo);
        logo.setBounds(810, 10, 250, 350);

        background.setIcon(new javax.swing.ImageIcon("background.png")); // NOI18N
        getContentPane().add(background);
        background.setBounds(0, -10, 1210, 720);


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

    private void logoutButtonMousePressed(java.awt.event.MouseEvent evt) {
        int option = JOptionPane.showConfirmDialog(null, "Do you want to logout?", "Logout", JOptionPane.YES_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            setVisible(false);
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new UIMenu().setVisible(true);
                }
            });
        }
    }

    private void restartButtonMousePressed(java.awt.event.MouseEvent evt) {
        int option = JOptionPane.showConfirmDialog(null, "Do you want to restart?", "Restart", JOptionPane.YES_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            //restart
        }
    }

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(UIGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(UIGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(UIGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(UIGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new UIGame().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel background;
    private javax.swing.JScrollPane chatFeildScroll;
    private javax.swing.JTextArea chatField;
    private javax.swing.JPanel gamePanel;
    private javax.swing.JLabel logo;
    private javax.swing.JTextField typeBox;
    private javax.swing.JTextField showPlayer;
    private javax.swing.JButton logoutButton;
    private javax.swing.JButton restartButton;
    // End of variables declaration//GEN-END:variables
}
