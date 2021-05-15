/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.tictactoe;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import myproject.tictactoe.Utils.Pair;

/**
 *
 * @author son
 */
public class Server extends Thread {

    private int port;
    private String ip;

    public static final int maxPlayer = 2; // num player can connect 
    public int currentNumPlayer;
    public String[] playerSymbols = {"X", "O"};

    private ServerSocket serverSocket;

    private ArrayList<ServerWorker> workerList;

    private static final String fileName = "userDataBase.txt";
    private HashSet<Pair<String, String>> validUser = new HashSet<Pair<String, String>>();

    public Server(String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.currentNumPlayer = 0;
        this.workerList = new ArrayList<>();
        // init database
        this.setValidUser();
    }

    public int getPort() {
        return this.port;
    }

    public String getIp() {
        return this.ip;
    }

    public ArrayList<ServerWorker> getWorkerList() {
        return this.workerList;
    }

    public HashSet<Pair<String, String>> getValidUser() {
        return this.validUser;
    }

    private void setValidUser() {
        File info = new File(this.fileName);

        try {
            Scanner myReader = new Scanner(info);
            while (myReader.hasNextLine()) {
                String[] data = myReader.nextLine().split(" ");
                this.validUser.add(new Pair(data[0], data[1].strip()));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred when trying to reading file");
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            this.serverSocket = new ServerSocket(this.port, this.maxPlayer);
            // user database setup
//            for(Pair<String, String> test: validUser){
//                System.out.println(test.toString());
//            }

            while (true) {
                System.out.println("About to accept " + (this.maxPlayer - this.currentNumPlayer) + " client connection");
                Socket clientServeSocket = serverSocket.accept();

                this.currentNumPlayer++;
                String playerSymbol = playerSymbols[this.currentNumPlayer - 1];

                System.out.println("Accepted connection from " + clientServeSocket);

                ServerWorker worker = new ServerWorker(this, clientServeSocket, playerSymbol);
                this.workerList.add(worker);
                worker.start();

                if (this.currentNumPlayer == this.maxPlayer) {
                    break;
                }
            }

            this.stopServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopServer() throws IOException {
        this.serverSocket.close();
    }

    public boolean startGame() {
        boolean isStart = true;
        for (ServerWorker worker : workerList) {
            if (!worker.isOnline) {
                isStart = false;
            }
        }
        System.out.println(isStart);
        return isStart;
    }

    public void triggerStart() throws IOException {
        for (ServerWorker worker : workerList) {
            if (worker.outputStream != null) {
                worker.outputStream.write(("Start").getBytes());

            }
        }
    }

}
