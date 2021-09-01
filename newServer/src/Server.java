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
    private UserSessionManager usersOnline;

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
            usersOnline = new UserSessionManager(new ArrayList<>());
            while (true) {
                Socket connection = null;
                try {
                   connection = serverSocket.accept();
                   serverDialogue.append("Client has connected " + connection.getLocalSocketAddress() + "\n");
                   serverDialogue.append("Client remote socket address :" + connection.getRemoteSocketAddress() + "\n");
                   //Writer out = new OutputStreamWriter(connection.getOutputStream());
                   //out.write("\n You are Client #" + clientNumber);
                   //out.flush();
                   String userName = "Client" + clientNumber;
                   String message = ("\n You are Client #" + clientNumber);
                   sendMessagePacket(message, "Server", userName, 1, connection);
                   ClientManager clientConnection = new ClientManager(userName, connection, connection.getOutputStream(), connection.getInputStream());
                   Thread t = new Thread(clientConnection);
                   clientList.add(clientConnection);
                   //clientConnection.setUserList(clientList);
                  // usersOnline.setUsersList(clientList);
                   clientConnection.setUserList(usersOnline);
                   t.start();
                   //out.flush();
                   clientNumber++;
                   updateClientLists();
                   connection.getOutputStream().flush();
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

    public void sendMessagePacket(String message, String userName, String sendTo, int packetHeader, Socket socket) throws IOException {
        /* creates and sends a messagePacket to desired location  */
        MessagePacket newMessage = new MessagePacket(message, userName, sendTo, packetHeader);
        ObjectOutputStream objOut = new ObjectOutputStream(socket.getOutputStream());
        objOut.writeObject(newMessage);
        objOut.flush();

    }
    public void updateClientLists() throws IOException {
        ArrayList temp = new ArrayList();
        for (ClientManager clients : clientList) {
            temp.add(clients.userName);
        }
        usersOnline.setUsersList(temp);
        sendClientLists();
    }

    private void sendClientLists() throws IOException {
        for (ClientManager clients : clientList) {
            clients.setClientsList(clientList);
            ObjectOutputStream objOut = new ObjectOutputStream(clients.socket.getOutputStream());
            objOut.writeObject(usersOnline);
            objOut.flush();
        }
    }

    /*
    Deprecated method
    public void checkForMessages() throws IOException {
        for (ClientManager clients : clientList) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(clients.dataInputStream));
            if (reader.ready()) {
                serverDialogue.append(reader.readLine());
            }
        }
    }
    */
    private void writeToUsers(String actionCommand) {
        /* iterates through clientlist to
     send message to every client on list.
      - will need a if clients.loggedon = true
      to make sure it doesn't send messages to clients who left the chat
     */
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