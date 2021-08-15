package com.muc;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread {

    private final int serverPort;

    private ArrayList<ServerDaemon> daemonList = new ArrayList<>();

    public Server(int serverPort) { this.serverPort = serverPort;}

    public List<ServerDaemon> getDaemonList() {
        return daemonList;
    }

    @Override
    public void run() {

        try {
            ServerSocket serverSocket = new ServerSocket(serverPort);
            while (true) {

                System.out.println("About to accept client connection...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted Client Connection from " + clientSocket);
                ServerDaemon daemon = new ServerDaemon(clientSocket, this);
                daemonList.add(daemon);
                daemon.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void removeDaemon(ServerDaemon serverDaemon) {
        daemonList.remove(serverDaemon);

    }
}
