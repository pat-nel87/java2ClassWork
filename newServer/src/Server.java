import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.net.*;



// Server class
public class Server extends JFrame {

    private ArrayList<ClientManager> clientList;
    public ServerSocket serverSocket;
    private int clientNumber;
    private JFrame serverWindow;
    private JTextArea serverDialogue;
    private JTextField serverEntryField;

    public Server()
    {
        super("Server Window");
        serverWindow = new JFrame();
        serverDialogue = new JTextArea();
        serverEntryField = new JTextField();
        serverEntryField.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        writeToUsers(actionEvent.getActionCommand());
                        serverEntryField.setText("");
                    }
                }
        );
        serverWindow.add(serverDialogue);
        serverWindow.add(serverEntryField, BorderLayout.SOUTH);
        serverWindow.setSize(500,500);
        serverWindow.setVisible(true);
        serverWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        clientList = new ArrayList<ClientManager>();
        clientNumber = 0;

        try {
            serverSocket = new ServerSocket(8818);
            serverDialogue.append("Server is now listening on Port 8818 \n");
            while (true) {
                Socket connection = null;
                try{
                   connection = serverSocket.accept();
                   serverDialogue.append("Client has connected " + connection.getLocalSocketAddress() + "\n");
                   Writer out = new OutputStreamWriter(connection.getOutputStream());
                   out.write("\n You are Client #" + clientNumber);
                   out.flush();
                   String userName = "Client" + clientNumber;

                   ClientManager clientConnection = new ClientManager(userName, connection, connection.getOutputStream(), connection.getInputStream() /* serverWindow, serverDialogue */);
                   Thread t = new Thread(clientConnection);
                   clientList.add(clientConnection);
                   clientConnection.setUserList(clientList);
                   t.start();
                   out.flush();
                   clientNumber++;


                } catch(IOException ex) {
                    ex.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



    }
    public void checkForMessages() throws IOException {
        for (ClientManager clients : clientList) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(clients.dataInputStream));
            if (reader.ready()) {
                serverDialogue.append(reader.readLine());
            }
        }
    }
    /* iterates through clientlist to
     send message to every client on list.
      - will need a if clients.loggedon = true
      to make sure it doesn't send messages to clients who left the chat
     */

    private void writeToUsers(String actionCommand) {
        for (ClientManager clients : clientList) {
            Writer writer = new OutputStreamWriter(clients.dataOutputStream);
            try {
                writer.write("\n" + actionCommand + "\n");
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        Server server = new Server();

    }





}