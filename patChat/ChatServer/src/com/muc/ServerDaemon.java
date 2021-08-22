package com.muc;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.Socket;
import java.util.HashSet;
import java.util.List;

public class ServerDaemon extends Thread {

    private final Socket clientSocket;
    private final Server server;
    private String login = null;
    private OutputStream outputStream;
    private HashSet<String> topicSet = new HashSet<>();

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
                if ("logoff".equals(cmd) || "quit".equalsIgnoreCase(cmd)) {
                    handleLogoff();
                    break;
                } else if ("login".equalsIgnoreCase(cmd)) {
                    handleLogin(outputStream, tokens);
                } else if ("msg".equalsIgnoreCase(cmd)) {
                    String[] tokensMsg = StringUtils.split(line, null, 3);
                    sendMessage(tokensMsg);
                } else if ("join".equalsIgnoreCase(cmd)) {
                    handleJoin(tokens);
            } else {
                String msg = "unknown " + cmd + "\n";
                outputStream.write(msg.getBytes());
                }
            }
        }

        clientSocket.close();
    }

    public boolean isMemberOfTopic(String topic) {
        return topicSet.contains(topic);
    }


    private void handleJoin(String[] tokens) {
        if (tokens.length > 1) {
            String topic = tokens[1];
            topicSet.add(topic);
        }

    }

    private void sendMessage(String[] tokens) throws IOException {
        String sendUser = tokens[1];
        String msgBody = tokens[2];

        boolean isTopic = sendUser.charAt(0) == '#';

        List<ServerDaemon> daemonList = server.getDaemonList();
        for(ServerDaemon daemon : daemonList) {
            if (sendUser.equalsIgnoreCase(daemon.getLogin())) {
                if (isTopic) {
                    String outMsg = "msg " + login + " " + msgBody + "\n";
                    daemon.send(outMsg);
                } else {
                String outMsg = "msg " + login + " " + msgBody + "\n";
                daemon.send(outMsg);
                }
            }
        }
    }

    private void handleLogoff() throws IOException {
        server.removeDaemon(this);
        List<ServerDaemon> daemonList = server.getDaemonList();
        String onMsg = "\n User " + login + " is offline " + "\n";
        for (ServerDaemon daemon : daemonList) {
            if (!login.equals(daemon.getLogin())) {
                daemon.send(onMsg);
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
                String msg = "ok login" + "\n";
                outputStream.write(msg.getBytes());
                this.login = login;

                System.out.println("\nUser " + login + " has connected\n");
                String onMsg = "\n User " + login + " is now logged in" + "\n";
                List<ServerDaemon> daemonList = server.getDaemonList();

                for(ServerDaemon daemon : daemonList) {
                    if (!login.equals(daemon.getLogin())) {
                        if (daemon.getLogin() != null) {
                            String msg2 = "online " + daemon.getLogin();
                            send(msg2);
                        }
                    }
                }

                for(ServerDaemon daemon : daemonList) {
                    if (!login.equals(daemon.getLogin())) {
                        daemon.send(onMsg);
                    }
                }
            } else {
                String msg = "error login";
                outputStream.write(msg.getBytes());
            }
        }
    }

    private void send(String onMsg) {
        if (login != null) {
            try {outputStream.write(onMsg.getBytes());
        } catch(Exception ex) {
            ex.printStackTrace();
            }
        }
    }

}
