package com.muc;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ChatClient {
    private final String serverName;
    private final int serverPort;
    private Socket socket;
    private InputStream serverIn;
    private OutputStream serverOut;
    private BufferedReader bufferedIn;

    private ArrayList<UserStateListener> userStateListeners = new ArrayList<>();
    private ArrayList<MessageListener> messageListeners = new ArrayList<>();

    public ChatClient(String serverName, int serverPort) {

        this.serverName = serverName;
        this.serverPort = serverPort;

    }

    public static void main(String[] args) throws IOException {
        ChatClient client = new ChatClient("localhost", 8818);
        client.addUserStateListener(new UserStateListener() {
            @Override
            public void online(String login) {
                System.out.println("ONLINE: " + login);
            }

            @Override
            public void offline(String login) {
                System.out.println("OFFLINE: " + login);
            }
        });

        client.addMessageListener(new MessageListener() {
            @Override
            public void onMessage(String sentFrom, String message) {
                System.out.println("New Message from: " + sentFrom + " --->" + message);
            }
        });

        if (!client.connect()) {
            System.err.println("\nConnection failed!\n");
        } else {
            System.out.println("\nConnect Successful!\n");
            if (client.login("guest", "guest")) {
                System.out.println("Login Successful");

                client.msg("guest2", "Hello Guest2!");
            } else {
                System.out.println("Login Failed");
            }

            //client.logoff();
        }
    }

    public void msg(String receiver, String s) throws IOException {
        String cmd = "msg " + receiver + " " + s + "\n";
        serverOut.write(cmd.getBytes());
    }

    public boolean login(String login, String password) throws IOException {
        String cmd = "login " + login + " " + password + "\n";
        serverOut.write(cmd.getBytes());

        String response = bufferedIn.readLine();
        System.out.println(response);

        if ("ok login".equalsIgnoreCase(response)) {
            startMessageRead();
            return true;
        } else {
            return false;
        }
    }

    public void logoff() throws IOException {
        String cmd = "logoff\n";
        serverOut.write(cmd.getBytes());

    }

    public void startMessageRead() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                readMessageLoop();
            }

        };
        thread.start();
    }

    public void readMessageLoop() {

        try {
            String line;
            while ((line = bufferedIn.readLine()) != null) {
                String[] tokens = StringUtils.split(line);
                if (tokens != null && tokens.length > 0) {
                    String cmd = tokens[0];
                    if ("online".equalsIgnoreCase(cmd)) {
                        handleOnline(tokens);
                    } else if ("offline".equalsIgnoreCase(cmd)) {
                        handleOffline(tokens);
                    } else if ("msg".equalsIgnoreCase(cmd)) {
                        String[] tokensMessage = StringUtils.split(line, null, 3);
                        handleMessage(tokensMessage);
                    }
                }

            }

        } catch (Exception ex) {
            ex.printStackTrace();

            try {
                socket.close();
             } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleMessage(String[] tokensMessage) {
        String login = tokensMessage[1];
        String message = tokensMessage[2];

        for (MessageListener listener : messageListeners) {
            listener.onMessage(login, message);
        }
    }

    private void handleOffline(String[] tokens) {
        String login = tokens[1];
        for (UserStateListener listener : userStateListeners) {
            listener.offline(login);

        }

    }

    private void handleOnline(String[] tokens) {
        String login = tokens[1];
        for (UserStateListener listener : userStateListeners) {
            listener.online(login);

        }

    }

    public boolean connect() {
        try {
            this.socket = new Socket(serverName, serverPort);
            System.out.println("Client port is " + socket.getLocalPort());
            this.serverOut = socket.getOutputStream();
            this.serverIn = socket.getInputStream();
            this.bufferedIn = new BufferedReader(new InputStreamReader(serverIn));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
        }

        public void addUserStateListener(UserStateListener listener) {
            userStateListeners.add(listener);
        }

        public void deleteUserStateListener(UserStateListener listener) {
        userStateListeners.remove(listener);
        }

        public void addMessageListener(MessageListener listener) {
            messageListeners.add(listener);
        }

        public void removeMessageListener(MessageListener listener) {
            messageListeners.remove(listener);
        }

}
