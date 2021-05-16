/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.tictactoe;

import javax.swing.*;

/**
 * @author HLuu
 */
public class UIConnect extends javax.swing.JFrame {

    public UIMenu menu = new UIMenu();

    /**
     * Creates new form UIConnect
     */
    public UIConnect() {
        initComponents();
        setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        inputIP = new javax.swing.JTextField();
        inputPort = new javax.swing.JTextField();
        ip = new javax.swing.JTextField();
        port = new javax.swing.JTextField();
        connectButton = new javax.swing.JButton();
        logo = new javax.swing.JLabel();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tic Tac Toe");
        setPreferredSize(new java.awt.Dimension(1200, 700));
        setResizable(false);
        setSize(new java.awt.Dimension(1200, 700));
        getContentPane().setLayout(null);

        inputIP.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        inputIP.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 5));
        getContentPane().add(inputIP);
        inputIP.setBounds(480, 500, 240, 30);

        inputPort.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        inputPort.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 5));
        getContentPane().add(inputPort);
        inputPort.setBounds(480, 460, 240, 30);

        ip.setEditable(false);
        ip.setBackground(new java.awt.Color(255, 255, 255));
        ip.setFont(new java.awt.Font("Action Man Shaded", 1, 16)); // NOI18N
        ip.setText("IP:");
        ip.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 5));
        getContentPane().add(ip);
        ip.setBounds(430, 500, 40, 30);

        port.setEditable(false);
        port.setBackground(new java.awt.Color(255, 255, 255));
        port.setFont(new java.awt.Font("Action Man Shaded", 1, 16)); // NOI18N
        port.setText("port:");
        port.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 5));
        getContentPane().add(port);
        port.setBounds(410, 460, 60, 30);

        connectButton.setBackground(new java.awt.Color(255, 255, 255));
        connectButton.setFont(new java.awt.Font("Action Man Shaded", 1, 24)); // NOI18N
        connectButton.setText("Connect");
        connectButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                connectButtonMousePressed(evt);
            }
        });
        getContentPane().add(connectButton);
        connectButton.setBounds(525, 590, 150, 50);

        logo.setIcon(new javax.swing.ImageIcon("logo tictactoe(edited).png")); // NOI18N
        getContentPane().add(logo);
        logo.setBounds(480, 30, 260, 350);

        background.setIcon(new javax.swing.ImageIcon("background.png")); // NOI18N
        getContentPane().add(background);
        background.setBounds(0, -10, 1210, 720);

        pack();
    }// </editor-fold>

    private void connectButtonMousePressed(java.awt.event.MouseEvent evt) {
        if (inputPort.getText().equals("") || inputIP.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "You need to enter port and IP", "Invalid connect", JOptionPane.INFORMATION_MESSAGE);
            inputPort.setText("");
            inputIP.setText("");
        } else {
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    menu = new UIMenu();
                    menu.setVisible(true);
                    menu.game = new Game(Integer.parseInt(inputPort.getText().strip()), inputIP.getText());
                    menu.game.player = new Player(menu.game.getPort(), menu.game.getHostIP());
                    menu.game.player.start();

                }
            });
            setVisible(false);
        }
    }

    // Variables declaration - do not modify
    private javax.swing.JLabel background;
    private javax.swing.JButton connectButton;
    private javax.swing.JTextField inputIP;
    private javax.swing.JTextField inputPort;
    private javax.swing.JTextField ip;
    private javax.swing.JLabel logo;
    private javax.swing.JTextField port;
    // End of variables declaration
}
