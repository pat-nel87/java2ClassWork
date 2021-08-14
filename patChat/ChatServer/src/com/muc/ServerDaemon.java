package com.muc;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ServerDaemon extends Thread {

    private final Socket clientSocket;
    private final Server server;
    private String login = null;
    private OutputStream outputStream;


    public ServerDaemon(Socket clientSocket, Server server) {
        this.clientSocket = clientSocket;
        this.server = server;
    }

    @Override

    public void run() {
        try {
            handleClientSocket();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void handleClientSocket() throws IOException, InterruptedException {


        InputStream inputStream = clientSocket.getInputStream();
        this.outputStream = clientSocket.getOutputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;


        while ( (line = reader.readLine()) != null) {
            String[] tokens = StringUtils.split(line);
            if (tokens != null && tokens.length > 0) {
                String cmd = tokens[0];
                if ("quit".equalsIgnoreCase(cmd)) {
                    System.out.println("Ready to quit?");
                    break;
                } else if ("login".equalsIgnoreCase(cmd)) {
                    handleLogin(outputStream, tokens);
            } else {
                String msg = "unknown " + cmd + "\n";
                outputStream.write(msg.getBytes());
                }
            }
        }
        clientSocket.close();
    }

    public String getLogin() { return login; }



    private void handleLogin(OutputStream outputStream, String[] tokens) throws IOException {


        if (tokens.length == 3) {
            String login = tokens[1];
            String password = tokens[2];

            if ((login.equals("guest") && password.equals("guest")) || login.equals("guest2") && password.equals("guest2")) {
                String msg = "ok login";
                outputStream.write(msg.getBytes());
                this.login = login;

                String onMsg = "online!" + login + "\n";
                List<ServerDaemon> daemonList = server.getDaemonList();
                for(ServerDaemon daemon : daemonList) {
                    daemon.send(onMsg);

                }

            } else {
                String msg = "error login";
                outputStream.write(msg.getBytes());

            }

        }

    }

    private void send(String onMsg) throws IOException {
        outputStream.write(onMsg.getBytes());
    }
}
