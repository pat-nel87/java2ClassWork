package com.muc;

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

    public ChatClient(String serverName, int serverPort) {

        this.serverName = serverName;
        this.serverPort = serverPort;

    }

    public static void main(String[] args) throws IOException {
        ChatClient client = new ChatClient("localhost", 8818);
        if (!client.connect()) {
            System.err.println("\nConnection failed!\n");
        } else {
            System.out.println("\nConnect Successful!\n");
            if (client.login("guest", "guest")) {
                System.out.println("Login Successful");
            } else {
                System.out.println("Login Failed");
            }

        }
    }

    private boolean login(String login, String password) throws IOException {
        String cmd = "login " + login + " " + password + "\n";
        serverOut.write(cmd.getBytes());

        String response = bufferedIn.readLine();
        System.out.println(response);

        if ("ok login".equalsIgnoreCase(response)) {
            return true;
        } else {
            return false;
        }


    }

    private boolean connect() {
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

}
