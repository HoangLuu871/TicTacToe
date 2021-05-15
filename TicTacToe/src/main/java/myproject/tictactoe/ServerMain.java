/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.tictactoe;

import java.io.IOException;

/**
 *
 * @author son
 */
public class ServerMain {

    public static void main(String args[]) throws InterruptedException {
        Server gameServer = new Server("localhost", 3333);
        gameServer.start();

        // check if two user login than start game
        while (gameServer.currentNumPlayer != Server.maxPlayer) {
            System.out.println(gameServer.currentNumPlayer);
            continue;
        }
        
        // sleep 2s waiting for server
        Thread.sleep(1000);
        
        // message trigger start game in user side
        try {
            gameServer.triggerStart();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
