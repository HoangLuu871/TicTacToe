/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.tictactoe;

import java.net.ConnectException;

/**
 *
 * @author son
 */
public class Game {

    private int port;
    private String hostIP;
    public Player player;

    Game(int port, String hostIP) {
        this.port = port;
        this.hostIP = hostIP;
    }

    ;
    
    public static void main(String args[]) throws InterruptedException {
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
//        Server gameServer = new Server("localhost", 3333);
//        gameServer.start();
        /* Create and display the form */
        Game game = new Game(3333, "localhost");

        game.player = new Player(game.port, game.hostIP);

        game.player.start();

//        if (!game.player.isConnect) {
//            return;
//        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UIGame(game.player).setVisible(true);
            }
        });
    }
}
