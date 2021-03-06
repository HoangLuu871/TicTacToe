/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.tictactoe;

/**
 * @author son
 */

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashSet;

import myproject.tictactoe.Utils.Pair;
import myproject.tictactoe.Utils.utils;

public class ServerWorker extends Thread {

    private final Socket clientServeSocket;
    private final Server gameServer;
    public OutputStream outputStream;

    public String workerName;
    private String password;
    public boolean isOnline;
    public String clientSymbol;
    private HashSet<Pair<String, String>> validUser;

    ServerWorker(Server server, Socket clientSocket, String symbol) {
        this.gameServer = server;
        this.clientServeSocket = clientSocket;
        this.isOnline = false;
        this.clientSymbol = symbol;
    }

    @Override
    public void run() {
        try {
            this.handleClientSocket(this.clientServeSocket);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void handleClientSocket(Socket clientSocket) throws IOException, InterruptedException {
        InputStream inputStream = clientSocket.getInputStream();
        outputStream = clientSocket.getOutputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        String query; // query a service to server
        String content; // content of query
        // set name
        while ((line = reader.readLine()) != null) {
            String[] tokens = line.split(" ");

            if (tokens != null && tokens.length > 0) {
                query = tokens[0];
                // check login 
                if (query.equalsIgnoreCase("login") && !this.isOnline) {
                    validUser = gameServer.getValidUser();
                    // tokens template: "login"
                    this.handleOnline(outputStream, tokens);
                } else if (query.equalsIgnoreCase("create")) {
                    this.handleCreateAccount(outputStream, tokens);

                }

                // if user login, user can use below commands;
                if (this.isOnline) {
                    System.out.println("Receive " + query + " from " + this.workerName);
                    if (query.equalsIgnoreCase("move")) {
                        // tokens template: "move text position"
                        this.handleMove(outputStream, tokens);
                    } else if (query.equalsIgnoreCase("logout")) {
                        // tokens template: "logout"
                        this.handleOffline(outputStream, tokens);
                        break;
                    } else if (query.equalsIgnoreCase("msg")) {
                        System.out.println("Server receive message from hahahahaha: " + tokens[1]);
                        this.handleMsg(outputStream, tokens);
                    } else if (query.equalsIgnoreCase("winner")) {
                        this.handleMatchResult(outputStream, tokens);
                    }
                }
            }

        }

        inputStream.close();

        outputStream.close();

        clientSocket.close();
    }

    private void handleMatchResult(OutputStream outputStream, String[] tokens) throws IOException {
        ArrayList<ServerWorker> workerList = this.gameServer.getWorkerList();
        String msg = "winner " + tokens[1] + "\n";

        System.out.println("MSG: " + msg);
        for (ServerWorker worker : workerList) {
            if (!this.workerName.equalsIgnoreCase(worker.workerName)) {
                System.out.println("Send match result to user: " + worker.workerName);
                worker.outputStream.write(msg.getBytes());
                worker.outputStream.flush();
            }
        }

    }

    private void handleMsg(OutputStream outputStream, String[] tokens) throws IOException {
        ArrayList<ServerWorker> workerList = this.gameServer.getWorkerList();
        if (tokens.length >= 3) {
            String msg = "";
            for (int i = 0; i < tokens.length; i++) {
                msg += (tokens[i] + " ");
            }
            System.out.println("MSG: " + msg);
            for (ServerWorker worker : workerList) {
                if (!this.workerName.equalsIgnoreCase(worker.workerName)) {
                    System.out.println("Send chat message to user: " + worker.workerName);
                    worker.outputStream.write((msg + "\n").getBytes());
                    worker.outputStream.flush();
                }
            }
        }
    }

    private void handleCreateAccount(OutputStream outputStream, String[] tokens) {
        if (tokens.length == 3) {
            System.out.println("Receive create account request from " + this.workerName);

            String userName = tokens[1];
            String password = tokens[2];
            utils.writeFile("userDataBase.txt", userName + " " + password);
            utils.writeFile("src/main/java/userDataBase.txt", userName + " " + password); // bug so we have to write two times

            this.gameServer.setValidUser();


        } else {
            System.out.println("Create account not success");
            String createMsg = "";
        }

    }

    private void handleOffline(OutputStream outputStream, String[] tokens) throws IOException {
        ArrayList<ServerWorker> workerList = this.gameServer.getWorkerList();
        // remove this worker
        workerList.remove(this);
        this.isOnline = false;
        String onlineMsg = "offline user " + this.workerName + "\n";
        for (ServerWorker worker : workerList) {
            if (!(this.workerName.equalsIgnoreCase(worker.workerName) && !worker.isOnline)) {
                worker.outputStream.write(onlineMsg.getBytes());
                worker.outputStream.flush();
            }
        }

    }

    private void handleOnline(OutputStream outputStream, String[] tokens) throws IOException {
        ArrayList<ServerWorker> workerList = this.gameServer.getWorkerList();

        // login username password
        if (tokens.length == 3) {
            this.workerName = tokens[1];
            this.password = tokens[2];

            String userMsg = "";
            userMsg = checkValidUser(workerName, password);
            System.out.println(userMsg);
            if (!userMsg.equalsIgnoreCase("Invalid User")) {
                this.outputStream.write(userMsg.getBytes());
                this.outputStream.flush();
                this.isOnline = true;
            } else {
                this.outputStream.write((userMsg + "\n").getBytes());
                this.outputStream.flush();
            }
            System.out.println("Receive " + userMsg);
        }
    }

    private void handleMove(OutputStream outputStream, String[] tokens) throws IOException {
        ArrayList<ServerWorker> workerList = this.gameServer.getWorkerList();

        if (tokens.length == 3) {
            String userText = tokens[1];
            String position = tokens[2];

            String moveMsg = "move " + userText + " " + position + "\n";

            for (ServerWorker worker : workerList) {
                if (!this.workerName.equalsIgnoreCase(worker.workerName)) {
                    System.out.println("Send to user: " + worker.workerName);
                    worker.outputStream.write(moveMsg.getBytes());
                    worker.outputStream.flush();
                }
            }
        }

    }

    private String checkValidUser(String workerName, String password) {
        Pair<String, String> loginInfo = new Pair(workerName, password);

        for (Pair<String, String> test : this.validUser) {
            if (test.equals(loginInfo)) {
                return "Welcome player " + workerName + " " + this.clientSymbol + "\n";
            }
        }
//        if((workerName.equals("son") && password.equals("son")) || (workerName.equals("hoang") && password.equals("hoang"))){
//            return "Welcome player " + workerName + "\n";
//        }

        return "Invalid User";

    }
}
