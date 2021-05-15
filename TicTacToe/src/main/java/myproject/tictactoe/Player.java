/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.tictactoe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author son
 */
public class Player extends Thread {

    private final int port;
    private final String hostIP;
    public final String symbol;
    private OutputStream outputStream;
    private InputStream inputStream;

    public boolean isLogin;
    public final String userName;

    // is game start
    public boolean isStart;
    public String opponentMove;

    public Player(int port, String ip, String symbol) {
        this.port = port;
        this.hostIP = ip;
        this.symbol = symbol;
        this.isLogin = false;
        this.isStart = false;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket(this.hostIP, this.port);
            this.handleServerSocket(socket);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void handleServerSocket(Socket userSocket) throws IOException, InterruptedException {
        inputStream = userSocket.getInputStream();
        outputStream = userSocket.getOutputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        this.login(outputStream, "son", "son");

        String line = "";
        String query = "";
        while ((line = reader.readLine()) != null) {
            String[] tokens = line.split(" ");

            if (tokens != null && tokens.length > 0) {
                query = tokens[0];
                // check login 
                if (query.equalsIgnoreCase("Welcome")) {
                    isLogin = true;
                } else if(query.equalsIgnoreCase("Start")) {
                    isStart = true;
                } 

                // if user login, user can use below commands;
                if (this.isLogin && this.isStart) {
                    if (query.equalsIgnoreCase("move")) {
                        // tokens template: "move text position"
                        this.receiveMove(outputStream, tokens);
                    }else if(query.equalsIgnoreCase("offline")){
                        break;
                    }
                }
            }

        }
        
        inputStream.close();
        outputStream.close();
        userSocket.close();
    }

    public void login(OutputStream outputStream, String user, String password) throws IOException {
        String clientMsg = "login " + user + " " + password + "\n";
        outputStream.write(clientMsg.getBytes());
    }
    
    public void logoff(OutputStream outputStream) throws IOException{
        String clientMsg = "logout";
        outputStream.write(clientMsg.getBytes());
        isLogin = false;
    }

    public void receiveMove(OutputStream outputStream, String[] tokens) {
        if (tokens.length == 3) {
//            String opponentName = tokens[1];
            opponentMove = tokens[2];
        }
    }
    
    public void makeMove(OutputStream outputStream, String move) throws IOException {
        if(outputStream == null) return;
        
        String clientMsg = "move " + this.userName + " " + move;
        outputStream.write(clientMsg.getBytes());
    }
    

}
