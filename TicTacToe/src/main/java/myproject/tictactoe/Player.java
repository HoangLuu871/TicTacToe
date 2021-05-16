/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.tictactoe;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import static myproject.tictactoe.UIGame.game;
import myproject.tictactoe.Utils.utils;

/**
 *
 * @author son
 */
public class Player extends Thread {

    private final int port;
    private final String hostIP;
    public String symbol;
    private OutputStream outputStream;
    private InputStream inputStream;

    public boolean isLogin;
    public String userName;

    // is game start
    public boolean isConnect;
    public boolean isStart;
    public boolean isTurn;
    public Color playerColor;
    
    // opponent info
    public String opponentMove;
    public String opponentSymbol;
    public Color opponentColor;
    public boolean isOpponentWin;
    
    // game board;
    public TicTacToe game;
    

    public Player(int port, String ip) {
        this.port = port;
        this.hostIP = ip;

        this.isLogin = false;
        this.isStart = false;
        this.isConnect = false;
        
        this.isOpponentWin = false;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket(this.hostIP, this.port); // create client socket
            this.isConnect = true;
            this.handleServerSocket(socket); // receive socket from server and handle
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

//        this.login(outputStream, "son", "son");

        String line = "";
        String query = "";
        
        while ((line = reader.readLine()) != null) {
            String[] tokens = line.split(" ");

            if (tokens != null && tokens.length > 0) {
                query = tokens[0];
                // check login 
                System.out.println("Receive " + tokens[0]);
                if (query.equalsIgnoreCase("Welcome")) {
                    isLogin = true;

                    // init user info 
                    this.userName = tokens[2];
                    this.symbol = tokens[3];
                    this.isTurn = this.symbol.equals("O");
                    
                    this.playerColor = "O".equals(this.symbol) ? Color.red : Color.green;
                    this.opponentColor = "X".equals(this.symbol) ? Color.red : Color.green;
                    
                    this.opponentSymbol = "X".equals(this.symbol) ? "O" : "X";
                } else if (query.equalsIgnoreCase("Start")) {
                    isStart = true; // check is game ready to start?
                }

                System.out.println("USERNAME: " + this.userName);
                // if user login, user can use below commands;
                if (this.isLogin && this.isStart) {
                    if (query.equalsIgnoreCase("move")) {
                        // tokens template: "move text position"
                        this.receiveMove(outputStream, tokens); // receive move from opponent 
                    } else if (query.equalsIgnoreCase("offline")) {
                        break;
                    } else if (query.equalsIgnoreCase("winner")) {
                        this.handleWinResult(outputStream, tokens);
                    }
                }
            }

        }
        System.out.println("enddddddddddd");

        inputStream.close();
        outputStream.close();
        userSocket.close();
    }

    public void login(String user, String password) throws IOException {
        String clientMsg = "login " + user + " " + password + "\n";
        outputStream.write(clientMsg.getBytes());
    }

    public void logoff() throws IOException {
        String clientMsg = "logout\n";
        outputStream.write(clientMsg.getBytes());
        outputStream.flush();
        isLogin = false;
        isStart = false;
    }

    public void receiveMove(OutputStream outputStream, String[] tokens) {
        if (tokens.length == 3) {
//            String opponentName = tokens[1];
            System.out.println("Receive from " + tokens[1]);
            this.opponentMove = tokens[2];
            
            // now is your turn
            this.getOpponentPos(this.game);
        }
    }

    public void makeMove(String move) throws IOException {
        if (this.outputStream == null) {
            return;
        }
        
        // opponent turn
        this.isTurn = false;
        
        System.out.println("MOVEEEEEE!!");
        String clientMsg = "move " + this.userName + " " + move + "\n";
        this.outputStream.write(clientMsg.getBytes());
        this.outputStream.flush();
    }
    
    public void getOpponentPos(TicTacToe game) {
        System.out.println("hellooooooo");
        System.out.println(this.opponentMove);

        for (int i = 0; i < game.height; i++) {
            for (int j = 0; j < game.width; j++) {
                // check object to string because its easy to send into socket
                if (this.opponentMove.equals(utils.getCoords(game.box[i][j].toString()))
                        && game.box[i][j].getText() != "X"
                        && game.box[i][j].getText() != "O") {
                    System.out.println("Inside");
                    game.box[i][j].setText(this.opponentSymbol);
                    game.box[i][j].setFont(new java.awt.Font("Stabillo Medium", 1, 23));
                    game.box[i][j].setForeground(this.opponentColor);
                    
                    // player turn;
                    this.isTurn = true;
                }
            }
        }

        if(!isTurn) System.out.println("Wrong move");
    }
    
    public void sendWinResult() throws IOException{
        if (this.outputStream == null) {
            return;
        }
        
        String winMsg = "winner " + this.userName + "\n";
        this.outputStream.write(winMsg.getBytes());
        this.outputStream.flush();
        
    }

    private void handleWinResult(OutputStream outputStream, String[] tokens) {
        this.isOpponentWin = true;
    }
}
